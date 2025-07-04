package com.example.integrador.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.integrador.entities.Producto;
import com.example.integrador.services.ClasificacionService;
import com.example.integrador.services.ProductoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("productos")
@RequiredArgsConstructor
public class ProductoController {
    private final ClasificacionService clasificacionService;
    private final ProductoService service;

    @GetMapping
    public String listaProducto(Model model){
        model.addAttribute("lista", service.productoSel());
        model.addAttribute("producto", new Producto());
        model.addAttribute("clasificaciones", clasificacionService.clasificacionSel());
        return "productos";
    }
    @PostMapping("/save")
    public String guardarProducto(@Valid @ModelAttribute Producto producto,BindingResult result, Model model){
        if (result.hasErrors()) {
            //Aseguras de que el objeto  clasificacion esté en el modelo
            model.addAttribute("clasificacion", producto);
            model.addAttribute("lista",service.productoSel());   
            return "productos";
        }
        service.productoInsertUpdate(producto);
        return "redirect:/productos";
    }
    @GetMapping("/edit")
    public String editarProducto(@RequestParam("id") Integer id, Model model){
        model.addAttribute("producto", service.productoSelectOne(id));
        model.addAttribute("lista", service.productoSel());
        model.addAttribute("clasificaciones", clasificacionService.clasificacionSel());
        return "productos";
    }
    @PostMapping("/delete")
    public String eliminarProducto(@RequestParam("id") Integer id) {
        service.productoDelete(id);
        return "redirect:/productos";
    }
}
