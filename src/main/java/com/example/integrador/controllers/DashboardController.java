package com.example.integrador.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.integrador.services.ClienteService;
import com.example.integrador.services.CompraService;
import com.example.integrador.services.ProductoService;
import com.example.integrador.services.VentaService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final VentaService ventaService;
    private final ClienteService clienteService;
    private final ProductoService productoService;
    private final CompraService compraService;

    @GetMapping
    public String verDashboard(Model model) {
        model.addAttribute("totalVentas", ventaService.contarVentas());
        model.addAttribute("gananciaTotal", ventaService.obtenerGananciaTotal());
        model.addAttribute("productoMasVendido", ventaService.obtenerProductoMasVendido());
        model.addAttribute("totalClientes", clienteService.contarClientes());
        model.addAttribute("totalProductos", productoService.contarProductos());
        model.addAttribute("totalCompras", compraService.contarCompras());
        return "dashboard";
    }
}
