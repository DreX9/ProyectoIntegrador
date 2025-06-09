package com.example.integrador.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class NavigatorController {

    @GetMapping ("/reportes")
    public String reportes() {
        return "reportes";
    }
    @GetMapping("/login")
    public String index() {
        return "index";
    }
}
