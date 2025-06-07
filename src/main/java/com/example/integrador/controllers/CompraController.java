package com.example.integrador.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.integrador.entities.Compra;
import com.example.integrador.entities.DetalleCompra;
import com.example.integrador.services.CompraService;
import com.example.integrador.services.ProveedorService;

import lombok.RequiredArgsConstructor;

// @Controller
// @RequestMapping("compras")
// @RequiredArgsConstructor
// public class CompraController {
//     private final CompraService compraService;
//     private final ProveedorService proveedorService;

//     @GetMapping
//     public String formularioCompra(Model model) {
//         model.addAttribute("compra", new Compra());
//         model.addAttribute("proveedores", proveedorService.proveedorSel());
//         return "compras";

//     }
//     @PostMapping("/save")
//     public String guardarCompra(@ModelAttribute Compra compra) {
//         compraService.compraInsert(compra);
//         return "redirect:/compras";
//     }
// }
