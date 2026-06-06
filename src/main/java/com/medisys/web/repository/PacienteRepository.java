package com.medisys.web.repository;

import com.medisys.web.entity.Paciente;
import com.medisys.web.entity.Usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
    boolean existsByDni(String dni);

    Optional<Paciente> findByUsuario(Usuario usuario);
}
