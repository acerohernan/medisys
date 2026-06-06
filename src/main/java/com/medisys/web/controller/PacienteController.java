package com.medisys.web.controller;

import java.util.Collections;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.medisys.web.entity.HistorialClinico;
import com.medisys.web.entity.Paciente;
import com.medisys.web.entity.Usuario;
import com.medisys.web.repository.CitaRepository;
import com.medisys.web.repository.ConsultaMedicaRepository;
import com.medisys.web.repository.EspecialidadRepository;
import com.medisys.web.repository.HistorialClinicoRepository;
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
    private final HistorialClinicoRepository historialClinicoRepository;
    private final ConsultaMedicaRepository consultaMedicaRepository;

    @GetMapping("/paciente")
    public String index(Authentication authentication, Model model) {
        String username = authentication.getName();
        Usuario usuario = usuarioRepository.findByCorreo(username).orElse(null);

        if (usuario == null)
            return "redirect:/login";

        Paciente paciente = pacienteRepository.findByUsuario(usuario).orElse(null);

        if (paciente == null)
            return "redirect:/login";

        var citas = citaRepository.findByPaciente(paciente);
        model.addAttribute("citas", citas);

        HistorialClinico historial = historialClinicoRepository.findByPaciente(paciente);
        model.addAttribute("consultas", historial != null
                ? consultaMedicaRepository.findByHistorialClinico(historial)
                : Collections.emptyList());

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
