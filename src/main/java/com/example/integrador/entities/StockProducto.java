package com.example.integrador.entities;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vista_stock_producto")
@Data
@NoArgsConstructor
public class StockProducto {
    @Id
    @Column(name = "id_producto")
    private Integer id;
    @Column(name = "nombre_producto")
    private String nombre;
    @Column(name = "stock_total")
    private Integer stock;
    @Column(name = "proxima_caducidad")
    private Date caducidad;
    @Column(name = "peso_total")
    private Double total;
}
