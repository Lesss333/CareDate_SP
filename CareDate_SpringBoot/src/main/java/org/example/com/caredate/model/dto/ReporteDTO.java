package org.example.com.caredate.model.dto;

public class ReporteDTO {

    private String mes;
    private long total;

    public ReporteDTO(String mes, long total) {
        this.mes = mes;
        this.total = total;
    }

    public String getMes() {
        return mes;
    }

    public long getTotal() {
        return total;
    }
}