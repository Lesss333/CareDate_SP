package org.example.com.caredate.model.controller;

import org.example.com.caredate.model.dto.*;
import org.example.com.caredate.model.service.AuthService;
import org.example.com.caredate.util.ApiResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/verificar-codigo")
    public ApiResponse<?> verificar(@RequestBody LoginDTO request) {
        service.verificarCodigo(request.getCorreo(), request.getCodigo());
        return new ApiResponse<>(true, "Cuenta verificada", null);
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponseDTO> login(@RequestBody LoginDTO request) {

        LoginResponseDTO response = service.login(
                request.getCorreo(),
                request.getPassword(),
                request.getCodigo()
        );

        return new ApiResponse<>(true, "Login exitoso", response);
    }

    @PostMapping("/enviar-codigo")
    public ApiResponse<?> enviarCodigo(@RequestBody LoginDTO request) {

        System.out.println("ENVIANDO CODIGO A: " + request.getCorreo());

        service.enviarCodigo(request.getCorreo());

        return new ApiResponse<>(true, "Código enviado", null);
    }

    @PostMapping("/registro/paciente")
    public ApiResponse<?> registrarPaciente(@RequestBody RegistroPacienteDTO dto) {
        service.registrarPaciente(dto);
        return new ApiResponse<>(true, "Paciente registrado", null);
    }

    @PostMapping("/registro/medico")
    public ApiResponse<?> registrarMedico(@RequestBody RegistroMedicoDTO dto) {
        service.registrarMedico(dto);
        return new ApiResponse<>(true, "Médico registrado", null);
    }

    @PostMapping("/registro/asistente")
    public ApiResponse<?> registrarAsistente(
            @RequestParam String correoMedico,
            @RequestBody RegistroAsistenteDTO dto) {

        service.registrarAsistente(correoMedico, dto);
        return new ApiResponse<>(true, "Asistente registrado", null);
    }
}
