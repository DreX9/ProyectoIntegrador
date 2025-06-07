package com.example.integrador.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.integrador.entities.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer>{
    
}
