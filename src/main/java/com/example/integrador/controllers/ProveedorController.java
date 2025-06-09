package com.example.integrador.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.integrador.entities.Proveedor;
import com.example.integrador.services.ProveedorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("proveedores")
@RequiredArgsConstructor
public class ProveedorController {
    
     private final ProveedorService service;

    @GetMapping
    public String listaProveedores(Model model){
        model.addAttribute("lista",service.proveedorSel());   
        model.addAttribute("proveedor", new Proveedor());
        return "proveedores";
    }
    @PostMapping("/save")
    public String guardarProveedor(@Valid @ModelAttribute Proveedor proveedor, BindingResult result, Model model){
        if (result.hasErrors()) {
            //Aseguras de que el objeto  clasificacion esté en el modelo
            model.addAttribute("clasificacion", proveedor);
            model.addAttribute("lista",service.proveedorSel());   
            return "proveedores";
        }
        service.proveedorInsertUpdate(proveedor);
        return "redirect:/proveedores";
    }
    @GetMapping("/edit")
    public String editarProveedor(@RequestParam("id") Integer id, Model model){
        model.addAttribute("proveedor", service.proveedorSelectOne(id));
        model.addAttribute("lista", service.proveedorSel());
        return "proveedores";
    }
    @PostMapping("/delete")
    public String eliminarProveedor(@RequestParam("id") Integer id) {
        service.proveedorDelete(id);
        return "redirect:/proveedores";
    }
}
