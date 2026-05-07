package org.example.com.caredate.model.service;

import org.example.com.caredate.repository.*;
import org.example.com.caredate.model.entity.*;
import org.example.com.caredate.model.enums.*;
import org.example.com.caredate.model.dto.*;
import org.example.com.caredate.util.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepo;
    private final MedicoRepository medicoRepo;
    private final CodigoRepo codigoRepo;
    private final EmailService emailService;
    private final JwtUtil jwtUtil;
    private final PacienteRepository pacienteRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository usuarioRepo,
                       MedicoRepository medicoRepo,
                       CodigoRepo codigoRepo,
                       EmailService emailService,
                       JwtUtil jwtUtil,
                       PacienteRepository pacienteRepo,
                       BCryptPasswordEncoder passwordEncoder) {

        this.usuarioRepo = usuarioRepo;
        this.medicoRepo = medicoRepo;
        this.codigoRepo = codigoRepo;
        this.emailService = emailService;
        this.jwtUtil = jwtUtil;
        this.pacienteRepo = pacienteRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public void enviarCodigo(String correo) {
        String codigo = String.valueOf((int)(Math.random()*900000)+100000);

        CodigoVerificacion c = new CodigoVerificacion();
        c.setCorreo(correo);
        c.setCodigo(codigo);
        c.setExpiracion(System.currentTimeMillis()+300000);

        codigoRepo.save(c);
        emailService.enviarCodigo(correo, codigo);
    }

    public void verificarCodigo(String correo, String codigo) {

        CodigoVerificacion c = codigoRepo.findTopByCorreoOrderByExpiracionDesc(correo)
                .orElseThrow(() -> new RuntimeException("Código no encontrado"));

        if (System.currentTimeMillis() > c.getExpiracion()) {
            throw new RuntimeException("Código expirado");
        }

        if (!c.getCodigo().equals(codigo)) {
            throw new RuntimeException("Código incorrecto");
        }

        Usuario user = usuarioRepo.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no existe"));

        user.setVerificado(true);
        usuarioRepo.save(user);
    }


    public LoginResponseDTO login(String correo, String password, String codigo) {

        Usuario user = usuarioRepo.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no existe"));

        CodigoVerificacion c = codigoRepo
                .findTopByCorreoOrderByExpiracionDesc(correo)
                .orElseThrow(() -> new RuntimeException("Código no enviado"));

        if (c == null) throw new RuntimeException("Código no enviado");

        if (System.currentTimeMillis() > c.getExpiracion()) {
            throw new RuntimeException("Código expirado");
        }

        if (!c.getCodigo().equals(codigo)) {
            throw new RuntimeException("Código incorrecto");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Credenciales incorrectas");
        }

        user.setVerificado(true);
        usuarioRepo.save(user);

        String token = jwtUtil.generarToken(user.getCorreo(), user.getRol().name());

        return new LoginResponseDTO(token, user.getRol().name(), user.getId());
    }

    public void registrarPaciente(RegistroPacienteDTO dto) {

        Usuario user = new Usuario();
        user.setCorreo(dto.correo);
        user.setPassword(passwordEncoder.encode(dto.password));
        user.setRol(Rol.PACIENTE);
        user.setVerificado(false);

        usuarioRepo.save(user);

        Paciente p = new Paciente();
        p.setUsuario(user);
        p.setNombre(dto.nombre);
        p.setApellidoPaterno(dto.apellidoPaterno);
        p.setApellidoMaterno(dto.apellidoMaterno);
        p.setSexo(dto.sexo);
        p.setTelefono(dto.telefono);
        p.setFechaNacimiento(dto.fechaNacimiento);

        pacienteRepo.save(p);

        enviarCodigo(dto.correo);
    }

    public void registrarMedico(RegistroMedicoDTO dto) {

        Usuario user = new Usuario();
        user.setCorreo(dto.correo);
        user.setPassword(passwordEncoder.encode(dto.password));
        user.setVerificado(true);

        if(dto.tipo.equalsIgnoreCase("PSICOLOGO")){
            user.setRol(Rol.PSICOLOGO);
        } else if(dto.tipo.equalsIgnoreCase("PSIQUIATRA")){
            user.setRol(Rol.PSIQUIATRA);
        } else {
            throw new RuntimeException("Tipo de médico inválido");
        }

        usuarioRepo.save(user);

        Medico m = new Medico();
        m.setUsuario(user);
        m.setNombre(dto.nombre);
        m.setEspecialidad(dto.especialidad);
        m.setCedula(dto.cedula);
        m.setExperiencia(dto.experiencia);
        m.setPinAsistente("1234");

        medicoRepo.save(m);
    }

    public void registrarAsistente(String correoMedico, RegistroAsistenteDTO dto) {

        Medico medico = medicoRepo.findByUsuarioCorreo(correoMedico)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));

        if(!medico.getPinAsistente().equals(dto.pin)){
            throw new RuntimeException("PIN incorrecto");
        }

        Usuario user = new Usuario();
        user.setCorreo(dto.correo);
        user.setPassword(passwordEncoder.encode(dto.password));
        user.setRol(Rol.ASISTENTE);
        user.setVerificado(true);

        usuarioRepo.save(user);
    }
}