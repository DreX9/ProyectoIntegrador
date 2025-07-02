package com.example.integrador.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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

            // Fila 0 (títulos agrupados)
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

            // Fila 1 (subtítulos)
            Row header = sheet.createRow(1);
            header.createCell(0).setCellValue("Fecha");
            header.createCell(1).setCellValue("Producto");

            header.createCell(2).setCellValue("Peso (kg)");
            header.createCell(3).setCellValue("C. Unit");
            header.createCell(4).setCellValue("C. Total");

            header.createCell(5).setCellValue("Peso (kg)");
            header.createCell(6).setCellValue("C. Unit");
            header.createCell(7).setCellValue("C. Total");

            header.createCell(8).setCellValue("Peso (kg)");
            header.createCell(9).setCellValue("C. Unit");
            header.createCell(10).setCellValue("C. Total");

            for (int i = 0; i <= 10; i++) {
                header.getCell(i).setCellStyle(headerStyle);
            }

            int rowNum = 2;

            for (KardexDTO mov : movimientos) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(mov.getFecha().toString());
                row.createCell(1).setCellValue(mov.getNombreProducto());

                if (mov.getTipo().equalsIgnoreCase("Entrada")) {
                    double peso = mov.getPeso();
                    double precio = mov.getPrecioUnitario();
                    double total = mov.getCostoTotal();

                    // Entrada
                    row.createCell(2).setCellValue(peso);
                    row.createCell(3).setCellValue(precio);
                    row.createCell(4).setCellValue(total);

                    saldos.add(new Lote(peso, precio));

                } else {
                    double pesoSalida = mov.getPeso();
                    double costoTotalSalida = 0;
                    double pesoOriginal = pesoSalida;
                    // FIFO (PEPS)
                    while (pesoSalida > 0 && !saldos.isEmpty()) {
                        Lote lote = saldos.get(0);
                        if (pesoSalida >= lote.peso) {
                            costoTotalSalida += lote.peso * lote.precioUnitario;
                            pesoSalida -= lote.peso;
                            saldos.remove(0);
                        } else {
                            costoTotalSalida += pesoSalida * lote.precioUnitario;
                            lote.peso -= pesoSalida;
                            pesoSalida = 0;
                        }
                    }
                    double costoUnitarioSalida = pesoOriginal != 0 ? costoTotalSalida / pesoOriginal : 0;
                    row.createCell(5).setCellValue(mov.getPeso());
                    row.createCell(6).setCellValue(costoUnitarioSalida);
                    row.createCell(7).setCellValue(costoTotalSalida);
                }

                // Calcular saldos
                double saldoPeso = saldos.stream().mapToDouble(l -> l.peso).sum();
                double saldoValor = saldos.stream().mapToDouble(l -> l.peso * l.precioUnitario).sum();
                double saldoUnitario = saldoPeso != 0 ? saldoValor / saldoPeso : 0;

                row.createCell(8).setCellValue(saldoPeso);
                row.createCell(9).setCellValue(saldoUnitario);
                row.createCell(10).setCellValue(saldoValor);
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