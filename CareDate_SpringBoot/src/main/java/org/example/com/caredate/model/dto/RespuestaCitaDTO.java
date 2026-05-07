package org.example.com.caredate.model.dto;

import java.time.LocalDateTime;

public class RespuestaCitaDTO {

    private Long id;
    private LocalDateTime fecha;
    private String estado;

    // 🔥 NUEVO
    private String motivo;

    private Long pacienteId;
    private String pacienteNombre;
    private String pacienteCorreo;

    private Long medicoId;
    private String medicoNombre;
    private String medicoCorreo;
    private String medicoEspecialidad;

    public RespuestaCitaDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    // 🔥 GET/SET MOTIVO
    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public String getPacienteNombre() {
        return pacienteNombre;
    }

    public void setPacienteNombre(String pacienteNombre) {
        this.pacienteNombre = pacienteNombre;
    }

    public String getPacienteCorreo() {
        return pacienteCorreo;
    }

    public void setPacienteCorreo(String pacienteCorreo) {
        this.pacienteCorreo = pacienteCorreo;
    }

    public Long getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(Long medicoId) {
        this.medicoId = medicoId;
    }

    public String getMedicoNombre() {
        return medicoNombre;
    }

    public void setMedicoNombre(String medicoNombre) {
        this.medicoNombre = medicoNombre;
    }

    public String getMedicoCorreo() {
        return medicoCorreo;
    }

    public void setMedicoCorreo(String medicoCorreo) {
        this.medicoCorreo = medicoCorreo;
    }

    public String getMedicoEspecialidad() {
        return medicoEspecialidad;
    }

    public void setMedicoEspecialidad(String medicoEspecialidad) {
        this.medicoEspecialidad = medicoEspecialidad;
    }
}