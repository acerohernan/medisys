package com.medisys.web.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import jakarta.persistence.PrePersist;

@Entity
@Table(name = "Cita")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cita {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCita")
    private Integer idCita;
    
    @ManyToOne
    @JoinColumn(name = "idPaciente")
    private Paciente paciente;
    
    @ManyToOne
    @JoinColumn(name = "idMedico")
    private Medico medico;
    
    @Column(name = "fechaHora")
    private LocalDateTime fechaHora;
    
    @Column(name = "estado", length = 20)
    private String estado = "Programada";
    
    @Column(name = "motivo", length = 255)
    private String motivo;
    
    @Column(name = "observaciones", length = 255)
    private String observaciones;

    @Column(name = "sede", length = 200)
    private String sede;

    @Column(name = "costo", precision = 10, scale = 2)
    private BigDecimal costo;

    @Column(name = "fechaCreacion")
    private LocalDateTime fechaCreacion;

    @PrePersist
    protected void onCreate() {
        if (fechaCreacion == null) {
            fechaCreacion = LocalDateTime.now();
        }
    }
}
