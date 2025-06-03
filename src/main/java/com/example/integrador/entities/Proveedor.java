package com.example.integrador.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "proveedores")
@Data
@NoArgsConstructor
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proveedor")
    private Integer id;
    @Column(name = "nombre_proveedor")
    private String nombre;
    private String contacto;
    private String telefono;
    private String direccion;
    private String ruc;
    @Column(name = "matricula_embarcacion")
    private String matricula;	
    @Column(name = "puerto_origen")
    private String puerto;
    @Column(name = "tipo_proveedor")
    private String tipo;
    @Column(name = "fecha_registro" , insertable = false, updatable = false)
    private LocalDate registro;
}
