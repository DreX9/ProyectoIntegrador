package com.example.integrador.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;

import com.example.integrador.entities.Cliente;
import com.example.integrador.entities.Usuario;
import com.example.integrador.entities.Venta;
import com.example.integrador.services.AlmacenService;
import com.example.integrador.services.ClienteService;
import com.example.integrador.services.InventarioService;
import com.example.integrador.services.UsuarioService;
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
    private final UsuarioService usuarioService; // Más adelante si manejas
    // usuario logueado

    @GetMapping
    public String formularioVenta(Model model) {
        Venta venta = new Venta();
        venta.setFechaVenta(LocalDate.now());
        venta.setDetalles(new ArrayList<>()); // Necesario para binding en el formulario

        model.addAttribute("venta", venta);
        model.addAttribute("inventarios", inventarioService.listarInventarioDisponibleAgrupado());
        model.addAttribute("clientes", clienteService.clienteSel()); // puede quedar vacío de momento
        model.addAttribute("almacenes", almacenService.almacenSel());
        return "ventas"; // Tu archivo Thymeleaf: ventas.html
    } 

    @PostMapping("/save")
    public String guardarVenta(@ModelAttribute Venta venta, Model model) {
        try {
            // ✅ 1. Validar cliente
            if (venta.getCliente() == null || venta.getCliente().getId() == null) {
                throw new RuntimeException("Debe seleccionar un cliente antes de guardar la venta.");
            }

            // ✅ 2. Obtener cliente real desde la base de datos
            Cliente cliente = clienteService
                    .clienteidOptional(venta.getCliente().getId())
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
            venta.setCliente(cliente);

            // ✅ 3. Obtener el correo del usuario logueado
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName(); // ← Este es el correo del usuario logueado

            // ✅ 4. Buscar el usuario por su correo
            Usuario usuario = usuarioService.buscarPorUsername(username)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            // ✅ 5. Asignar el usuario a la venta
            venta.setUsuario(usuario);

            // ✅ 6. Guardar la venta
            ventaService.registrarVenta(venta);
            return "redirect:/ventas";

        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("venta", venta);
            model.addAttribute("inventarios", inventarioService.listarInventarioDisponible());
            model.addAttribute("clientes", clienteService.clienteSel());
            model.addAttribute("almacenes", almacenService.almacenSel());
            return "ventas";
        }
    }

    @PostMapping("/buscarCliente")
    public String buscarCliente(@RequestParam("filtro") String filtro, Model model) {
        Optional<Cliente> clienteOpt = clienteService.buscarPorDniONombre(filtro);

        Venta venta = new Venta();
        venta.setFechaVenta(LocalDate.now());
        venta.setDetalles(new ArrayList<>());

        if (clienteOpt.isPresent()) {
            venta.setCliente(clienteOpt.get()); // ✅ Aquí está la clave
            model.addAttribute("cliente", clienteOpt.get());
        } else {
            model.addAttribute("error", "No se encontró un cliente con ese DNI o nombre.");
        }

        model.addAttribute("venta", venta);
        model.addAttribute("inventarios", inventarioService.listarInventarioDisponibleAgrupado());
        model.addAttribute("clientes", clienteService.clienteSel());
        model.addAttribute("almacenes", almacenService.almacenSel());

        return "ventas";
    }

}
