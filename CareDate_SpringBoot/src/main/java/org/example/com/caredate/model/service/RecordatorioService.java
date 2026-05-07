package org.example.com.caredate.model.service;

import org.example.com.caredate.model.entity.Cita;
import org.example.com.caredate.model.enums.EstadoCita;
import org.example.com.caredate.repository.CitaRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecordatorioService {

    private final CitaRepository citaRepo;
    private final EmailService emailService;

    public RecordatorioService(CitaRepository citaRepo, EmailService emailService) {
        this.citaRepo = citaRepo;
        this.emailService = emailService;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void enviarRecordatorios() {

        LocalDateTime ahora = LocalDateTime.now();
        List<Cita> citas = citaRepo.findAll();

        for (Cita c : citas) {

            if (c.getEstado() != EstadoCita.PENDIENTE &&
                    c.getEstado() != EstadoCita.CONFIRMADA) {
                continue;
            }

            long horas = Duration.between(ahora, c.getFecha()).toHours();

            if (horas <= 24 && horas > 23 && !c.isRecordatorio24hEnviado()) {
                emailService.enviarCorreo(
                        c.getPaciente().getCorreo(),
                        "Tienes una cita en 24 horas"
                );
                c.setRecordatorio24hEnviado(true);
                citaRepo.save(c);
            }

            if (horas <= 2 && horas > 1 && !c.isRecordatorio2hEnviado()) {
                emailService.enviarCorreo(
                        c.getPaciente().getCorreo(),
                        "Tienes una cita en 2 horas"
                );
                c.setRecordatorio2hEnviado(true);
                citaRepo.save(c);
            }
        }
    }
}