package org.example.com.caredate.model.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.com.caredate.model.dto.ReporteDTO;
import org.example.com.caredate.model.service.ReporteService;
import org.example.com.caredate.util.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

    private final ReporteService service;

    public ReporteController(ReporteService service) {
        this.service = service;
    }

    @GetMapping("/citas-por-mes")
    public ApiResponse<List<ReporteDTO>> citasPorMes(HttpServletRequest request) {
        return new ApiResponse<>(true, "Citas por mes", service.citasPorMes());
    }

    @GetMapping("/mes-mas-demandado")
    public ApiResponse<ReporteDTO> mesMasDemandado(HttpServletRequest request) {
        return new ApiResponse<>(true, "Mes con más citas", service.mesMasDemandado());
    }
}