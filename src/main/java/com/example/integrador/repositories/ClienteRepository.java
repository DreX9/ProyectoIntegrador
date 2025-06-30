package com.example.integrador.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.integrador.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente,Integer>{
    Optional<Cliente> findByDniOrNombreContainingIgnoreCase(String dni, String nombre);

}
