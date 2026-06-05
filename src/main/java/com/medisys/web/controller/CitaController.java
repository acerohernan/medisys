package com.medisys.web.controller;

import com.medisys.web.entity.Cita;
import com.medisys.web.entity.HistorialClinico;
import com.medisys.web.entity.Medico;
import com.medisys.web.entity.Paciente;
import com.medisys.web.entity.Usuario;
import com.medisys.web.repository.CitaRepository;
import com.medisys.web.repository.HistorialClinicoRepository;
import com.medisys.web.repository.MedicoRepository;
import com.medisys.web.repository.PacienteRepository;
import com.medisys.web.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestController
public class CitaController {

    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;
    private final CitaRepository citaRepository;
    private final HistorialClinicoRepository historialRepository;
    private final UsuarioRepository usuarioRepository;

    public CitaController(PacienteRepository pacienteRepository,
                          MedicoRepository medicoRepository,
                          CitaRepository citaRepository,
                          HistorialClinicoRepository historialRepository,
                          UsuarioRepository usuarioRepository) {
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
        this.citaRepository = citaRepository;
        this.historialRepository = historialRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public static class ReservationRequest {
        public String specialty;
        public String doctor;
        public String doctorCMP;
        public String date;
        public String time;
        public String cost;
        public String sede;
        public Integer medicoId;
    }

    @PostMapping(path = "/api/citas/reservar", consumes = "application/json")
    public ResponseEntity<?> reservar(@RequestBody ReservationRequest req,
                                      @AuthenticationPrincipal Object principal) {
        if (req == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Datos incompletos"));
        }
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Usuario no autenticado"));
        }

        String username = null;
        if (principal instanceof org.springframework.security.core.userdetails.UserDetails userDetails) {
            username = userDetails.getUsername();
        } else if (principal instanceof String usernameStr) {
            username = usernameStr;
        }

        if (username == null || username.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Usuario no autenticado"));
        }

        Usuario usuario = usuarioRepository.findByCorreo(username).orElse(null);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Usuario autenticado no encontrado"));
        }

        Paciente paciente = pacienteRepository.findByUsuario(usuario);
        if (paciente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Paciente no encontrado para el usuario autenticado"));
        }

        Medico medico = medicoRepository.findById(req.medicoId).orElse(null);
        if (medico == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Médico no encontrado"));
        }

        // Check / create historial clinico if missing
        HistorialClinico historial = historialRepository.findByPaciente(paciente);
        if (historial == null) {
            historial = new HistorialClinico();
            historial.setPaciente(paciente);
            historial.setObservacionesGenerales("Historial creado automáticamente al reservar primera cita.");
            historialRepository.save(historial);
        }

        // Parse date and time
        LocalDateTime fechaHora;
        try {
            String[] parts = req.date.split("-");
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);

            // parse time like "09:30 AM"
            DateTimeFormatter tf = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);
            java.time.LocalTime lt = java.time.LocalTime.parse(req.time, tf);
            fechaHora = LocalDateTime.of(year, month, day, lt.getHour(), lt.getMinute());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Formato de fecha/hora inválido"));
        }

        Cita cita = new Cita();
        cita.setPaciente(paciente);
        cita.setMedico(medico);
        cita.setFechaHora(fechaHora);
        cita.setEstado("Programada");
        cita.setSede(req.sede);
        try {
            cita.setCosto(new BigDecimal(req.cost));
        } catch (Exception e) {
            cita.setCosto(BigDecimal.ZERO);
        }

        citaRepository.save(cita);

        Map<String, Object> resp = new HashMap<>();
        resp.put("message", "Cita creada");
        resp.put("idCita", cita.getIdCita());
        return ResponseEntity.ok(resp);
    }
}
