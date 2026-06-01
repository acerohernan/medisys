package com.medisys.web.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "HistorialClinico")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialClinico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idHistorial")
    private Integer idHistorial;
    
    @OneToOne
    @JoinColumn(name = "idPaciente", unique = true)
    private Paciente paciente;
    
    @Column(name = "fechaApertura")
    private LocalDate fechaApertura;
    
    @Column(name = "observacionesGenerales", length = 255)
    private String observacionesGenerales;
    
    @PrePersist
    protected void onCreate() {
        if (fechaApertura == null) {
            fechaApertura = LocalDate.now();
        }
    }
}
