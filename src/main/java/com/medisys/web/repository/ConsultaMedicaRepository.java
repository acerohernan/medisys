package com.medisys.web.repository;

import com.medisys.web.entity.ConsultaMedica;
import com.medisys.web.entity.HistorialClinico;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultaMedicaRepository extends JpaRepository<ConsultaMedica, Integer> {
    List<ConsultaMedica> findByHistorialClinico(HistorialClinico historialClinico);
}
