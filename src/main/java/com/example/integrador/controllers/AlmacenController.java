package com.example.integrador.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.integrador.entities.Almacen;
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
        model.addAttribute("almacen", new Almacen());
        return "almacenes";
    }

    @PostMapping("/save")
    public String guardarAlmacen(@ModelAttribute Almacen almacen){
        service.almacenInsertUpdate(almacen);
        return "redirect:/almacenes";
    }
    @GetMapping("/edit")
    public String editarAlmacen(@RequestParam("id") int id, Model model){
        model.addAttribute("almacen", service.almacenSelectOne(id));
        model.addAttribute("lista", service.almacenSel());
        return "almacenes";
    }
    @PostMapping("/delete")
    public String eliminarAlmacen(@RequestParam("id") Integer id){
        service.almacenDelete(id);
        return "redirect:/almacenes";
    }
}
