package com.example.integrador.controllers;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.integrador.entities.Compra;
import com.example.integrador.services.AlmacenService;
import com.example.integrador.services.CompraService;
import com.example.integrador.services.HistorialCompraService;
import com.example.integrador.services.ProductoService;
import com.example.integrador.services.ProveedorService;
import com.example.integrador.services.UsuarioService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("compras")
@RequiredArgsConstructor
public class CompraController {
    private final CompraService compraService;
    private final ProveedorService proveedorService;
    private final UsuarioService usuarioService; // Necesario si asignas usuario
    private final ProductoService productoService; // Para mostrar productos en el formulario
    private final AlmacenService almacenService;
    private final HistorialCompraService historialCompraService;

    @GetMapping
    public String formularioCompra(Model model) {
        Compra compra = new Compra();
        compra.setFechaCompra(LocalDate.now());
        compra.setDetalles(new ArrayList<>()); // Importante para el binding en el formulario

        model.addAttribute("compra", compra);
        model.addAttribute("proveedores", proveedorService.proveedorSel());
        model.addAttribute("productos", productoService.productoSel());
        model.addAttribute("almacenes", almacenService.almacenSel()); // Para seleccionar productos
        model.addAttribute("historial", historialCompraService.listarHistorial());
        return "compras"; // Thymeleaf o JSP
    }

    @PostMapping("/save")
    public String guardarCompra(@ModelAttribute Compra compra) {
        // compra.setUsuario(usuarioService.usuarioActual());

        compraService.registrarCompra(compra);
        return "redirect:/compras";
    }
}
