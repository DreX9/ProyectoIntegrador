package com.example.integrador.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VentaResumenDTO {
    private Integer idVenta;
    private String nombreCliente;
    private LocalDate fecha;
    private LocalTime hora;
    private Double total;
    private String nombreUsuario;
    private String dniCliente; 
}
