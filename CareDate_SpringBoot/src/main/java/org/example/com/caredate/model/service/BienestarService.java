package org.example.com.caredate.model.service;

import org.example.com.caredate.model.entity.Bienestar;
import org.example.com.caredate.repository.BienestarRepository;
import org.springframework.stereotype.Service;

@Service
public class BienestarService {

    private final BienestarRepository repo;

    public BienestarService(BienestarRepository repo) {
        this.repo = repo;
    }

    public Bienestar guardar(Bienestar b){
        return repo.save(b);
    }
}