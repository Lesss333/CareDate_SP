package org.example.com.caredate.repository;

import org.example.com.caredate.model.entity.DocumentoMedico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentoMedicoRepository extends JpaRepository<DocumentoMedico, Long> {
    List<DocumentoMedico> findByPacienteIdOrderByFechaSubidaDesc(Long pacienteId);
    List<DocumentoMedico> findByPacienteCorreoOrderByFechaSubidaDesc(String correo);
}