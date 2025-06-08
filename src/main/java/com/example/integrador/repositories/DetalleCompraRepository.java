package com.example.integrador.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.integrador.entities.DetalleCompra;

public interface DetalleCompraRepository extends JpaRepository <DetalleCompra, Integer>{
    
}
