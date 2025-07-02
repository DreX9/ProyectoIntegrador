package com.example.integrador.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.integrador.auth.CustomUserDetailService;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SegurityConfig {
        private final CustomUserDetailService service;
        private final BCryptPasswordEncoder passwordEncoder;
        private final CustomSuccessHandler successHandler; // <--- nuevo

        @Bean
        DaoAuthenticationProvider provider() {
                DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
                provider.setUserDetailsService(service);
                provider.setPasswordEncoder(passwordEncoder);
                return provider;
        }

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http.authorizeHttpRequests(auth -> auth
                                // 📂 Recursos estáticos y páginas públicas
                                .requestMatchers("/", "/login", "/403", "/css/**", "/js/**", "/images/**").permitAll()

                                // 🧑‍💼 Usuarios: solo el ADMIN
                                .requestMatchers("/usuarios/**").hasAuthority("Administrador")

                                // 🏪 Almacenes: ADMIN y ALMACENISTA
                                .requestMatchers("/almacenes/**").hasAnyAuthority("Administrador", "Almacenista")

                                // 📦 Compras: ADMIN y ALMACENISTA
                                .requestMatchers("/compras/**").hasAnyAuthority("Administrador", "Almacenista")

                                // 🐟 Productos y clasificaciones: ADMIN y ALMACENISTA pueden ver y gestionar
                                .requestMatchers("/productos/**", "/clasificaciones/**")
                                .hasAnyAuthority("Administrador", "Almacenista")

                                // 📤 Ventas: ADMIN y VENDEDOR
                                .requestMatchers("/ventas/**").hasAnyAuthority("Administrador", "Vendedor")

                                // 👥 Clientes: ADMIN y VENDEDOR
                                .requestMatchers("/clientes/**").hasAnyAuthority("Administrador", "Vendedor")

                                // 🚚 Proveedores: ADMIN y ALMACENISTA
                                .requestMatchers("/proveedores/**").hasAnyAuthority("Administrador", "Almacenista")

                                // 📊 Inventario: solo ADMIN y ALMACENISTA
                                .requestMatchers("/inventarios/**").hasAnyAuthority("Administrador", "Almacenista")

                                // 📈 Kardex: solo ADMIN (puedes incluir almacenista si lo deseas como consulta)
                                .requestMatchers("/kardexs/**").hasAuthority("Administrador")

                                // Cualquier otra ruta requiere autenticación
                                .anyRequest().authenticated())
                                // Configuración de login
                                .formLogin(login -> login
                                                .loginPage("/login")
                                                .successHandler(successHandler)
                                                .permitAll())
                                // Configuración de logout
                                .logout(logout -> logout
                                                .logoutSuccessUrl("/login?logout")
                                                .permitAll())
                                // Manejo de errores de acceso
                                .exceptionHandling(exception -> exception
                                                .accessDeniedPage("/403"));

                return http.build();
        }
}
