package com.example.integrador.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.integrador.entities.Cliente;
import com.example.integrador.repositories.ClienteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;
    public List<Cliente> clienteSel(){
        return repository.findAll();
    }
}
