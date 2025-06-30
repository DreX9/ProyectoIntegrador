package com.example.integrador.config;

import org.springframework.boot.autoconfigure.graphql.GraphQlProperties.Http;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                http.authorizeHttpRequests(
                                auth -> auth
                                                // paginas publicas
                                                .requestMatchers("/", "/login", "/403", "/css/**", "/js/**",
                                                                "/images/**")
                                                .permitAll()
                                                // primero la más específica
                                                .requestMatchers(HttpMethod.POST, "/usuarios/save")
                                                .hasAnyAuthority("Administrador", "Almacenista")

                                                // luego la general
                                                .requestMatchers("/usuarios/**")
                                                .hasAnyAuthority("Administrador", "Almacenista")

                                                // resto de páginas por rol
                                                .requestMatchers("/compras").hasAuthority("Administrador")
                                                .requestMatchers("/ventas").hasAuthority("Vendedor")
                                                .requestMatchers("/almacenes").hasAuthority("Almacenista")

                                                .anyRequest().authenticated())
                                .formLogin(login -> login
                                                .loginPage("/login") // <- tu página personalizada de login
                                                .successHandler(successHandler)
                                                .permitAll())
                                .logout(logout -> logout
                                                .logoutSuccessUrl("/login?logout")
                                                .permitAll())
                                .exceptionHandling(exception -> exception
                                                .accessDeniedPage("/403") // <- página de acceso denegado
                                );
                return http.build();
        }
}
