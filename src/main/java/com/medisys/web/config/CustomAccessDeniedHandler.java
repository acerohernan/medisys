package com.medisys.web.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.util.Collection;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Authentication authentication = (Authentication) request.getUserPrincipal();
        String redirectUrl = request.getContextPath() + "/access-denied";

        if (authentication != null && authentication.isAuthenticated()) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            boolean isAdmin = authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
            boolean isPaciente = authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_PACIENTE"));
            boolean isMedico = authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_MEDICO"));

            if (isAdmin) {
                redirectUrl = request.getContextPath() + "/";
            } else if (isPaciente) {
                redirectUrl = request.getContextPath() + "/paciente/reserva-cita";
            } else if (isMedico) {
                redirectUrl = request.getContextPath() + "/medico/historial-clinico";
            }
        }

        response.sendRedirect(redirectUrl);
    }
}
