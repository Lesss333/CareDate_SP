package org.example.com.caredate.model.service;

import org.example.com.caredate.model.dto.HistorialClinicoDTO;
import org.example.com.caredate.model.entity.*;
import org.example.com.caredate.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistorialClinicoService {

    private final CitaRepository citaRepo;
    private final NotaMedicaRepository notaRepo;
    private final RecetaRepository recetaRepo;
    private final DocumentoMedicoRepository docRepo;

    public HistorialClinicoService(CitaRepository citaRepo,
                                   NotaMedicaRepository notaRepo,
                                   RecetaRepository recetaRepo,
                                   DocumentoMedicoRepository docRepo) {
        this.citaRepo = citaRepo;
        this.notaRepo = notaRepo;
        this.recetaRepo = recetaRepo;
        this.docRepo = docRepo;
    }

    public List<HistorialClinicoDTO> obtenerHistorialCompleto(String correo) {

        List<Cita> citas = citaRepo.findByMedico_CorreoOrderByFechaDesc(correo);

        return citas.stream().map(cita -> {

            HistorialClinicoDTO dto = new HistorialClinicoDTO();

            dto.setCitaId(cita.getId());
            dto.setFecha(cita.getFecha());
            dto.setEstado(cita.getEstado().name());

            if (cita.getMedico() != null) {
                dto.setMedicoNombre(cita.getMedico().getCorreo());
            }

            // Nota médica
            notaRepo.findByCitaId(cita.getId()).ifPresent(nota -> {
                dto.setDescripcion(nota.getDescripcion());
                dto.setDiagnostico(nota.getDiagnostico());
                dto.setTratamiento(nota.getTratamiento());
            });

            // Receta
            recetaRepo.findByCitaId(cita.getId()).ifPresent(receta -> {
                dto.setMedicamento(receta.getMedicamento());
                dto.setDosis(receta.getDosis());
                dto.setIndicaciones(receta.getIndicaciones());
            });

            // Documentos
            List<String> documentos = docRepo.findByPacienteIdOrderByFechaSubidaDesc(
                            cita.getPaciente().getId()
                    ).stream().map(DocumentoMedico::getNombreArchivo)
                    .collect(Collectors.toList());

            dto.setDocumentos(documentos);

            return dto;

        }).toList();
    }
}