package com.example.integrador.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.integrador.entities.Usuario;
import com.example.integrador.services.RolService;
import com.example.integrador.services.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
@Controller
@RequestMapping("usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    
    private final UsuarioService service;
    private final RolService rolService;

    @GetMapping
    public String listaUsuarios(Model model) {
        model.addAttribute("lista", service.usuarioSel());
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", rolService.rolSel());
        return "usuarios";
    }

    @PostMapping("/save")
    public String guardarUsuario(@Valid @ModelAttribute Usuario usuario, BindingResult result, Model model) {
        if (result.hasErrors()) {
            //Aseguras de que el objeto  clasificacion esté en el modelo
            model.addAttribute("clasificacion", usuario);
            model.addAttribute("lista",service.usuarioSel());   
            return "clientes";
        }
        service.usuarioInsertUpdate(usuario);
        return "redirect:/usuarios";
    }

    @GetMapping("/edit")
    public String editarUsuario(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("usuario", service.usuarioSelectOne(id));
        model.addAttribute("lista", service.usuarioSel()); 
        model.addAttribute("roles", rolService.rolSel());
        return "usuarios"; 
    }

    @PostMapping("/delete")
    public String eliminarUsuario(@RequestParam("id") Integer id) {
        service.usuarioDelete(id);
        return "redirect:/usuarios";
    }
}
