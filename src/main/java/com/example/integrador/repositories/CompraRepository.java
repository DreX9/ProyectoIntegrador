package com.example.integrador.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.integrador.entities.Compra;

public interface CompraRepository extends JpaRepository<Compra, Integer>{
    @Query("SELECT COUNT(c) FROM Compra c")
    int contarCompras();
}
