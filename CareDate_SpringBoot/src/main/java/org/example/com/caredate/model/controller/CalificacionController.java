package org.example.com.caredate.model.controller;

import org.example.com.caredate.model.entity.Calificacion;
import org.example.com.caredate.repository.CalificacionRepository;
import org.example.com.caredate.util.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calificaciones")
public class CalificacionController {

    private final CalificacionRepository repo;

    public CalificacionController(CalificacionRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public ApiResponse<?> crear(@RequestBody Calificacion c) {
        repo.save(c);
        return new ApiResponse<>(true, "Calificación guardada", null);
    }
}