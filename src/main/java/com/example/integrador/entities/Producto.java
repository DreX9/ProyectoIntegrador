package com.example.integrador.entities;

import java.time.LocalDate;
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
@Table(name = "productos")
@Data
@NoArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer id;
    private String nombre;
    private String descripcion;
    @Column(name = "fecha_registro" , insertable = false, updatable = false)
    private LocalDate registro;
    private String estado;
    @ManyToOne
    @JoinColumn(name = "id_clasificacion")
    private Clasificacion clasificacion;
}
