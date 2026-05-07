package org.example.com.caredate.repository;

import org.example.com.caredate.model.entity.Cita;
import org.example.com.caredate.model.enums.EstadoCita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {

    boolean existsByMedicoIdAndFecha(Long medicoId, LocalDateTime fecha);

    List<Cita> findByPaciente_CorreoOrderByFechaDesc(String correo);

    List<Cita> findByPaciente_CorreoAndEstadoInOrderByFechaDesc(String correo, List<EstadoCita> estados);

    List<Cita> findByPacienteIdOrderByFechaDesc(Long pacienteId);

    List<Cita> findByMedicoIdOrderByFechaDesc(Long medicoId);

    List<Cita> findByMedico_CorreoOrderByFechaDesc(String correo);

    List<Cita> findByMedicoCorreoOrderByFechaDesc(String correo);

    List<Cita> findByPacienteCorreoAndEstadoInOrderByFechaDesc(String correo, List<EstadoCita> estados);

    long countByEstado(EstadoCita estado);
}