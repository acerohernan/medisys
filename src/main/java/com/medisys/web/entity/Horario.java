package com.medisys.web.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalTime;

@Entity
@Table(name = "Horario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Horario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idHorario")
    private Integer idHorario;
    
    @ManyToOne
    @JoinColumn(name = "idMedico")
    private Medico medico;
    
    @Column(name = "dia", length = 20)
    private String dia;
    
    @Column(name = "horaInicio")
    private LocalTime horaInicio;
    
    @Column(name = "horaFin")
    private LocalTime horaFin;
}
