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
    private String nombre;//
    private String apellido;//
    private String correo;//
    private String telefono;//
    private String dni;//
    @Column(name = "usuario")
    private String usuarioName;//
    @Column(name = "contraseña")
    private String contrasena;//
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "fecha_nacimiento")
    private LocalDate nacimiento;
    @Column(name = "fecha_registro", insertable = false, updatable = false)
    private LocalDate registro;
    @ManyToOne
    @JoinColumn(name = "id_rol")
    private Rol rol;

}
