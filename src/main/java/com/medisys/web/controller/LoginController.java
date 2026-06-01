package com.medisys.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model
    ) {
        if (error != null) {
            model.addAttribute("errorMessage", "Usuario o contraseña inválidos");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "Sesión cerrada correctamente");
        }
        return "login";
    }

    @GetMapping("/")
    public String home(Authentication authentication, Model model) {
        model.addAttribute("username", authentication.getName());
        model.addAttribute("roles", authentication.getAuthorities());
        return "home";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }
}
