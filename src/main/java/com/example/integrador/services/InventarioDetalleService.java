package com.example.integrador.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.integrador.entities.InventarioDetalle;
import com.example.integrador.repositories.VistaInventarioDetalle;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventarioDetalleService {
    private final VistaInventarioDetalle repository;
    public List<InventarioDetalle> inventarioDetalleSel() {
        return repository.findAll(); 
    }
}
