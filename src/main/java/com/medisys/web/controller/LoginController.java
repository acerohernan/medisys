package com.medisys.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.medisys.web.config.RouteRedirectHelper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controlador para gestionar autenticacion y paginas publicas
 * Sigue las mejores practicas de Spring Security
 */
@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * GET /login - Muestra la pagina de inicio de sesion
     * Maneja parametros de error, logout y sesion expirada
     */
    @GetMapping("/login")
    public String login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            @RequestParam(value = "expired", required = false) String expired,
            Authentication authentication,
            Model model
    ) {
        if (authentication != null && authentication.isAuthenticated()
                && authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ANONYMOUS"))) {
            return "redirect:" + RouteRedirectHelper.getRedirectFor(authentication);
        }

        if (error != null) {
            logger.warn("Intento de login fallido");
            model.addAttribute("errorMessage", "Usuario o contraseña inválidos");
        }
        if (logout != null) {
            logger.info("Usuario cerro sesion correctamente");
            model.addAttribute("logoutMessage", "Sesión cerrada correctamente");
        }
        if (expired != null) {
            logger.warn("Sesion expirada");
            model.addAttribute("expiredMessage", "Su sesión ha expirado. Por favor, inicie sesión nuevamente");
        }
        return "login";
    }

    /**
     * GET / - Pagina de inicio (home)
     * Solo accesible para usuarios autenticados
     */
    @GetMapping("/")
    public String home(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            logger.info("Usuario autenticado: {}", authentication.getName());
            model.addAttribute("username", authentication.getName());
            model.addAttribute("roles", authentication.getAuthorities());
        }
        return "home";
    }

    /**
     * GET /access-denied - Pagina de acceso denegado (403)
     * Se muestra cuando un usuario no tiene permisos para acceder a un recurso
     */
    @GetMapping("/access-denied")
    public String accessDenied(Authentication authentication, Model model) {
        if (authentication != null) {
            logger.warn("Acceso denegado para usuario: {}", authentication.getName());
            model.addAttribute("username", authentication.getName());
        }
        return "access-denied";
    }
}
