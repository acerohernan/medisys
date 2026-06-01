package com.medisys.web.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "ConsultaMedica")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaMedica {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idConsulta")
    private Integer idConsulta;
    
    @ManyToOne
    @JoinColumn(name = "idHistorial")
    private HistorialClinico historialClinico;
    
    @ManyToOne
    @JoinColumn(name = "idMedico")
    private Medico medico;
    
    @Column(name = "fecha")
    private LocalDateTime fecha;
    
    @Column(name = "motivoConsulta", length = 255)
    private String motivoConsulta;
    
    @Column(name = "diagnostico", length = 255)
    private String diagnostico;
    
    @Column(name = "tratamiento", length = 255)
    private String tratamiento;
    
    @Column(name = "observaciones", length = 255)
    private String observaciones;
    
    @PrePersist
    protected void onCreate() {
        if (fecha == null) {
            fecha = LocalDateTime.now();
        }
    }
}
