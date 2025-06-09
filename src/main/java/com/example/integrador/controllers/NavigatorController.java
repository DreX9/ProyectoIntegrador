package com.example.integrador.controllers;

 import org.springframework.stereotype.Controller; 
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.RequestMapping;

 @Controller
 @RequestMapping
 public class NavigatorController {
    
     @GetMapping("/ventas")
     public String ventas(){
         return "ventas";   
     }

     @GetMapping("/compras")
     public String compras(){
         return "compras";   
     }

     @GetMapping("/inventarios")
     public String inventarios(){
         return "inventarios";   
     }

     @GetMapping("/reportes")
     public String reportes(){
         return "reportes";   
     }
 }
