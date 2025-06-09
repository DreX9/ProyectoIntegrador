package com.example.integrador.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

    @Entity
    @Table(name = "detalle_compra")
    @Data
    @NoArgsConstructor
    public class DetalleCompra {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_detalle_compra")
        private Integer id;
        @ManyToOne
        @JoinColumn(name = "id_compra")
        private Compra compra;
        @ManyToOne
        @JoinColumn(name = "id_producto")
        private Producto producto;
        @ManyToOne
        @JoinColumn(name = "id_almacen")
        private Almacen almacen;
        private Integer cantidad;
        @Column(name = "precio_unitario")
        private Double precioUnitario;
        @Column(name = "peso_total_kg")
        private Double pesoTotal;
        @Column(name = "subtotal")
        private Double subtotal;
    }
