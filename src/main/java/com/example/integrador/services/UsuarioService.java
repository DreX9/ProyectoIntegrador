package com.example.integrador.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.integrador.entities.Usuario;
import com.example.integrador.repositories.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;
    public Usuario crearUsuario(Usuario usuario) throws Exception{
        if (repository.findByUsuarioName(usuario.getUsuarioName()).isEmpty()) {
            throw new Exception("Usuario ya registrado");
        }
         usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        return repository.save(usuario);
    }
    public Usuario buscUsuarioName(String user){
        return repository.findByUsuarioName(user).orElseThrow();
    }
    // public List<Usuario> usuarioSel(){
    //     return repository.findAll();
    // }
    // public Usuario usuarioSelectOne(Integer id) {
    //     return repository.findById(id).orElse(null);
    // }
    
    // public Usuario usuarioInsertUpdate(Usuario usuario){
    //     return repository.save(usuario);
    // }
    //  public void usuarioDelete(Integer id) {
    //     repository.deleteById(id);
    // }
}
