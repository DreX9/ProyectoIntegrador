package com.example.integrador.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.integrador.entities.Usuario;
import com.example.integrador.repositories.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository repository;

    public Usuario crearUsuario(Usuario usuario) throws Exception {
        return repository.save(usuario);
    }

    public Optional<Usuario> buscarPorUsername(String usuarioName) {
        return repository.findByUsuarioName(usuarioName);
    }

    public Usuario buscUsuarioName(String user) {
        return repository.findByUsuarioName(user).orElseThrow();
    }

    public List<Usuario> usuarioSel() {
        return repository.findAll();
    }

    public Usuario usuarioFindById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public void eliminarUsuario(Integer id) {
        repository.deleteById(id);
    }
    // public List<Usuario> usuarioSel(){
    // return repository.findAll();
    // }
    // public Usuario usuarioSelectOne(Integer id) {
    // return repository.findById(id).orElse(null);
    // }

    // public Usuario usuarioInsertUpdate(Usuario usuario){
    // return repository.save(usuario);
    // }
    // public void usuarioDelete(Integer id) {
    // repository.deleteById(id);
    // }
}
