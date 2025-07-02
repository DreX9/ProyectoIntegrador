package com.example.integrador.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InventarioResumenDTO {
    private Integer idInventario;
    private Integer idProducto;
    private String nombreProducto;
    private Double pesoTotalDisponible;
}
