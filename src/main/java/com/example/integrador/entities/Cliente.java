package com.example.integrador.entities;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private String dni;
    private String actividad;
    @Column( insertable = false, updatable = false)
    private LocalDate registro;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate nacimiento;
    
}
