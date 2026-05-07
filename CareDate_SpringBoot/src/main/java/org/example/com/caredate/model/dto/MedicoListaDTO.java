package org.example.com.caredate.model.dto;

public class MedicoListaDTO {

    private Long id;
    private String nombre;
    private String especialidad;
    private String cedula;
    private int experiencia;

    public MedicoListaDTO() {
    }

    public MedicoListaDTO(Long id, String nombre, String especialidad, String cedula, int experiencia) {
        this.id = id;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.cedula = cedula;
        this.experiencia = experiencia;
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