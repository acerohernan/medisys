package com.medisys.web.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "Paciente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paciente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPaciente")
    private Integer idPaciente;
    
    @OneToOne
    @JoinColumn(name = "idUsuario", unique = true)
    private Usuario usuario;
    
    @Column(name = "dni", unique = true, length = 8)
    private String dni;
    
    @Column(name = "telefono", length = 15)
    private String telefono;
    
    @Column(name = "direccion", length = 200)
    private String direccion;
    
    @Column(name = "fechaNacimiento")
    private LocalDate fechaNacimiento;
    
    @Column(name = "genero", length = 10)
    private String genero;
}
