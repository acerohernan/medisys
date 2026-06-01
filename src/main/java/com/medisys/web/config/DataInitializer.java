package com.medisys.web.config;

import com.medisys.web.entity.Rol;
import com.medisys.web.entity.Usuario;
import com.medisys.web.repository.RolRepository;
import com.medisys.web.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;

@Component
public class DataInitializer {

    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RolRepository rolRepository,
                           UsuarioRepository usuarioRepository,
                           PasswordEncoder passwordEncoder) {
        this.rolRepository = rolRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        Rol adminRole = rolRepository.findByNombre("ADMIN").orElseGet(() -> rolRepository.save(new Rol(null, "ADMIN")));
        rolRepository.findByNombre("PACIENTE").orElseGet(() -> rolRepository.save(new Rol(null, "PACIENTE")));

        if (!usuarioRepository.existsByCorreo("admin@medisys.com")) {
            Usuario admin = new Usuario();
            admin.setNombres("Administrador");
            admin.setApellidos("Medisys");
            admin.setCorreo("admin@medisys.com");
            admin.setContrasena(passwordEncoder.encode("Admin123!"));
            admin.setRol(adminRole);
            admin.setEstado(true);
            admin.setFechaRegistro(LocalDateTime.now());
            usuarioRepository.save(admin);
        }
    }
}
