package com.example.integrador.services;

import java.util.List;

import org.springframework.stereotype.Service;
import com.example.integrador.entities.Rol;
import com.example.integrador.repositories.RolRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RolService {

    private final RolRepository repository;
    public List<Rol> rolSel(){
        return repository.findAll();
    }
    public Rol rolSelectOne(Integer id) {
        return repository.findById(id).orElse(null);
    }
    // INSERT into Rol && UPDATE dinosaurio SET
    public Rol rolInsertUpdate(Rol rol){
        return repository.save(rol);
    }
     public void rolDelete(Integer id) {
        repository.deleteById(id);
    }
}
