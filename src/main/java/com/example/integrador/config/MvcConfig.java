package com.example.integrador.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer{
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        WebMvcConfigurer.super.addViewControllers(registry);
        registry.addViewController("/").setViewName("productos"); 

        // Agregar controladores para las diferentes vistas
        registry.addViewController("/almacenes").setViewName("almacenes");
        registry.addViewController("/productos").setViewName("productos");
        registry.addViewController("/clientes").setViewName("clientes");
        registry.addViewController("/proveedores").setViewName("proveedores");
        registry.addViewController("/inventarios").setViewName("inventarios");
        registry.addViewController("/compras").setViewName("templates/compras");
        registry.addViewController("/ventas").setViewName("templates/ventas");
        registry.addViewController("/reportes").setViewName("templates/reportes");
        registry.addViewController("/usuarios").setViewName("templates/usuarios");
    }
}
