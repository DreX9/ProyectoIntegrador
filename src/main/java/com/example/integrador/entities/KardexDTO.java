package com.example.integrador.entities;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class KardexDTO {
    private String tipo;
    private Date fecha;
    private String nombreProducto;
    private Double peso;
    private double precioUnitario;
    private double costoTotal;
    private Timestamp  momento;

    public KardexDTO(String tipo, Date fecha, String nombreProducto, Double peso, double precioUnitario,
            double costoTotal, Timestamp  momento) {
        this.tipo = tipo;
        this.fecha = fecha;
        this.nombreProducto = nombreProducto;
        this.peso = peso;
        this.precioUnitario = precioUnitario;
        this.costoTotal = costoTotal;
        this.momento = momento;
    }
}
