package com.example.integrador.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.integrador.services.ProveedorService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("proveedores")
@RequiredArgsConstructor
public class ProveedorController {
    
     private final ProveedorService service;

    @GetMapping
    public String listaProveedores(Model model){
        model.addAttribute("lista",service.proveedorSel());   
        return "proveedores";
    }
}
