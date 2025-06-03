package com.example.integrador.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.integrador.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
    
}
