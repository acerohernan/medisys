package com.medisys.web.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "Medico")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMedico")
    private Integer idMedico;
    
    @OneToOne
    @JoinColumn(name = "idUsuario", unique = true)
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name = "idEspecialidad")
    private Especialidad especialidad;
    
    @Column(name = "numeroColegiatura", length = 50)
    private String numeroColegiatura;
    
    @Column(name = "telefono", length = 15)
    private String telefono;

    @Column(name = "anios_experiencia")
    private Integer aniosExperiencia;

    @Column(name = "foto_url", length = 255)
    private String fotoUrl;

    @Column(name = "valoracion", precision = 3, scale = 2)
    private BigDecimal valoracion;

    @Column(name = "disponibilidad", length = 100)
    private String disponibilidad;
}
