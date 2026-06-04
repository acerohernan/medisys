package com.medisys.web.service;

import com.medisys.web.dto.RegistroDTO;
import com.medisys.web.entity.Paciente;
import com.medisys.web.entity.Rol;
import com.medisys.web.entity.Usuario;
import com.medisys.web.repository.PacienteRepository;
import com.medisys.web.repository.RolRepository;
import com.medisys.web.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PacienteRepository pacienteRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository,
                         RolRepository rolRepository,
                         PacienteRepository pacienteRepository,
                         PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.pacienteRepository = pacienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registra un nuevo usuario validando que no exista el correo
     * y que las contraseñas coincidan
     */
    public Usuario registrar(RegistroDTO registroDTO) throws Exception {
        // Validar que las contraseñas coincidan
        if (!registroDTO.getContrasena().equals(registroDTO.getConfirmarContrasena())) {
            throw new Exception("Las contraseñas no coinciden");
        }

        // Validar que el correo no exista
        if (usuarioRepository.existsByCorreo(registroDTO.getCorreo())) {
            throw new Exception("El correo ya está registrado");
        }

        // Validar que el dni no exista
        if (pacienteRepository.existsByDni(registroDTO.getDni())) {
            throw new Exception("El DNI ya está registrado");
        }

        // Obtener el rol
        Rol rol = rolRepository.findByNombre("PACIENTE")
                .orElseThrow(() -> new Exception("El rol seleccionado no existe"));

        // Crear el nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setNombres(registroDTO.getNombres());
        usuario.setApellidos(registroDTO.getApellidos());
        usuario.setCorreo(registroDTO.getCorreo());
        usuario.setContrasena(passwordEncoder.encode(registroDTO.getContrasena()));
        usuario.setRol(rol);
        usuario.setEstado(true);

        // Guardar el usuario
        usuario = usuarioRepository.save(usuario);
        log.info("Usuario registrado exitosamente: {} con rol: {}", 
                usuario.getCorreo(), rol.getNombre());

        // Crear y guardar el paciente con la información faltante
        Paciente paciente = new Paciente();
        paciente.setUsuario(usuario);
        paciente.setDni(registroDTO.getDni());
        paciente.setTelefono(registroDTO.getTelefono());
        paciente.setDireccion(registroDTO.getDireccion());
        paciente.setFechaNacimiento(registroDTO.getFechaNacimiento());
        paciente.setGenero(registroDTO.getGenero());
        pacienteRepository.save(paciente);
        log.info("Paciente creado para el usuario: {}", usuario.getCorreo());

        return usuario;
    }

    /**
     * Obtiene todos los roles disponibles
     */
    public List<Rol> obtenerTodosLosRoles() {
        return rolRepository.findAll();
    }

    /**
     * Obtiene un rol por su ID
     */
    public Optional<Rol> obtenerRolPorId(Integer idRol) {
        return rolRepository.findById(idRol);
    }

    /**
     * Verifica si un correo ya está registrado
     */
    public boolean existeCorreo(String correo) {
        return usuarioRepository.existsByCorreo(correo);
    }
}
