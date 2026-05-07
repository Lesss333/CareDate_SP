package org.example.com.caredate.model.service;

import org.example.com.caredate.model.dto.RecetaDTO;
import org.example.com.caredate.model.entity.Cita;
import org.example.com.caredate.model.entity.Receta;
import org.example.com.caredate.repository.CitaRepository;
import org.example.com.caredate.repository.RecetaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RecetaService {

    private final RecetaRepository recetaRepo;
    private final CitaRepository citaRepo;

    public RecetaService(RecetaRepository recetaRepo, CitaRepository citaRepo) {
        this.recetaRepo = recetaRepo;
        this.citaRepo = citaRepo;
    }

    public Receta crear(Long citaId, RecetaDTO dto) {
        if (recetaRepo.findByCitaId(citaId).isPresent()) {
            throw new RuntimeException("La cita ya tiene receta");
        }

        Cita cita = citaRepo.findById(citaId)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        Receta receta = new Receta();
        receta.setCita(cita);
        receta.setMedicamento(dto.getMedicamento());
        receta.setDosis(dto.getDosis());
        receta.setIndicaciones(dto.getIndicaciones());
        receta.setFechaCreacion(LocalDateTime.now());

        return recetaRepo.save(receta);
    }

    public Receta obtenerPorCita(Long citaId) {
        return recetaRepo.findByCitaId(citaId)
                .orElseThrow(() -> new RuntimeException("No existe receta para esta cita"));
    }

    public Receta actualizar(Long id, RecetaDTO dto) {
        Receta receta = recetaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada"));

        receta.setMedicamento(dto.getMedicamento());
        receta.setDosis(dto.getDosis());
        receta.setIndicaciones(dto.getIndicaciones());

        return recetaRepo.save(receta);
    }
}