package com.example.integrador.services;

import java.util.List;

import org.springframework.stereotype.Service;
import com.example.integrador.entities.StockProducto;
import com.example.integrador.repositories.VistaStockProducto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockProductoService {
    private final VistaStockProducto repository;
    public List<StockProducto> stockProductoSel() {
        return repository.findAll(); 
    }
}
