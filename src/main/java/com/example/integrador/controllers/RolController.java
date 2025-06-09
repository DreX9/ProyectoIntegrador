package com.example.integrador.controllers;

import org.springframework.stereotype.Controller; 
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.integrador.entities.Rol;
import com.example.integrador.services.RolService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("roles")
@RequiredArgsConstructor
public class RolController {

    private final RolService service;

    @GetMapping
    public String listaRoles(Model model) {
        model.addAttribute("lista", service.rolSel());
        model.addAttribute("rol", new Rol());
        return "roles";
    }

    @PostMapping("/save")
    public String guardarRol(@Valid @ModelAttribute Rol rol, BindingResult result, Model model) {
        if (result.hasErrors()) {
            //Aseguras de que el objeto  clasificacion esté en el modelo
            model.addAttribute("clasificacion", rol);
            model.addAttribute("lista",service.rolSel());   
            return "roles";
        }
        service.rolInsertUpdate(rol);
        return "redirect:/roles";
    }

    @GetMapping("/edit")
    public String editarRol(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("rol", service.rolSelectOne(id));
        model.addAttribute("lista", service.rolSel()); 
        return "roles"; 
    }

    @PostMapping("/delete")
    public String eliminarRol(@RequestParam("id") Integer id) {
        service.rolDelete(id);
        return "redirect:/roles";
    }

}
