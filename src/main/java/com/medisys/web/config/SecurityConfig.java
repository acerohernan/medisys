package com.medisys.web.config;

import com.medisys.web.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * Configuración de seguridad siguiendo las mejores prácticas de Spring Security 6+
 * Implementa autenticación segura con protección CSRF y gestión de sesiones
 */
@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * Bean de autenticacion que usa el proveedor personalizado
     * Esto permite mejor control y logging del proceso de autenticacion
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * Cadena principal de filtros de seguridad
     * Configura autorización, login, logout y manejo de excepciones
     */
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http, 
            AuthenticationProvider authenticationProvider) throws Exception {
        http
            // Configuración de autorización HTTP
            .authorizeHttpRequests(authorize -> authorize
                // Recursos públicos sin autenticación
                .requestMatchers(
                    "/css/**",
                    "/js/**",
                    "/images/**",
                    "/webjars/**",
                    "/login",
                    "/registro",
                    "/registro/**",
                    "/access-denied",
                    "/error"
                ).permitAll()
                // Rutas protegidas por rol
                .requestMatchers("/").hasRole("ADMIN")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/paciente/reserva-cita/**").hasAnyRole("PACIENTE","ADMIN")
                .requestMatchers("/api/citas/reservar").hasAnyRole("PACIENTE","ADMIN")
                .requestMatchers("/medico/historial-clinico/**").hasAnyRole("MEDICO","ADMIN")
                // Cualquier otra ruta requiere autenticación
                .anyRequest().authenticated()
            )
            // Configuración de login con form
            .formLogin(form -> form
                .loginPage("/login")                    
                .loginProcessingUrl("/login")           
                .successHandler(customAuthenticationSuccessHandler())
                .failureUrl("/login?error=true")        
                .usernameParameter("username")          
                .passwordParameter("password")          
                .permitAll()
            )
            // Configuración de logout
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)             // Invalida la sesión HTTP
                .deleteCookies("JSESSIONID")             // Elimina cookies de sesión
                .permitAll()
            )
            // Protección CSRF habilitada por defecto en Spring Security 6+
            .csrf(csrfConfigurer -> csrfConfigurer
                .ignoringRequestMatchers("/api/citas/reservar")
            )
            // Manejo de excepciones
            .exceptionHandling(exception -> exception
                .accessDeniedHandler(customAccessDeniedHandler())
            )
            // Configuración de sesiones
            .sessionManagement(session -> session
                .sessionConcurrency(concurrency -> concurrency
                    .maximumSessions(1)                  // Máximo 1 sesión por usuario
                    .expiredUrl("/login?expired=true")   // Redirección si sesión expira
                )
            )
            // Seguridad de headers HTTP
            .headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions.deny())  // Previene clickjacking
                .xssProtection(xss -> { })                          // Protección XSS
                .contentSecurityPolicy(csp -> csp
                    .policyDirectives("default-src *; script-src *; style-src *; img-src *; font-src *;")
                    .reportOnly()
                )
            )
            // Usar el AuthenticationProvider personalizado
            .authenticationProvider(authenticationProvider);

        return http.build();
    }

    /**
     * Codificador de contraseñas con BCrypt
     * Utiliza fuerza 12 para mayor seguridad
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    /**
     * Servicio de detalles de usuario personalizado
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return customUserDetailsService;
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }
}

