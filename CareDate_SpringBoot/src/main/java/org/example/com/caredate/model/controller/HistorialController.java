package org.example.com.caredate.model.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.com.caredate.model.dto.HistorialClinicoDTO;
import org.example.com.caredate.model.service.HistorialClinicoService;
import org.example.com.caredate.util.ApiResponse;
import org.example.com.caredate.util.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historial")
public class HistorialController {

    private final HistorialClinicoService service;
    private final JwtUtil jwtUtil;

    public HistorialController(HistorialClinicoService service, JwtUtil jwtUtil) {
        this.service = service;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/me")
    public ApiResponse<List<HistorialClinicoDTO>> miHistorial(HttpServletRequest request) {

        String correo = (String) request.getAttribute("correo");

        return new ApiResponse<>(
                true,
                "Historial clínico completo",
                service.obtenerHistorialCompleto(correo)
        );
    }
}