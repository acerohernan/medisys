package com.medisys.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MedicoController {

    @GetMapping("/medico/historial-clinico")
    public String historialClinico(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
        }
        return "historial-clinico";
    }
}
