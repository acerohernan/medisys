package com.medisys.web.config;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class RouteRedirectHelper {

    public static String getRedirectFor(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            return "/login";
        }

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "/";
        }
        if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_MEDICO"))) {
            return "/medico/historial-clinico";
        }
        if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_PACIENTE"))) {
            return "/paciente/reserva-cita";
        }
        return "/login";
    }
}
