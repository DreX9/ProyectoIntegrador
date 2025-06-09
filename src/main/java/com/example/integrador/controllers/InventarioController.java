package com.example.integrador.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.integrador.services.InventarioDetalleService;
import com.example.integrador.services.StockProductoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("inventarios")
@RequiredArgsConstructor
public class InventarioController {
    private final StockProductoService service;
    private final InventarioDetalleService detalleService;
    @GetMapping
    public String listarInventario(Model model){
        model.addAttribute("listar", service.stockProductoSel() );
        model.addAttribute("detalle", detalleService.inventarioDetalleSel() );
        return "inventarios";
    }
}
