package com.example.integrador.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.integrador.entities.Venta;

public interface VentaRepository extends JpaRepository<Venta, Integer> {
    List<Venta> findByFechaVentaAfter(LocalDate fecha);
}
