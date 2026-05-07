package org.example.com.caredate.model.service;

import org.example.com.caredate.model.dto.NotaMedicaDTO;
import org.example.com.caredate.model.entity.Cita;
import org.example.com.caredate.model.entity.NotaMedica;
import org.example.com.caredate.repository.CitaRepository;
import org.example.com.caredate.repository.NotaMedicaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotaMedicaService {

    private final NotaMedicaRepository notaRepo;
    private final CitaRepository citaRepo;

    public NotaMedicaService(NotaMedicaRepository notaRepo, CitaRepository citaRepo) {
        this.notaRepo = notaRepo;
        this.citaRepo = citaRepo;
    }

    public NotaMedica crear(Long citaId, NotaMedicaDTO dto) {
        if (notaRepo.findByCitaId(citaId).isPresent()) {
            throw new RuntimeException("La cita ya tiene nota médica");
        }

        Cita cita = citaRepo.findById(citaId)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        NotaMedica nota = new NotaMedica();
        nota.setCita(cita);
        nota.setDescripcion(dto.getDescripcion());
        nota.setDiagnostico(dto.getDiagnostico());
        nota.setTratamiento(dto.getTratamiento());
        nota.setFechaCreacion(LocalDateTime.now());

        return notaRepo.save(nota);
    }

    public NotaMedica obtenerPorCita(Long citaId) {
        return notaRepo.findByCitaId(citaId)
                .orElseThrow(() -> new RuntimeException("No existe nota médica para esta cita"));
    }

    public NotaMedica actualizar(Long id, NotaMedicaDTO dto) {
        NotaMedica nota = notaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Nota médica no encontrada"));

        nota.setDescripcion(dto.getDescripcion());
        nota.setDiagnostico(dto.getDiagnostico());
        nota.setTratamiento(dto.getTratamiento());

        return notaRepo.save(nota);
    }
}