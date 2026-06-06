package com.medisys.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.medisys.web.repository.EspecialidadRepository;
import com.medisys.web.repository.MedicoRepository;

@Controller
public class PacienteController {

    private final EspecialidadRepository especialidadRepository;
    private final MedicoRepository medicoRepository;

    public PacienteController(EspecialidadRepository especialidadRepository,
                              MedicoRepository medicoRepository) {
        this.especialidadRepository = especialidadRepository;
        this.medicoRepository = medicoRepository;
    }

    @GetMapping("/paciente")
    public String index(Authentication authentication, Model model) {
        return "paciente/index";
    }

    @GetMapping("/paciente/reserva-cita")
    public String reservaCita(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
        }
        model.addAttribute("especialidades", especialidadRepository.findAll());
        model.addAttribute("medicos", medicoRepository.findAll());
        return "paciente/reserva-cita";
    }
}
