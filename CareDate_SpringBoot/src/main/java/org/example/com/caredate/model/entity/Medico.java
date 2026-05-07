package org.example.com.caredate.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;

@Entity
public class Medico {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    private Usuario usuario;

    private String nombre;
    private String especialidad; // Psicologo / Psiquiatra
    private String cedula;
    private int experiencia;

    private String pinAsistente;

    public String getPinAsistente() {
        return pinAsistente;
    }

    public void setPinAsistente(String pinAsistente) {
        this.pinAsistente = pinAsistente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }
}