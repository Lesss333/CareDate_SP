package org.example.com.caredate.model.controller;

import org.example.com.caredate.model.dto.RecetaDTO;
import org.example.com.caredate.model.entity.Receta;
import org.example.com.caredate.model.service.RecetaService;
import org.example.com.caredate.util.ApiResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recetas")
public class RecetaController {

    private final RecetaService recetaService;

    public RecetaController(RecetaService recetaService) {
        this.recetaService = recetaService;
    }

    @PostMapping("/cita/{citaId}")
    public ApiResponse<Receta> crear(
            @PathVariable Long citaId,
            @RequestBody RecetaDTO dto
    ) {
        return new ApiResponse<>(true, "Receta creada correctamente",
                recetaService.crear(citaId, dto));
    }


    @GetMapping("/cita/{citaId}")
    public ApiResponse<Receta> obtenerPorCita(@PathVariable Long citaId) {
        return new ApiResponse<>(true, "Receta encontrada",
                recetaService.obtenerPorCita(citaId));
    }


    @PutMapping("/{id}")
    public ApiResponse<Receta> actualizar(
            @PathVariable Long id,
            @RequestBody RecetaDTO dto
    ) {
        return new ApiResponse<>(true, "Receta actualizada",
                recetaService.actualizar(id, dto));
    }
}