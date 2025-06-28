package com.example.integrador.entities;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer id;
    @NotBlank(message="El nombre es obligatorio")
    @Size(max=100, message="El nombre debe tener menos de 100 caracteres")
    @Column(nullable = false)
    private String nombre;//
    @Column(nullable = false)
    @NotBlank(message="El apellido es obligatorio")
    @Size(max=100, message="El apellido debe tener menos de 100 caracteres")
    private String apellido;//
    @Column(nullable = false)
    @NotBlank(message="El correo es obligatorio")
    private String correo;//
    @Column(nullable = false, unique = true)
    @NotBlank(message="El telefono es obligatorio")
    @Size(max=9, message="El telefono debe tener menos de 10 caracteres")
    private String telefono;//
    @Column(nullable = false)
    @NotBlank(message="El dni es obligatorio")
    @Size(max=8, message="El dni debe tener menos de 9 caracteres")
    private String dni;//
    @NotBlank(message="El usuario es obligatorio")
    @Size(max=50, message="El nombnre de usuario debe tener al menos 10 caracteres")
    @Column(name = "usuario", nullable = false)    
    private String usuarioName;//
    @NotBlank(message="La contraseña es obligatoria")
    @Column(name = "contraseña", nullable = false)
    private String contrasena;//
    @NotNull(message = "La fecha es obligatoria")
    @PastOrPresent(message = "La fecha no puede ser futura")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate nacimiento;
    @Column(name = "fecha_registro", insertable = false, updatable = false, nullable = false)
    private LocalDate registro;
    @ManyToOne
    @JoinColumn(name = "id_rol")
    private Rol rol;

}
