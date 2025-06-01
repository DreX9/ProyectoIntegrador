package com.example.integrador.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.integrador.services.ClasificacionService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("clasificaciones")
@RequiredArgsConstructor
public class ClasificacionController {

    private final ClasificacionService service;

    @GetMapping
    public String listaClasificaciones(Model model){
        model.addAttribute("lista",service.clasificacionSel());   
        return "clasificaciones";
    }
}
