package com.example.integrador.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.integrador.services.AlmacenService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("almacenes")
@RequiredArgsConstructor
public class AlmacenController {
    
    private final AlmacenService service;
    @GetMapping
    public String listaAlmacen(Model model){
        model.addAttribute("lista",service.almacenSel());   
        return "almacenes";
    }
}
