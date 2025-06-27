package com.example.integrador.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.integrador.entities.Venta;

public interface VentaRepository extends JpaRepository<Venta, Integer>{
    
}
