package com.example.integrador.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.integrador.entities.Producto;
import com.example.integrador.repositories.ProductoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository repository;
    
    public List<Producto> productoSel(){
        return repository.findAll();
    }
    public Producto productoSelectOne(Integer id) {
        return repository.findById(id).orElse(null);
    }
    
    public Producto productoInsertUpdate(Producto producto){
        return repository.save(producto);
    }
     public void productoDelete(Integer id) {
        repository.deleteById(id);
    }
}
