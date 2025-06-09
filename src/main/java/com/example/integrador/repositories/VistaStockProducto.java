package com.example.integrador.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.integrador.entities.StockProducto;

public interface VistaStockProducto extends JpaRepository<StockProducto, Integer>{
    
}
