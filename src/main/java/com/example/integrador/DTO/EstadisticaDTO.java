package com.example.integrador.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadisticaDTO {
    private String productoMasVendido;
    private Double gananciaTotal;
    private List<String> etiquetas; // nombres de productos
    private List<Integer> cantidades; // para gráfico de barra
    private List<Double> ingresos; // para gráfico de pastel
}
