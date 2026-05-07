package org.example.com.caredate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.com.caredate.model.entity.Paciente;
import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    Optional<Paciente> findByUsuarioId(Long usuarioId);


}
