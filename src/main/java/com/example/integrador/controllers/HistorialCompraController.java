package com.example.integrador.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.integrador.services.HistorialCompraService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HistorialCompraController {
    private final HistorialCompraService historialCompraService;

    @GetMapping("/compras/historial")
    public String mostrarHistorial(Model model) {
        model.addAttribute("historial", historialCompraService.listarHistorial());
        return "historial_compras"; // archivo HTML Thymeleaf
    }
}
