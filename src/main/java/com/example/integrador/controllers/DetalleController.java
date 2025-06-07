package com.example.integrador.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.integrador.entities.DetalleCompra;
import com.example.integrador.services.AlmacenService;
import com.example.integrador.services.DetalleCompraService;
import com.example.integrador.services.ProductoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("compras")
@RequiredArgsConstructor
public class DetalleController {
    private final DetalleCompraService detalleService;
    private final ProductoService productoService;
    private final AlmacenService almacenService;

    @GetMapping
    public String formularioDetalleCompra(Model model) {
        model.addAttribute("detalle", new DetalleCompra());
        model.addAttribute("productos", productoService.productoSel());
        model.addAttribute("almacenes", almacenService.almacenSel());
        return "compras"; 
    }

    @PostMapping("/save")
    public String guardarDetalleCompra(@ModelAttribute DetalleCompra detalle) {
        detalleService.DetalleCompraInset(detalle);
        return "redirect:/compras";
    }
}   
