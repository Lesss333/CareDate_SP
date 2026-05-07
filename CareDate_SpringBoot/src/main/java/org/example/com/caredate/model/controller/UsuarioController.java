package org.example.com.caredate.model.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.com.caredate.model.entity.Usuario;
import org.example.com.caredate.repository.UsuarioRepository;
import org.example.com.caredate.util.ApiResponse;
import org.example.com.caredate.util.JwtUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioRepository usuarioRepo;
    private final JwtUtil jwtUtil;

    public UsuarioController(UsuarioRepository usuarioRepo, JwtUtil jwtUtil) {
        this.usuarioRepo = usuarioRepo;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/perfil")
    public ApiResponse<Usuario> perfil(HttpServletRequest request){

        String correo = (String) request.getAttribute("correo");

        Usuario user = usuarioRepo.findByCorreo(correo).orElseThrow();

        return new ApiResponse<>(true, "Perfil obtenido", user);
    }
}