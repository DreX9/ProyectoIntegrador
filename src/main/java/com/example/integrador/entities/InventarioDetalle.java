package com.example.integrador.entities;


import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vista_inventario_detallado")
@Data
@NoArgsConstructor
public class InventarioDetalle {
    @Id
    @Column(name = "id_inventario")
    private int id;
    @Column(name = "nombre_producto")
    private String nombrePro;
    @Column(name = "nombre_almacen")
    private String nombreAlma;
    @Column(name = "precio_unitario")
    private double precio;
    @Column(name = "subtotal")
    private double subtotal;
    @Column(name = "fecha_compra")
    private Date fechaCompra;
    @Column(name = "fecha_caducidad_estimada")
    private Date fechaCaducidad;
    @Column(name = "cantidad_disponible")
    private int cantidad;
    @Column(name = "peso_total_disponible_kg")
    private double pesoTotal;
}
