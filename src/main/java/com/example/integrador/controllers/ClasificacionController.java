package com.example.integrador.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.integrador.entities.Clasificacion;
import com.example.integrador.services.ClasificacionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("clasificaciones")
@RequiredArgsConstructor
public class ClasificacionController {

    private final ClasificacionService service;

    @GetMapping
    public String listaClasificaciones(Model model){
        model.addAttribute("lista",service.clasificacionSel());   
        model.addAttribute("clasificacion", new Clasificacion());
        return "clasificaciones";
    }
    @PostMapping("/save")
    public String guardarClasificacion(@Valid @ModelAttribute Clasificacion clasificacion, BindingResult result, Model model) {
        if (result.hasErrors()) {
            //Aseguras de que el objeto  clasificacion esté en el modelo
            model.addAttribute("clasificacion", clasificacion);
            model.addAttribute("lista",service.clasificacionSel());   
            return "clasificaciones";
        }
        service.clasificacionInsertUpdate(clasificacion);
        return "redirect:/clasificaciones";
    }
    @GetMapping("/edit")
    public String editarClasificacion(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("clasificacion", service.clasificacionSelectOne(id));
        model.addAttribute("lista", service.clasificacionSel());
        return "clasificaciones";
    }
    @PostMapping("/delete")
        public String eliminarClasificacion(@RequestParam("id") Integer id) {
            service.clasificacionDelete(id);
            return "redirect:/clasificaciones";
        }
}
