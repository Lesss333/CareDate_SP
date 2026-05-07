package org.example.com.caredate.model.service;

import org.example.com.caredate.model.entity.DocumentoMedico;
import org.example.com.caredate.model.entity.Usuario;
import org.example.com.caredate.repository.DocumentoMedicoRepository;
import org.example.com.caredate.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DocumentoMedicoService {

    private final DocumentoMedicoRepository documentoRepo;
    private final UsuarioRepository usuarioRepo;

    public DocumentoMedicoService(DocumentoMedicoRepository documentoRepo, UsuarioRepository usuarioRepo) {
        this.documentoRepo = documentoRepo;
        this.usuarioRepo = usuarioRepo;
    }

    public DocumentoMedico subir(Long pacienteId, MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Debes enviar un archivo");
        }

        Usuario paciente = usuarioRepo.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        String nombreOriginal = file.getOriginalFilename();
        String extension = obtenerExtension(nombreOriginal).toLowerCase();

        if (!extension.equals("pdf") && !extension.equals("png") && !extension.equals("jpg") && !extension.equals("jpeg")) {
            throw new RuntimeException("Solo se permiten archivos PDF, PNG, JPG y JPEG");
        }

        Path carpeta = Paths.get("uploads/documentos");
        Files.createDirectories(carpeta);

        String nombreGuardado = UUID.randomUUID() + "_" + nombreOriginal;
        Path ruta = carpeta.resolve(nombreGuardado);

        Files.copy(file.getInputStream(), ruta, StandardCopyOption.REPLACE_EXISTING);

        DocumentoMedico doc = new DocumentoMedico();
        doc.setPaciente(paciente);
        doc.setNombreArchivo(nombreOriginal);
        doc.setTipoArchivo(extension);
        doc.setRutaArchivo(ruta.toString());
        doc.setFechaSubida(LocalDateTime.now());

        return documentoRepo.save(doc);
    }

    public List<DocumentoMedico> listarPorPaciente(Long pacienteId) {
        return documentoRepo.findByPacienteIdOrderByFechaSubidaDesc(pacienteId);
    }

    public List<DocumentoMedico> listarMios(String correo) {
        return documentoRepo.findByPacienteCorreoOrderByFechaSubidaDesc(correo);
    }

    public DocumentoMedico obtener(Long id) {
        return documentoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento no encontrado"));
    }

    public void eliminar(Long id) throws IOException {
        DocumentoMedico doc = obtener(id);

        Path ruta = Paths.get(doc.getRutaArchivo());
        Files.deleteIfExists(ruta);

        documentoRepo.delete(doc);
    }

    private String obtenerExtension(String nombreArchivo) {
        if (nombreArchivo == null || !nombreArchivo.contains(".")) {
            return "";
        }
        return nombreArchivo.substring(nombreArchivo.lastIndexOf(".") + 1);
    }
}