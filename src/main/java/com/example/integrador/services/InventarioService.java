package com.example.integrador.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.integrador.DTO.InventarioResumenDTO;
import com.example.integrador.entities.Inventario;
import com.example.integrador.repositories.InventarioRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class InventarioService {

    private final InventarioRepository inventarioRepository;

    public List<Inventario> listarInventarioDisponible() {
        // Puedes filtrar si quieres, o devolver todo
        return inventarioRepository.findAll(); // o aplicar filtro
    }

    public List<InventarioResumenDTO> listarInventarioDisponibleAgrupado() {
        return inventarioRepository.obtenerResumenInventario();
    }
}
