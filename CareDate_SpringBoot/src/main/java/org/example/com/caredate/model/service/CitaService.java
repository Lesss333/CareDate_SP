package org.example.com.caredate.model.service;

import org.example.com.caredate.model.dto.*;
import org.example.com.caredate.model.entity.Cita;
import org.example.com.caredate.model.entity.Paciente;
import org.example.com.caredate.model.entity.Usuario;
import org.example.com.caredate.model.enums.EstadoCita;
import org.example.com.caredate.repository.CitaRepository;
import org.example.com.caredate.repository.MedicoRepository;
import org.example.com.caredate.repository.PacienteRepository;
import org.example.com.caredate.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class CitaService {

    private final CitaRepository citaRepo;
    private final UsuarioRepository usuarioRepo;
    private final PacienteRepository pacienteRepo;
    private final MedicoRepository medicoRepo;

    public CitaService(CitaRepository citaRepo,
                       UsuarioRepository usuarioRepo,
                       PacienteRepository pacienteRepo,
                       MedicoRepository medicoRepo) {
        this.citaRepo = citaRepo;
        this.usuarioRepo = usuarioRepo;
        this.pacienteRepo = pacienteRepo;
        this.medicoRepo = medicoRepo;
    }

    public RespuestaCitaDTO crearCita(SolicitudCitaDTO dto, String correo) {

        if (dto.getMedicoId() == null || dto.getFecha() == null) {
            throw new RuntimeException("Médico y fecha son obligatorios");
        }

        LocalDateTime fecha = dto.getFecha();

        if (fecha.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("No puedes agendar en el pasado");
        }

        Usuario paciente = usuarioRepo.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        Usuario medico = usuarioRepo.findById(dto.getMedicoId())
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));

        boolean conflicto = citaRepo.existsByMedicoIdAndFecha(dto.getMedicoId(), fecha);
        if (conflicto) {
            throw new RuntimeException("Horario ocupado");
        }

        Cita cita = new Cita();
        cita.setPaciente(paciente);
        cita.setMedico(medico);
        cita.setFecha(fecha);
        cita.setEstado(EstadoCita.PENDIENTE);
        cita.setMotivo(dto.getMotivo());

        return convertirADTO(citaRepo.save(cita));
    }

    public List<RespuestaCitaDTO> listar() {
        return citaRepo.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public RespuestaCitaDTO obtenerPorId(Long id) {
        Cita cita = citaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        return convertirADTO(cita);
    }

    public RespuestaCitaDTO actualizar(Long id, ActualizarCitaDTO dto) {
        Cita cita = citaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        if (cita.getEstado() == EstadoCita.CANCELADA ||
                cita.getEstado() == EstadoCita.ASISTIO ||
                cita.getEstado() == EstadoCita.NO_ASISTIO) {
            throw new RuntimeException("No se puede editar una cita finalizada");
        }

        Long medicoIdFinal = dto.getMedicoId() != null ? dto.getMedicoId() : cita.getMedico().getId();
        LocalDateTime fechaFinal = dto.getFecha() != null ? dto.getFecha() : cita.getFecha();

        if (fechaFinal.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Fecha inválida");
        }

        boolean conflicto = citaRepo.existsByMedicoIdAndFecha(medicoIdFinal, fechaFinal);
        boolean mismaCita = cita.getMedico().getId().equals(medicoIdFinal)
                && cita.getFecha().equals(fechaFinal);

        if (conflicto && !mismaCita) {
            throw new RuntimeException("Horario ocupado");
        }

        if (dto.getMedicoId() != null) {
            Usuario medico = usuarioRepo.findById(dto.getMedicoId())
                    .orElseThrow(() -> new RuntimeException("Médico no encontrado"));
            cita.setMedico(medico);
        }

        if (dto.getFecha() != null) {
            cita.setFecha(dto.getFecha());
        }

        return convertirADTO(citaRepo.save(cita));
    }

    public RespuestaCitaDTO confirmar(Long id) {
        Cita cita = obtenerEntidadPorId(id);
        cita.setEstado(EstadoCita.CONFIRMADA);
        return convertirADTO(citaRepo.save(cita));
    }

    public RespuestaCitaDTO cancelar(Long id) {
        Cita cita = obtenerEntidadPorId(id);
        cita.setEstado(EstadoCita.CANCELADA);
        return convertirADTO(citaRepo.save(cita));
    }

    public RespuestaCitaDTO marcarAsistio(Long id) {
        Cita cita = obtenerEntidadPorId(id);
        cita.setEstado(EstadoCita.ASISTIO);
        return convertirADTO(citaRepo.save(cita));
    }

    public RespuestaCitaDTO marcarNoAsistio(Long id) {
        Cita cita = obtenerEntidadPorId(id);
        cita.setEstado(EstadoCita.NO_ASISTIO);
        return convertirADTO(citaRepo.save(cita));
    }

    public void eliminar(Long id) {
        citaRepo.delete(obtenerEntidadPorId(id));
    }

    public List<RespuestaCitaDTO> obtenerMisCitasPaciente(String correo) {
        return citaRepo.findByMedico_CorreoOrderByFechaDesc(correo)
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public List<RespuestaCitaDTO> obtenerMisCitasMedico(String correo) {
        return citaRepo.findByMedicoCorreoOrderByFechaDesc(correo)
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public List<MedicoListaDTO> listarMedicosDisponibles() {
        return medicoRepo.findAll()
                .stream()
                .map(m -> new MedicoListaDTO(
                        m.getUsuario().getId(),
                        m.getNombre(),
                        m.getEspecialidad(),
                        m.getCedula(),
                        m.getExperiencia()
                ))
                .toList();
    }

    public List<RespuestaCitaDTO> obtenerHistorialPaciente(String correo) {
        List<EstadoCita> estados = List.of(
                EstadoCita.CANCELADA,
                EstadoCita.ASISTIO,
                EstadoCita.NO_ASISTIO
        );

        return citaRepo.findByPacienteCorreoAndEstadoInOrderByFechaDesc(correo, estados)
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public List<Paciente> obtenerPacientesDelMedico(String correo) {
        return citaRepo.findByMedicoCorreoOrderByFechaDesc(correo)
                .stream()
                .map(cita -> pacienteRepo.findByUsuarioId(cita.getPaciente().getId()).orElse(null))
                .filter(Objects::nonNull)
                .distinct()
                .toList();
    }

    private Cita obtenerEntidadPorId(Long id) {
        return citaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
    }

    private RespuestaCitaDTO convertirADTO(Cita cita) {
        RespuestaCitaDTO dto = new RespuestaCitaDTO();

        dto.setId(cita.getId());
        dto.setFecha(cita.getFecha());
        dto.setEstado(cita.getEstado().name());
        dto.setMotivo(cita.getMotivo());

        if (cita.getPaciente() != null) {
            dto.setPacienteId(cita.getPaciente().getId());
            dto.setPacienteCorreo(cita.getPaciente().getCorreo());

            pacienteRepo.findByUsuarioId(cita.getPaciente().getId())
                    .ifPresent(p -> dto.setPacienteNombre(
                            p.getNombre() + " " + p.getApellidoPaterno()
                    ));
        }

        if (cita.getMedico() != null) {
            dto.setMedicoId(cita.getMedico().getId());
            dto.setMedicoCorreo(cita.getMedico().getCorreo());

            medicoRepo.findByUsuarioId(cita.getMedico().getId())
                    .ifPresent(m -> {
                        dto.setMedicoNombre(m.getNombre());
                        dto.setMedicoEspecialidad(m.getEspecialidad());
                    });
        }

        return dto;
    }
}