package com.medisys.web.config;

import com.medisys.web.service.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Proveedor de autenticacion personalizado que implementa mejores practicas de seguridad
 * - Logging detallado de intentos de autenticacion
 * - Validacion adicional de credenciales
 * - Manejo seguro de errores de autenticacion
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(
            CustomUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        // Log del intento de autenticacion
        logger.info("Intento de autenticacion para usuario: {}", username);

        // Validar que el usuario existe
        UserDetails userDetails;
        try {
            userDetails = userDetailsService.loadUserByUsername(username);
        } catch (Exception e) {
            logger.warn("Usuario no encontrado o bloqueado: {}", username);
            throw new BadCredentialsException("Usuario o contraseña inválidos", e);
        }

        // Validar que el usuario esta habilitado
        if (!userDetails.isEnabled()) {
            logger.warn("Intento de login con cuenta deshabilitada: {}", username);
            throw new BadCredentialsException("Usuario o contraseña inválidos");
        }

        // Validar contraseña
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            logger.warn("Contraseña incorrecta para usuario: {}", username);
            throw new BadCredentialsException("Usuario o contraseña inválidos");
        }

        // Validar que la cuenta no esta expirada
        if (!userDetails.isAccountNonExpired()) {
            logger.warn("Intento de login con cuenta expirada: {}", username);
            throw new BadCredentialsException("Cuenta expirada");
        }

        // Validar que la cuenta no esta bloqueada
        if (!userDetails.isAccountNonLocked()) {
            logger.warn("Intento de login con cuenta bloqueada: {}", username);
            throw new BadCredentialsException("Cuenta bloqueada");
        }

        // Validar que las credenciales no estan expiradas
        if (!userDetails.isCredentialsNonExpired()) {
            logger.warn("Intento de login con credenciales expiradas: {}", username);
            throw new BadCredentialsException("Credenciales expiradas");
        }

        // Autenticacion exitosa
        logger.info("Autenticacion exitosa para usuario: {}", username);
        
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                password,
                userDetails.getAuthorities()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
