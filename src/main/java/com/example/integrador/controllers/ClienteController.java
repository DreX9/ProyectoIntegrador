package com.example.integrador.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.integrador.entities.Cliente;
import com.example.integrador.services.ClienteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor; 

@Controller
@RequestMapping("clientes")
@RequiredArgsConstructor
public class ClienteController {
    
    private final ClienteService service;

    @GetMapping
    public String listaClientes(Model model){
        model.addAttribute("lista",service.clienteSel()); 
        model.addAttribute("cliente", new Cliente());  
        model.addAttribute("totalClientes", service.contarClientes());
        return "clientes";
    }
    @PostMapping("/save")
    public String guardarCliente(@Valid @ModelAttribute Cliente cliente,BindingResult result, Model model) {
        if (result.hasErrors()) {
            //Aseguras de que el objeto  clasificacion esté en el modelo
            model.addAttribute("clasificacion", cliente);
            model.addAttribute("lista",service.clienteSel());   
            return "clientes";
        }
        service.clienteInsertUpdate(cliente);
        return "redirect:/clientes";
    }

    @GetMapping("/edit")
    public String editarCliente(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("cliente", service.clienteSelectOne(id));
        model.addAttribute("lista", service.clienteSel()); 
        return "clientes"; 
    }

    @PostMapping("/delete")
    public String eliminarCliente(@RequestParam("id") Integer id) {
        service.clienteDelete(id);
        return "redirect:/clientes";
    }
}
