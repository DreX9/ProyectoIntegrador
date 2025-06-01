package com.example.integrador.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.integrador.services.ClienteService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("clientes")
@RequiredArgsConstructor
public class ClienteController {
    
    private final ClienteService service;

    @GetMapping
    public String listaClientes(Model model){
        model.addAttribute("lista",service.clienteSel());   
        return "clientes";
    }
}
