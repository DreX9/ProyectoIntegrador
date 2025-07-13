package com.example.integrador.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.integrador.entities.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer>{
    @Query("SELECT COUNT(p) FROM Producto p")
    int contarProductos();
}
