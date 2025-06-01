package com.example.integrador.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.integrador.entities.Almacen;
import com.example.integrador.repositories.AlamacenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlmacenService {
    
    private final AlamacenRepository repository;
    public List<Almacen> almacenSel(){
        return repository.findAll();
    }
}
