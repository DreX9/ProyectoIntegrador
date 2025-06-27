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
@Table(name = "detalle_venta")
@Data
@NoArgsConstructor
public class DetalleVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_venta")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "id_venta")
    private Venta venta;
    @ManyToOne
    @JoinColumn(name = "id_inventario")
    private Inventario inventario;
    @Column(name = "peso_total_kg")
    private Double peso;
    @Column(name = "precio_unitario")
    private Double precio;
    @Column(name = "subtotal")
    private Double subTotal;
}
