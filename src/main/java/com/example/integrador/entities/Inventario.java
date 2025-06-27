package com.example.integrador.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
@Table(name = "inventario")
@Data
@NoArgsConstructor
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inventario")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_almacen")
    private Almacen Almacen;

    @Column(name = "cantidad_disponible")
    private Integer cantidadDisponible;

    @Column(name = "peso_total_disponible_kg")
    private Double pesoDisponible;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime  fechaActualizacion;

    @ManyToOne
    @JoinColumn(name = "id_detalle_compra", nullable = false)
    private DetalleCompra detalleCompra;

    @Column(name = "fecha_caducidad_estimada")
    private LocalDate fechaCaducidad;
}