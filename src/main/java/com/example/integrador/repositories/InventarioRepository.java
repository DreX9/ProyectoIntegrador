package com.example.integrador.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.integrador.entities.Inventario;

public interface InventarioRepository extends JpaRepository<Inventario, Integer>{
    
}
