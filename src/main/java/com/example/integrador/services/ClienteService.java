package com.example.integrador.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.integrador.entities.Cliente;
import com.example.integrador.repositories.ClienteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    public List<Cliente> clienteSel() {
        return repository.findAll();
    }

    public Cliente clienteSelectOne(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public Cliente clienteInsertUpdate(Cliente cliente) {
        return repository.save(cliente);
    }

    public void clienteDelete(Integer id) {
        repository.deleteById(id);
    }

    public long contarClientes() {
        return repository.count();
    }

    public Optional<Cliente> buscarPorDniONombre(String filtro) {
        return repository.findByDniOrNombreContainingIgnoreCase(filtro, filtro);
    }

    public Optional<Cliente> clienteidOptional(Integer id) {
        return repository.findById(id);
    }
}
