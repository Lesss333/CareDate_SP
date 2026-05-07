package org.example.com.caredate.repository;

import org.example.com.caredate.model.entity.Receta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecetaRepository extends JpaRepository<Receta, Long> {
    Optional<Receta> findByCitaId(Long citaId);
}