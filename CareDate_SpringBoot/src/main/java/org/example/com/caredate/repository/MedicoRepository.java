package org.example.com.caredate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.com.caredate.model.entity.Medico;
import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Optional<Medico> findByUsuarioCorreo(String correo);

    Optional<Medico> findByUsuarioId(Long usuarioId);
}