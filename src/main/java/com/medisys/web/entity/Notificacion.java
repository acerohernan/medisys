package com.medisys.web.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "Notificacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notificacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idNotificacion")
    private Integer idNotificacion;
    
    @ManyToOne
    @JoinColumn(name = "idPaciente")
    private Paciente paciente;
    
    @Column(name = "mensaje", length = 255)
    private String mensaje;
    
    @Column(name = "tipo", length = 20)
    private String tipo;
    
    @Column(name = "estado", length = 20)
    private String estado = "Pendiente";
    
    @Column(name = "fechaEnvio")
    private LocalDateTime fechaEnvio;
}
