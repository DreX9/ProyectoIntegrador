package com.example.integrador;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.integrador.entities.Rol;
import com.example.integrador.entities.Usuario;
import com.example.integrador.repositories.RolRepository;
import com.example.integrador.repositories.UsuarioRepository;

@SpringBootApplication
public class IntegradorApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntegradorApplication.class, args);
	}
	@Bean
    CommandLineRunner commandLineRunner(UsuarioRepository usuarioRepository,
                                        RolRepository rolRepository,
                                        PasswordEncoder encoder) {
        return args -> {
            if (usuarioRepository.findByUsuarioName("admin").isEmpty()) {
                Usuario usuario = new Usuario();
                usuario.setNombre("Admin");
                usuario.setApellido("Principal");
                usuario.setDni("12345678");
                usuario.setCorreo("admin@mail.pe");
                usuario.setTelefono("987654321");
                usuario.setNacimiento(LocalDate.of(2000, 1, 1));
                usuario.setRegistro(LocalDate.now());
                usuario.setUsuarioName("admin");
                usuario.setContrasena(encoder.encode("admin123")); // 🔐 encriptada

                // Asignar el rol existente "Almacenista"
                Rol rolAlmacen = rolRepository.findByNombre("Almacenista")
                        .orElseThrow(() -> new RuntimeException("⚠️ El rol 'Almacenista' no existe"));
                usuario.setRol(rolAlmacen);

                usuarioRepository.save(usuario);
                System.out.println("✅ Usuario admin creado: usuarioName = 'admin', clave = 'admin123'");
            }
        };
    }
}
