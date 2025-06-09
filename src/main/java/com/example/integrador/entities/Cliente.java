package com.example.integrador.entities;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Integer id;
    @Column(name = "nombre_clasificacion", nullable = false)
    @NotBlank(message="El nombre es obligatorio")
    @Size(max=100, message="El nombre debe tener menos de 100 caracteres")
    private String nombre;
    @Column(nullable = false)
    @NotBlank(message="El apellido es obligatorio")
    @Size(max=100, message="El apellido debe tener menos de 100 caracteres")
    private String apellido;
    @Column(nullable = false)
    @NotBlank(message="El correo es obligatorio")
    private String correo;
    @Column(nullable = false)
    @NotBlank(message="El telefono es obligatorio")
    @Size(max=9, message="El telefono debe tener menos de 10 caracteres")
    private String telefono;
    @Column(nullable = false)
    @NotBlank(message="El dni es obligatorio")
    @Size(max=8, message="El dni debe tener menos de 9 caracteres")
    private String dni;
    @Column(nullable = false)
    @NotBlank(message="La actividad es obligatoria")
    private String actividad;
    @Column( insertable = false, updatable = false,nullable = false)
    private LocalDate registro;
    @NotNull(message = "Ingrese la fecha")
    @PastOrPresent(message = "La fecha no puede ser futura")
    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate nacimiento;
    
}
