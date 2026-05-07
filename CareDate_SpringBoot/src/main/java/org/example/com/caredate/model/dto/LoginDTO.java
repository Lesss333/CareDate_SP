package org.example.com.caredate.model.dto;

public class LoginDTO {
    public String correo;
    public String password;
    public String codigo;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCorreo() { return correo; }
    public String getPassword() { return password; }

    public void setCorreo(String correo) { this.correo = correo; }
    public void setPassword(String password) { this.password = password; }
}

