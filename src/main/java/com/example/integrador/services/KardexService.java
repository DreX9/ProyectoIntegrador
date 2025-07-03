package com.example.integrador.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.integrador.entities.KardexDTO;
import com.example.integrador.repositories.KardexRepository;

@Service
public class KardexService {

    @Autowired
    private KardexRepository kardexRepository;

    private static class Lote {
        double peso;
        double precioUnitario;

        public Lote(double peso, double precioUnitario) {
            this.peso = peso;
            this.precioUnitario = precioUnitario;
        }
    }

    public byte[] generarKardexExcel(Integer idProducto) {
        List<KardexDTO> movimientos = kardexRepository.obtenerKardexPorProducto(idProducto);
        List<Lote> saldos = new LinkedList<>();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Kardex");

            // Estilos
            CellStyle headerStyle = workbook.createCellStyle();
            Font boldFont = workbook.createFont();
            boldFont.setBold(true);
            headerStyle.setFont(boldFont);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);

            CellStyle entradasStyle = workbook.createCellStyle();
            entradasStyle.cloneStyleFrom(headerStyle);
            entradasStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
            entradasStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            CellStyle salidasStyle = workbook.createCellStyle();
            salidasStyle.cloneStyleFrom(headerStyle);
            salidasStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
            salidasStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            CellStyle saldosStyle = workbook.createCellStyle();
            saldosStyle.cloneStyleFrom(headerStyle);
            saldosStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
            saldosStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Fila 0: títulos agrupados
            Row groupRow = sheet.createRow(0);
            groupRow.createCell(0).setCellValue("Fecha");
            groupRow.createCell(1).setCellValue("Producto");
            groupRow.createCell(2).setCellValue("ENTRADAS");
            groupRow.createCell(5).setCellValue("SALIDAS");
            groupRow.createCell(8).setCellValue("SALDOS");

            groupRow.getCell(2).setCellStyle(entradasStyle);
            groupRow.getCell(5).setCellStyle(salidasStyle);
            groupRow.getCell(8).setCellStyle(saldosStyle);

            sheet.addMergedRegion(new CellRangeAddress(0, 0, 2, 4));
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 5, 7));
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 8, 10));

            // Fila 1: subtítulos
            Row header = sheet.createRow(1);
            String[] headers = {
                "Fecha", "Producto", "Peso (kg)", "C. Unit", "C. Total",
                "Peso (kg)", "C. Unit", "C. Total",
                "Peso (kg)", "C. Unit", "C. Total"
            };

            for (int i = 0; i < headers.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            int rowNum = 2;

            for (KardexDTO mov : movimientos) {
                if (mov.getTipo().equalsIgnoreCase("Entrada")) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(mov.getFecha().toString());
                    row.createCell(1).setCellValue(mov.getNombreProducto());
                    row.createCell(2).setCellValue(mov.getPeso());
                    row.createCell(3).setCellValue(mov.getPrecioUnitario());
                    row.createCell(4).setCellValue(mov.getCostoTotal());

                    saldos.add(new Lote(mov.getPeso(), mov.getPrecioUnitario()));

                } else { // Salida
                    double pesoSalida = mov.getPeso();

                    while (pesoSalida > 0 && !saldos.isEmpty()) {
                        Lote lote = saldos.get(0);
                        double usado = Math.min(lote.peso, pesoSalida);

                        Row row = sheet.createRow(rowNum++);
                        row.createCell(0).setCellValue(mov.getFecha().toString());
                        row.createCell(1).setCellValue(mov.getNombreProducto());
                        row.createCell(5).setCellValue(usado);
                        row.createCell(6).setCellValue(lote.precioUnitario);
                        row.createCell(7).setCellValue(usado * lote.precioUnitario);

                        lote.peso -= usado;
                        pesoSalida -= usado;

                        if (lote.peso <= 0) {
                            saldos.remove(0);
                        }
                    }
                }

                // Mostrar todos los saldos actuales
                for (Lote s : saldos) {
                    Row saldoRow = sheet.createRow(rowNum++);
                    saldoRow.createCell(8).setCellValue(s.peso);
                    saldoRow.createCell(9).setCellValue(s.precioUnitario);
                    saldoRow.createCell(10).setCellValue(s.peso * s.precioUnitario);
                }
            }

            for (int i = 0; i <= 10; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream output = new ByteArrayOutputStream();
            workbook.write(output);
            return output.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Error al generar el archivo", e);
        }
    }
}
