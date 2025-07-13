package com.example.integrador.util;

import java.io.OutputStream;
import java.awt.Color;


import com.example.integrador.entities.Venta;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import java.text.DecimalFormat;

public class FacturaPdfGenerator {

    public static void generarFacturaPDF(OutputStream out, Venta venta) throws Exception {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, out);
        document.open();

        DecimalFormat df = new DecimalFormat("0.00");
        String numeroFactura = String.format("F%06d", venta.getId());

        // Fuentes
        Font tituloFont = new Font(Font.HELVETICA, 18, Font.BOLD);
        Font normalFont = new Font(Font.HELVETICA, 12);
        Font boldFont = new Font(Font.HELVETICA, 12, Font.BOLD);

        // Título
        Paragraph titulo = new Paragraph("FACTURA #" + numeroFactura, tituloFont);
        titulo.setAlignment(Paragraph.ALIGN_RIGHT);
        document.add(titulo);

        // Info del cliente y factura
        document.add(new Paragraph("Cliente: " + venta.getCliente().getNombre() + " " + venta.getCliente().getApellido()+
                           " (DNI: " + venta.getCliente().getDni() + ")", normalFont));
        document.add(new Paragraph("Fecha: " + venta.getFechaVenta(), normalFont));
        document.add(new Paragraph("Hora: " + venta.getMomento().toLocalTime(), normalFont));
        document.add(new Paragraph("Vendido por: " + venta.getUsuario().getNombre() + " " + venta.getUsuario().getApellido(), normalFont));
        document.add(new Paragraph(" ")); // espacio

        // Tabla principal
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        table.setWidths(new float[]{3, 1.5f, 1, 2, 1, 2});

        // Encabezados
        String[] headers = {"CONCEPTO", "PRECIO", "UNIDADES", "SUBTOTAL", "IGV", "TOTAL"};
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, boldFont));
            cell.setBackgroundColor(Color.LIGHT_GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }

        // Datos de los productos
        for (var detalle : venta.getDetalles()) {
            String producto = detalle.getInventario().getDetalleCompra().getProducto().getNombre();
            double precio = detalle.getPrecio();
            double peso = detalle.getPeso();
            double subtotal = detalle.getSubTotal();
            double igv = subtotal * 0.18;
            double totalFila = subtotal + igv;

            table.addCell(new Phrase(producto, normalFont));
            table.addCell(new Phrase("S/ " + df.format(precio), normalFont));
            table.addCell(new Phrase(df.format(peso), normalFont));
            table.addCell(new Phrase("S/ " + df.format(subtotal), normalFont));
            table.addCell(new Phrase("18%", normalFont));
            table.addCell(new Phrase("S/ " + df.format(totalFila), normalFont));
        }

        document.add(table);
        document.add(new Paragraph(" "));

        // Totales
        PdfPTable resumen = new PdfPTable(2);
        resumen.setWidthPercentage(40);
        resumen.setHorizontalAlignment(Element.ALIGN_RIGHT);

        resumen.addCell(new Phrase("BASE IMPONIBLE", boldFont));
        resumen.addCell("S/ " + df.format(venta.getTotal() - venta.getIgv()));

        resumen.addCell(new Phrase("IGV (18%)", boldFont));
        resumen.addCell("S/ " + df.format(venta.getIgv()));

        resumen.addCell(new Phrase("TOTAL", boldFont));
        resumen.addCell("S/ " + df.format(venta.getTotal()));

        document.add(resumen);
        document.close();
    }
}