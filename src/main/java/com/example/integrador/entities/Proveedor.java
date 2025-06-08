package com.example.integrador.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message="El nombre es obligatorio")
    @Size(max=100, message="El nombre debe tener menos de 100 caracteres")
    @Column(name = "nombre_proveedor", nullable = false)
    private String nombre;
    @NotBlank(message="El contacto es obligatorio")
    @Size(max=100, message="El contacto debe tener menos de 100 caracteres")
    @Column(nullable = false)
    private String contacto;
    @Column(nullable = false)
    @NotBlank(message="El telefono es obligatorio")
    @Size(max=9, message="El telefono debe tener menos de 10 caracteres")
    private String telefono;
    @NotBlank(message="La direccion es obligatoria")
    @Size(max=300, message="La direccion debe tener menos de 500 caracteres")
    @Column(nullable = false)
    private String direccion;
    @NotBlank(message="El RUC es obligatorio")
    @Size(max=100, message="El RUC debe tener menos de 100 caracteres")
    @Column(nullable = false)
    private String ruc;
    @NotBlank(message="La matrícula es obligatoria")
    @Size(max=50, message="La matrícula debe tener menos de 50 caracteres")
    @Column(name = "matricula_embarcacion",nullable = false)
    private String matricula;
    @NotBlank(message="El puerto es obligatorio")
    @Size(max=100, message="El puerto debe tener menos de 100 caracteres")
    @Column(name = "puerto_origen",nullable = false)
    private String puerto;
    @NotBlank(message="El tipo es obligatorio")
    @Column(name = "tipo_proveedor", nullable = false)
    private String tipo;
    @Column(name = "fecha_registro" , insertable = false, updatable = false, nullable = false)
    private LocalDate registro;
}
