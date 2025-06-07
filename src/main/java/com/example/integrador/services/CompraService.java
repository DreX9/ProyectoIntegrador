package com.example.integrador.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.integrador.entities.Compra;
import com.example.integrador.repositories.CompraRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompraService {

    private final CompraRepository repository;

    
    // Ver todas las compras (opcional: para historial)
    public List<Compra> compraSel(){
        return repository.findAll();
    }

    // Ver una compra específica
    public Compra compraSelectOne(Integer id) {
        return repository.findById(id).orElse(null);
    }
    // Registrar una nueva compra
    public Compra compraInsert(Compra compra){
        return repository.save(compra);
    }

}
