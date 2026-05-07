package org.example.com.caredate.model.entity;

import jakarta.persistence.*;
import org.example.com.caredate.model.enums.EstadoCita;

import java.time.LocalDateTime;

@Entity
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;

    @Enumerated(EnumType.STRING)
    private EstadoCita estado;

    @ManyToOne
    private Usuario paciente;

    @ManyToOne
    private Usuario medico;

    @Column(length = 1000)
    private String motivo;

    private boolean recordatorio24hEnviado = false;
    private boolean recordatorio2hEnviado = false;

    public boolean isRecordatorio24hEnviado() {
        return recordatorio24hEnviado;
    }

    public void setRecordatorio24hEnviado(boolean recordatorio24hEnviado) {
        this.recordatorio24hEnviado = recordatorio24hEnviado;
    }

    public boolean isRecordatorio2hEnviado() {
        return recordatorio2hEnviado;
    }

    public void setRecordatorio2hEnviado(boolean recordatorio2hEnviado) {
        this.recordatorio2hEnviado = recordatorio2hEnviado;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
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

    public EstadoCita getEstado() {
        return estado;
    }

    public void setEstado(EstadoCita estado) {
        this.estado = estado;
    }

    public Usuario getPaciente() {
        return paciente;
    }

    public void setPaciente(Usuario paciente) {
        this.paciente = paciente;
    }

    public Usuario getMedico() {
        return medico;
    }

    public void setMedico(Usuario medico) {
        this.medico = medico;
    }
}