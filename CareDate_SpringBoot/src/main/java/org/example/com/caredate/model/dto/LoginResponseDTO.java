package org.example.com.caredate.model.dto;

public class LoginResponseDTO {

    private String token;
    private String tipo;
    private Long id;

    public LoginResponseDTO(String token, String tipo, Long id) {
        this.token = token;
        this.tipo = tipo;
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public String getTipo() {
        return tipo;
    }

    public Long getId() {
        return id;
    }
}