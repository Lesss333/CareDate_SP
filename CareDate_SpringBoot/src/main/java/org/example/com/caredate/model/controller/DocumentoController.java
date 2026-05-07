package org.example.com.caredate.model.controller;

import org.example.com.caredate.model.entity.DocumentoMedico;
import org.example.com.caredate.model.service.DocumentoMedicoService;
import org.example.com.caredate.util.ApiResponse;
import org.example.com.caredate.util.JwtUtil;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletRequest;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/documentos")
public class DocumentoController {

    private final DocumentoMedicoService documentoService;
    private final JwtUtil jwtUtil;

    public DocumentoController(DocumentoMedicoService documentoService, JwtUtil jwtUtil) {
        this.documentoService = documentoService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/paciente/{pacienteId}")
    public ApiResponse<DocumentoMedico> subir(@PathVariable Long pacienteId,
                                              @RequestParam("file") MultipartFile file) throws IOException {
        return new ApiResponse<>(true, "Documento subido", documentoService.subir(pacienteId, file));
    }

    @GetMapping("/paciente/{pacienteId}")
    public ApiResponse<List<DocumentoMedico>> listarPorPaciente(@PathVariable Long pacienteId) {
        return new ApiResponse<>(true, "Documentos del paciente", documentoService.listarPorPaciente(pacienteId));
    }

    @GetMapping("/mis-documentos")
    public ApiResponse<List<DocumentoMedico>> misDocumentos(HttpServletRequest request) {

        String correo = (String) request.getAttribute("correo");

        return new ApiResponse<>(true, "Mis documentos", documentoService.listarMios(correo));
    }

    @GetMapping("/{id}")
    public ApiResponse<DocumentoMedico> obtener(@PathVariable Long id) {
        return new ApiResponse<>(true, "Documento encontrado", documentoService.obtener(id));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> eliminar(@PathVariable Long id) throws IOException {
        documentoService.eliminar(id);
        return new ApiResponse<>(true, "Documento eliminado", null);
    }

    @GetMapping("/{id}/descargar")
    public ResponseEntity<InputStreamResource> descargar(@PathVariable Long id) throws IOException {
        DocumentoMedico doc = documentoService.obtener(id);

        FileInputStream inputStream = new FileInputStream(Paths.get(doc.getRutaArchivo()).toFile());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + doc.getNombreArchivo() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(inputStream));
    }
}