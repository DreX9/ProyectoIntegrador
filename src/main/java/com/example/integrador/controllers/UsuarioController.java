package com.example.integrador.controllers;

import java.security.Principal;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.integrador.entities.Rol;
import com.example.integrador.entities.Usuario;
import com.example.integrador.services.RolService;
import com.example.integrador.services.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;
    private final RolService rolService;
    private final PasswordEncoder encoder;

    @PostMapping("/save")
    public String saveMetodoUser(@Valid @ModelAttribute Usuario usuario, BindingResult result, Model model,
            Principal principal) {

        // Validar nombre de usuario duplicado al crear o editar
        Optional<Usuario> existente = service.buscarPorUsername(usuario.getUsuarioName());

        if (usuario.getId() == null) {
            // CREAR: si ya existe un usuario con el mismo nombre
            if (existente.isPresent()) {
                result.rejectValue("usuarioName", "error.usuario", "El nombre de usuario ya está en uso");
            }
        } else {
            // EDITAR: si hay otro usuario distinto con el mismo nombre
            if (existente.isPresent() && !existente.get().getId().equals(usuario.getId())) {
                result.rejectValue("usuarioName", "error.usuario", "Ese nombre de usuario ya está en uso");
            }
        }

        if (result.hasErrors()) {
            model.addAttribute("roles", rolService.rolSel());
            model.addAttribute("lista", service.usuarioSel());
            return "usuarios";
        }

        try {
            Rol rolCompleto = rolService.rolSelectOne(usuario.getRol().getId());
            usuario.setRol(rolCompleto);

            if (usuario.getId() != null) {
                // Editando
                Usuario existenteBD = service.usuarioFindById(usuario.getId());

                if (usuario.getContrasena() == null || usuario.getContrasena().isBlank()) {
                    usuario.setContrasena(existenteBD.getContrasena());
                } else {
                    usuario.setContrasena(encoder.encode(usuario.getContrasena()));
                }
            } else {
                // Nuevo usuario
                usuario.setContrasena(encoder.encode(usuario.getContrasena()));
            }

            service.crearUsuario(usuario);
            return "redirect:/usuarios";

        } catch (Exception e) {
            e.printStackTrace(); // muestra error real en consola
            model.addAttribute("error", "Error al guardar usuario: " + e.getMessage());
            model.addAttribute("usuario", usuario);
            model.addAttribute("roles", rolService.rolSel());
            model.addAttribute("lista", service.usuarioSel());
            return "usuarios";
        }
    }

    @GetMapping
    public String listaUsuarios(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", rolService.rolSel());
        model.addAttribute("lista", service.usuarioSel()); // <-- agregar esta línea
        return "usuarios";
    }

    @GetMapping("/edit")
    public String editUsuario(@RequestParam("id") Integer id, Model model) {
        Usuario user = service.usuarioFindById(id); // crea este método en el service
        model.addAttribute("usuario", user);
        model.addAttribute("roles", rolService.rolSel());
        model.addAttribute("lista", service.usuarioSel());
        return "usuarios";
    }

    @PostMapping("/delete")
    public String eliminarUsuario(@RequestParam("id") Integer id) {
        service.eliminarUsuario(id);
        return "redirect:/usuarios";
    }
    // @PostMapping("/save")
    // public String guardarUsuario(@Valid @ModelAttribute Usuario usuario,
    // BindingResult result, Model model) {
    // if (result.hasErrors()) {
    // //Aseguras de que el objeto clasificacion esté en el modelo
    // model.addAttribute("clasificacion", usuario);
    // model.addAttribute("lista",service.usuarioSel());
    // return "clientes";
    // }
    // service.usuarioInsertUpdate(usuario);
    // return "redirect:/usuarios";
    // }

    // @GetMapping("/edit")
    // public String editarUsuario(@RequestParam("id") Integer id, Model model) {
    // model.addAttribute("usuario", service.usuarioSelectOne(id));
    // model.addAttribute("lista", service.usuarioSel());
    // model.addAttribute("roles", rolService.rolSel());
    // return "usuarios";
    // }

    // @PostMapping("/delete")
    // public String eliminarUsuario(@RequestParam("id") Integer id) {
    // service.usuarioDelete(id);
    // return "redirect:/usuarios";
    // }
}
