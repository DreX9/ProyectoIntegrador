package com.example.integrador.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.integrador.entities.HistoriaCompra;
import com.example.integrador.repositories.VistaHistorialCompraRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistorialCompraService {
    private final VistaHistorialCompraRepository vista;

    public List<HistoriaCompra> listarHistorial() {
        return vista.findAll(); 
    }
}
