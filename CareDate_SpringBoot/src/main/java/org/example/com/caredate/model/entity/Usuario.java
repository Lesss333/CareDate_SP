package org.example.com.caredate.model.entity;

import jakarta.persistence.*;
import org.example.com.caredate.model.enums.Rol;

@Entity
@Table(name = "usuarios")
public class Usuario {


    @Column(nullable = false)
    private boolean verificado = false;

    public Boolean getVerificado() {
        return verificado;
    }

    public void setVerificado(Boolean verificado) {
        this.verificado = verificado;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String correo;

    private String password;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    private int intentosFallidos;
    private long bloqueoHasta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public int getIntentosFallidos() {
        return intentosFallidos;
    }

    public void setIntentosFallidos(int intentosFallidos) {
        this.intentosFallidos = intentosFallidos;
    }

    public long getBloqueoHasta() {
        return bloqueoHasta;
    }

    public void setBloqueoHasta(long bloqueoHasta) {
        this.bloqueoHasta = bloqueoHasta;
    }
}
