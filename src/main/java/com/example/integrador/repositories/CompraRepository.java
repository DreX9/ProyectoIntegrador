package com.example.integrador.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.integrador.entities.Compra;

public interface CompraRepository extends JpaRepository<Compra, Integer>{
    
}
