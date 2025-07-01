package com.example.integrador.entities;

import java.sql.Date;

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
    // Este constructor es obligatorio si estás usando una consulta @Query con nativeQuery = true y new com.example...
    public KardexDTO(String tipo, Date fecha, String nombreProducto, Double peso, double precioUnitario, double costoTotal) {
        this.tipo = tipo;
        this.fecha = fecha;
        this.nombreProducto = nombreProducto;
        this.peso = peso;
        this.precioUnitario = precioUnitario;
        this.costoTotal = costoTotal;
    }
}
