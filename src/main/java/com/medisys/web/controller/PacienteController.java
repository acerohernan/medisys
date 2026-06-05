package com.medisys.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.medisys.web.repository.EspecialidadRepository;

@Controller
public class PacienteController {

    private final EspecialidadRepository especialidadRepository;

    public PacienteController(EspecialidadRepository especialidadRepository) {
        this.especialidadRepository = especialidadRepository;
    }

    @GetMapping("/paciente/reserva-cita")
    public String reservaCita(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
        }
        model.addAttribute("especialidades", especialidadRepository.findAll());
        return "reserva-cita";
    }
}
