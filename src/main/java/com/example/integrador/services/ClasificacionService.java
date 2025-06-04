package com.example.integrador.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.integrador.entities.Clasificacion;
import com.example.integrador.repositories.ClasificacionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClasificacionService {
    
    private final ClasificacionRepository repository;
    public List<Clasificacion> clasificacionSel(){
        return repository.findAll();
    }
    public Clasificacion clasificacionSelectOne(Integer id) {
        return repository.findById(id).orElse(null);
    }
    
    public Clasificacion clasificacionInsertUpdate(Clasificacion clasificacion){
        return repository.save(clasificacion);
    }
     public void clasificacionDelete(Integer id) {
        repository.deleteById(id);
    }

}
