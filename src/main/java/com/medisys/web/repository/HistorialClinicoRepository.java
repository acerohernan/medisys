package com.medisys.web.repository;

import com.medisys.web.entity.HistorialClinico;
import com.medisys.web.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorialClinicoRepository extends JpaRepository<HistorialClinico, Integer> {
    HistorialClinico findByPaciente(Paciente paciente);
}
