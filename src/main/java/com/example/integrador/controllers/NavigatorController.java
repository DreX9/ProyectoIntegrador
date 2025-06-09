package com.example.integrador.controllers;

<<<<<<< HEAD
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
=======
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
>>>>>>> 3a9205c0100de1cbb35e39e2a8a075ac97623551
