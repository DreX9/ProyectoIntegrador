package com.example.integrador.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.integrador.entities.DetalleVenta;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta,Integer>{
    
}
