package com.medisys.web.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Especialidad")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Especialidad {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEspecialidad")
    private Integer idEspecialidad;
    
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "icon_class", nullable = false, length = 100)
    private String iconClass;

    @Column(name = "costo", precision = 18, scale = 2)
    private BigDecimal costo;
}
