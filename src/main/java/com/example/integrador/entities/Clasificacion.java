package com.example.integrador.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "clasificaciones")
@Data
@NoArgsConstructor
public class Clasificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_clasificacion")
    private Integer id;
    @Column(name = "nombre_clasificacion")
    private String nombre;
    @Column(name = "tipo_presentacion")
    private String  presentacion;
}
