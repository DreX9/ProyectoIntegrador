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
<<<<<<< HEAD

    // INSERT into Rol && UPDATE dinosaurio SET
=======
    
>>>>>>> 21c827f039d754efa5379edb11b9d13432e787ac
    public Rol rolInsertUpdate(Rol rol){
        return repository.save(rol);
    }
    
     public void rolDelete(Integer id) {
        repository.deleteById(id);
    }
}
