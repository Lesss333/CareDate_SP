package org.example.com.caredate.model.service;

import org.example.com.caredate.model.dto.ReporteDTO;
import org.example.com.caredate.model.entity.Cita;
import org.example.com.caredate.repository.CitaRepository;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.*;

@Service
public class ReporteService {

    private final CitaRepository citaRepo;

    public ReporteService(CitaRepository citaRepo) {
        this.citaRepo = citaRepo;
    }

    public List<ReporteDTO> citasPorMes() {

        List<Cita> citas = citaRepo.findAll();

        Map<Month, Long> conteo = new HashMap<>();

        for (Cita c : citas) {
            Month mes = c.getFecha().getMonth();
            conteo.put(mes, conteo.getOrDefault(mes, 0L) + 1);
        }

        List<ReporteDTO> resultado = new ArrayList<>();

        for (Month mes : conteo.keySet()) {
            resultado.add(new ReporteDTO(mes.name(), conteo.get(mes)));
        }

        return resultado;
    }

    public ReporteDTO mesMasDemandado() {

        return citasPorMes().stream()
                .max(Comparator.comparingLong(ReporteDTO::getTotal))
                .orElse(null);
    }
}