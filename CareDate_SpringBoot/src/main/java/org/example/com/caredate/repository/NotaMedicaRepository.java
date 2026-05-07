package org.example.com.caredate.repository;

import org.example.com.caredate.model.entity.NotaMedica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotaMedicaRepository extends JpaRepository<NotaMedica, Long> {
    Optional<NotaMedica> findByCitaId(Long citaId);
}