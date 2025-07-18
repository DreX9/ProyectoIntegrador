package com.example.integrador.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.integrador.entities.HistoriaCompra;
@Repository

public interface VistaHistorialCompraRepository extends JpaRepository<HistoriaCompra, Integer>{

    List<HistoriaCompra> findAll();
}