package com.medisys.web.repository;

import com.medisys.web.entity.Cita;
import com.medisys.web.entity.Paciente;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {
    List<Cita> findByPaciente(Paciente paciente);
}
