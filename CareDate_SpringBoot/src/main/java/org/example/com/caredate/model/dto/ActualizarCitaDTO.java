package org.example.com.caredate.model.dto;

import java.time.LocalDateTime;

public class ActualizarCitaDTO {

    private LocalDateTime fecha;
    private Long medicoId;

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Long getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(Long medicoId) {
        this.medicoId = medicoId;
    }
}