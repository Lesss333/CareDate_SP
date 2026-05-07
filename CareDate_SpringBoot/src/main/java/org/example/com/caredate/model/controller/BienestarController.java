package org.example.com.caredate.model.controller;

import org.example.com.caredate.model.entity.Bienestar;
import org.example.com.caredate.model.service.BienestarService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bienestar")
public class BienestarController {

    private final BienestarService service;

    public BienestarController(BienestarService service) {
        this.service = service;
    }

    @PostMapping
    public Bienestar guardar(@RequestBody Bienestar b) {
        return service.guardar(b);
    }
}