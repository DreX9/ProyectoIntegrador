package com.example.integrador.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.integrador.DTO.EstadisticaDTO;
import com.example.integrador.services.VentaService;
import lombok.RequiredArgsConstructor;

import org.springframework.ui.Model;

@Controller
@RequestMapping("reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final VentaService ventaService;

    @GetMapping
    public String verEstadisticas(
            @RequestParam(name = "periodo", required = false, defaultValue = "semanal") String periodo,
            Model model) {

        EstadisticaDTO dto = ventaService.obtenerEstadisticasPorPeriodo(periodo); // usa el parámetro

        model.addAttribute("productoMasVendido", dto.getProductoMasVendido());
        model.addAttribute("gananciaTotal", dto.getGananciaTotal());

        model.addAttribute("labels", dto.getEtiquetas());      // Para las gráficas
        model.addAttribute("valores", dto.getIngresos());      // Para gráfico pastel
        model.addAttribute("cantidades", dto.getCantidades()); // Para gráfico barras
        model.addAttribute("periodoSeleccionado", periodo);    // Para marcar el botón seleccionado

        return "reportes";
    }
}