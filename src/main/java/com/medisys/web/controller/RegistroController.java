package com.medisys.web.controller;

import com.medisys.web.dto.RegistroDTO;
import com.medisys.web.entity.Rol;
import com.medisys.web.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import com.medisys.web.config.RouteRedirectHelper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para gestionar el registro de nuevos usuarios
 */
@Controller
@RequestMapping("/registro")
@Slf4j
public class RegistroController {

    private final UsuarioService usuarioService;

    public RegistroController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * GET /registro - Muestra el formulario de registro
     */
    @GetMapping
    public String mostrarFormularioRegistro(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()
                && authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ANONYMOUS"))) {
            return "redirect:" + RouteRedirectHelper.getRedirectFor(authentication);
        }

        model.addAttribute("registroDTO", new RegistroDTO());
        
        // Obtener todos los roles disponibles
        List<Rol> roles = usuarioService.obtenerTodosLosRoles();
        model.addAttribute("roles", roles);
        
        log.info("Formulario de registro mostrado");
        return "registro";
    }

    /**
     * POST /registro - Procesa el registro de un nuevo usuario
     */
    @PostMapping
    public String registrarUsuario(
            Authentication authentication,
            @Valid @ModelAttribute RegistroDTO registroDTO,
            BindingResult bindingResult,
            Model model
    ) {
        if (authentication != null && authentication.isAuthenticated()
                && authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ANONYMOUS"))) {
            return "redirect:" + RouteRedirectHelper.getRedirectFor(authentication);
        }
        // Si hay errores de validación, volver a mostrar el formulario
        if (bindingResult.hasErrors()) {
            log.warn("Errores de validación en el registro: {}", bindingResult.getAllErrors());
            List<Rol> roles = usuarioService.obtenerTodosLosRoles();
            model.addAttribute("roles", roles);
            return "registro";
        }

        try {
            // Intentar registrar el usuario
            usuarioService.registrar(registroDTO);
            model.addAttribute("successMessage", "¡Registro exitoso! Por favor, inicia sesión con tus credenciales.");
            log.info("Usuario registrado exitosamente: {}", registroDTO.getCorreo());
            return "redirect:/login?registered=true";
        } catch (Exception e) {
            log.error("Error al registrar usuario: {}", e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
            
            // Volver a mostrar el formulario con el mensaje de error
            List<Rol> roles = usuarioService.obtenerTodosLosRoles();
            model.addAttribute("roles", roles);
            return "registro";
        }
    }

    /**
     * Validación AJAX para verificar disponibilidad de correo
     */
    @PostMapping("/verificar-correo")
    @ResponseBody
    public boolean verificarCorreo(@RequestParam String correo) {
        boolean existe = usuarioService.existeCorreo(correo);
        log.debug("Verificación de correo: {} - Existe: {}", correo, existe);
        return !existe; // Retorna true si el correo está disponible
    }
}
