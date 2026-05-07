package org.example.com.caredate.model.controller;

import org.example.com.caredate.model.dto.NotaMedicaDTO;
import org.example.com.caredate.model.entity.NotaMedica;
import org.example.com.caredate.model.service.NotaMedicaService;
import org.example.com.caredate.util.ApiResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/atenciones")
public class NotaMedicaController {

    private final NotaMedicaService notaService;

    public NotaMedicaController(NotaMedicaService notaService) {
        this.notaService = notaService;
    }

    @PostMapping("/cita/{citaId}/nota-medica")
    public ApiResponse<NotaMedica> crear(@PathVariable Long citaId, @RequestBody NotaMedicaDTO dto) {
        return new ApiResponse<>(true, "Nota médica creada", notaService.crear(citaId, dto));
    }

    @GetMapping("/cita/{citaId}/nota-medica")
    public ApiResponse<NotaMedica> obtener(@PathVariable Long citaId) {
        return new ApiResponse<>(true, "Nota médica obtenida", notaService.obtenerPorCita(citaId));
    }

    @PutMapping("/nota-medica/{id}")
    public ApiResponse<NotaMedica> actualizar(@PathVariable Long id, @RequestBody NotaMedicaDTO dto) {
        return new ApiResponse<>(true, "Nota médica actualizada", notaService.actualizar(id, dto));
    }
}