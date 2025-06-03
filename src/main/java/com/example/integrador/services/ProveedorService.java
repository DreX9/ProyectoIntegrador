package com.example.integrador.services;

import java.util.List;

import org.springframework.stereotype.Service;
import com.example.integrador.entities.Proveedor;
import com.example.integrador.repositories.ProveedorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProveedorService {

    private final ProveedorRepository repository;
    
    public List<Proveedor> proveedorSel(){
        return repository.findAll();
    }
    public Proveedor proveedorSelectOne(Integer id) {
        return repository.findById(id).orElse(null);
    }
    
    public Proveedor proveedorInsertUpdate(Proveedor proveedor){
        return repository.save(proveedor);
    }
     public void proveedorDelete(Integer id) {
        repository.deleteById(id);
    }
}
