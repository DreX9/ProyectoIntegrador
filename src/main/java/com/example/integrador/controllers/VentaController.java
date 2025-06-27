package com.example.integrador.controllers;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.integrador.entities.Venta;
import com.example.integrador.services.AlmacenService;
import com.example.integrador.services.ClienteService;
import com.example.integrador.services.InventarioService;
import com.example.integrador.services.VentaService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("ventas")
@RequiredArgsConstructor
public class VentaController {
    private final VentaService ventaService;
    private final InventarioService inventarioService; // Para mostrar inventario disponible
    private final ClienteService clienteService; // Para futura búsqueda de cliente
    private final AlmacenService almacenService; // Por si quieres mostrar almacenes
    // private final UsuarioService usuarioService; // Más adelante si manejas
    // usuario logueado

    @GetMapping
    public String formularioVenta(Model model) {
        Venta venta = new Venta();
        venta.setFechaVenta(LocalDate.now());
        venta.setDetalles(new ArrayList<>()); // Necesario para binding en el formulario

        model.addAttribute("venta", venta);
        model.addAttribute("inventarios", inventarioService.listarInventarioDisponible());
        model.addAttribute("clientes", clienteService.clienteSel()); // puede quedar vacío de momento
        model.addAttribute("almacenes", almacenService.almacenSel());
        return "ventas"; // Tu archivo Thymeleaf: ventas.html
    }

    @PostMapping("/save")
    public String guardarVenta(@ModelAttribute Venta venta, Model model) {
        try {
            ventaService.registrarVenta(venta);
            return "redirect:/ventas";
        } catch (RuntimeException ex) {
            // Enviamos el mensaje de error al HTML
            model.addAttribute("error", ex.getMessage());

            // Reenviamos los datos necesarios para recargar el formulario correctamente
            model.addAttribute("venta", venta);
            model.addAttribute("inventarios", inventarioService.listarInventarioDisponible());
            model.addAttribute("clientes", clienteService.clienteSel());
            model.addAttribute("almacenes", almacenService.almacenSel());

            return "ventas"; // Vuelve a cargar el formulario con el mensaje
        }
    }

}
