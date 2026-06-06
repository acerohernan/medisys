package com.medisys.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.medisys.web.entity.Paciente;
import com.medisys.web.entity.Usuario;
import com.medisys.web.repository.CitaRepository;
import com.medisys.web.repository.EspecialidadRepository;
import com.medisys.web.repository.MedicoRepository;
import com.medisys.web.repository.PacienteRepository;
import com.medisys.web.repository.UsuarioRepository;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class PacienteController {

    private final EspecialidadRepository especialidadRepository;
    private final MedicoRepository medicoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CitaRepository citaRepository;
    private final PacienteRepository pacienteRepository;

    @GetMapping("/paciente")
    public String index(Authentication authentication, Model model) {
        String username = authentication.getName();
        Usuario usuario = usuarioRepository.findByCorreo(username).orElse(null);

        if (usuario == null)
            return "redirect:/login";

        Paciente paciente = pacienteRepository.findByUsuario(usuario).orElse(null);

        if (paciente == null)
            return "redirect:/login";

        model.addAttribute("citas", citaRepository.findByPaciente(paciente));

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
