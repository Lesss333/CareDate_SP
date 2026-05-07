package org.example.com.caredate.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class RegistroPacienteDTO {

    @JsonProperty("nombre")
    public String nombre;

    @JsonProperty("apellido")
    public String apellidoPaterno;

    @JsonProperty("apellidoMaterno")
    public String apellidoMaterno;

    @JsonProperty("correo")
    public String correo;

    @JsonProperty("telefono")
    public String telefono;

    @JsonProperty("password")
    public String password;

    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate fechaNacimiento;

    @JsonProperty("sexo")
    public String sexo;
}