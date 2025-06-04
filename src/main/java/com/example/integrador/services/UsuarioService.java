package com.example.integrador.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.integrador.entities.Usuario;
import com.example.integrador.repositories.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository repository;
    public List<Usuario> usuarioSel(){
        return repository.findAll();
    }
    public Usuario usuarioSelectOne(Integer id) {
        return repository.findById(id).orElse(null);
    }
    
    public Usuario usuarioInsertUpdate(Usuario usuario){
        return repository.save(usuario);
    }
     public void usuarioDelete(Integer id) {
        repository.deleteById(id);
    }
}
