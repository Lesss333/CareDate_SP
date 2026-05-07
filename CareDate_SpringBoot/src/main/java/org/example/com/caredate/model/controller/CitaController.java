package org.example.com.caredate.model.controller;

import org.example.com.caredate.model.dto.*;
import org.example.com.caredate.model.service.CitaService;
import org.example.com.caredate.util.ApiResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/citas")
@CrossOrigin
public class CitaController {

    private final CitaService service;

    public CitaController(CitaService service) {
        this.service = service;
    }

    @PostMapping
    public ApiResponse<?> crear(@RequestBody SolicitudCitaDTO dto,
                                Authentication auth) {

        return new ApiResponse<>(true, "Cita creada",
                service.crearCita(dto, auth.getName()));
    }

    @GetMapping
    public ApiResponse<?> listar() {
        return new ApiResponse<>(true, "Lista",
                service.listar());
    }

    @GetMapping("/{id}")
    public ApiResponse<?> obtener(@PathVariable Long id) {
        return new ApiResponse<>(true, "Cita",
                service.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ApiResponse<?> actualizar(@PathVariable Long id,
                                     @RequestBody ActualizarCitaDTO dto) {
        return new ApiResponse<>(true, "Actualizada",
                service.actualizar(id, dto));
    }

    @PostMapping("/{id}/confirmar")
    public ApiResponse<?> confirmar(@PathVariable Long id) {
        return new ApiResponse<>(true, "Confirmada",
                service.confirmar(id));
    }

    @PostMapping("/{id}/cancelar")
    public ApiResponse<?> cancelar(@PathVariable Long id) {
        return new ApiResponse<>(true, "Cancelada",
                service.cancelar(id));
    }

    @PostMapping("/{id}/asistio")
    public ApiResponse<?> asistio(@PathVariable Long id) {
        return new ApiResponse<>(true, "Asistió",
                service.marcarAsistio(id));
    }

    @PostMapping("/{id}/no-asistio")
    public ApiResponse<?> noAsistio(@PathVariable Long id) {
        return new ApiResponse<>(true, "No asistió",
                service.marcarNoAsistio(id));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return new ApiResponse<>(true, "Eliminada", null);
    }

    @GetMapping("/paciente")
    public ApiResponse<?> misCitasPaciente(Authentication auth) {
        return new ApiResponse<>(true, "Mis citas",
                service.obtenerMisCitasPaciente(auth.getName()));
    }

    @GetMapping("/medico")
    public ApiResponse<?> misCitasMedico(Authentication auth) {
        return new ApiResponse<>(true, "Citas médico",
                service.obtenerMisCitasMedico(auth.getName()));
    }

    @GetMapping("/historial")
    public ApiResponse<?> historial(Authentication auth) {
        return new ApiResponse<>(true, "Historial",
                service.obtenerHistorialPaciente(auth.getName()));
    }

    @GetMapping("/medicos")
    public ApiResponse<?> medicos() {
        return new ApiResponse<>(true, "Médicos",
                service.listarMedicosDisponibles());
    }

    @GetMapping("/mis-pacientes")
    public ApiResponse<?> misPacientes(Authentication auth) {
        return new ApiResponse<>(true, "Pacientes",
                service.obtenerPacientesDelMedico(auth.getName()));
    }
}