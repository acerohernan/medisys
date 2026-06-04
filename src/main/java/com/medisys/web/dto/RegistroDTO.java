package com.medisys.web.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroDTO {
    
    @NotBlank(message = "El nombre es requerido")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombres;
    
    @NotBlank(message = "El apellido es requerido")
    @Size(min = 2, max = 100, message = "El apellido debe tener entre 2 y 100 caracteres")
    private String apellidos;
    
    @NotBlank(message = "El correo es requerido")
    @Email(message = "El correo debe ser válido")
    private String correo;
    
    @NotBlank(message = "La contraseña es requerida")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String contrasena;
    
    @NotBlank(message = "Debe confirmar la contraseña")
    private String confirmarContrasena;

    @NotBlank(message = "El DNI es requerido")
    @Pattern(regexp = "\\d{8}", message = "El DNI debe contener 8 dígitos")
    private String dni;

    @NotBlank(message = "El teléfono es requerido")
    @Pattern(regexp = "\\d{9}", message = "El teléfono debe contener 9 dígitos")
    private String telefono;

    @NotBlank(message = "La dirección es requerida")
    @Size(max = 200, message = "La dirección no puede exceder los 200 caracteres")
    private String direccion;

    @NotNull(message = "La fecha de nacimiento es requerida")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaNacimiento;

    @NotBlank(message = "El género es requerido")
    private String genero;
}
