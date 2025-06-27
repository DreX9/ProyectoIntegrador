package com.example.integrador.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Column(name = "nombre_clasificacion", nullable = false)
    @NotBlank(message="El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;
    @Column(name = "tipo_presentacion", nullable = false)
    @NotBlank(message="La presentación es obligatoria")    
    @Size(min = 2, max = 50, message = "La presentación debe tener entre 2 y 50 caracteres")
    private String  presentacion;
    @Column(name = "peso_maximo_kg")
    @NotNull(message = "Integrese un peso valido")
    @Min(value = 5)
    private Double peso;
}
