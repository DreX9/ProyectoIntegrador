package com.example.integrador.entities;

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
@Table(name = "almacenes")
@Data
@NoArgsConstructor
public class Almacen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_almacen")
    private Integer id;
    @NotBlank(message="El nombre es obligatorio")
    @Size(max=100, message="El nombre debe tener menos de 100 caracteres")
    @Column(nullable = false, length = 100) //El campo no puede ser nulo, tamaño del nombre = 100
    private String nombre;
    @NotBlank(message="La descripcion es obligatoria")
    @Size(max=300, message="La descripcion debe tener menos de 300 caracteres")
    @Column(nullable = false)
    private String descripcion;
    @NotBlank(message="La direccion es obligatoria")
    @Size(max=300, message="La direccion debe tener menos de 500 caracteres")
    @Column(nullable = false)
    private String direccion;   
}
