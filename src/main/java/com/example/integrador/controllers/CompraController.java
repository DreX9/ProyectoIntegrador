package com.example.integrador.controllers;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.Authentication;

import com.example.integrador.entities.Compra;
import com.example.integrador.entities.Usuario;
import com.example.integrador.services.AlmacenService;
import com.example.integrador.services.CompraService;
import com.example.integrador.services.HistorialCompraService;
import com.example.integrador.services.ProductoService;
import com.example.integrador.services.ProveedorService;
import com.example.integrador.services.UsuarioService;

import jakarta.validation.Valid;
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
        return "compras"; // Thymeleaf 
    }

    @PostMapping("/save")
    public String guardarCompra(@Valid @ModelAttribute Compra compra,
                            BindingResult result,
                            Model model) {
        // 1. Obtener el username del usuario logueado
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName(); // ← este es el email/username del usuario

    // 2. Buscar el usuario en la base de datos
    Usuario usuario = usuarioService.buscarPorUsername(username)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    // 3. Asignar el usuario a la compra
    compra.setUsuario(usuario);

        compraService.registrarCompra(compra);
        return "redirect:/compras";
    }
}
