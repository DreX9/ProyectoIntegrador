package com.example.integrador.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        String redirectUrl = "/"; // Valor por defecto

        for (GrantedAuthority auth : authentication.getAuthorities()) {
            String rol = auth.getAuthority();

            if (rol.equals("Administrador")) {
                redirectUrl = "/compras";
                break;
            } else if (rol.equals("Vendedor")) {
                redirectUrl = "/ventas";
                break;
            } else if (rol.equals("Almacenista")) {
                redirectUrl = "/almacenes";
                break;
            }
        }

        response.sendRedirect(redirectUrl);
    }
}