package org.example.com.caredate.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Bienestar {
    @Id
    @GeneratedValue
    private Long id;

    private String estadoEmocional;

    @ManyToOne
    private Usuario usuario;
}


