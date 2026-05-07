package org.example.com.caredate.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCodigo(String correo, String codigo) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(correo);
        mensaje.setSubject("Código de verificación CareDate");
        mensaje.setText("Tu código es: " + codigo);

        mailSender.send(mensaje);
    }

    public void enviarCorreo(String correo, String fecha) {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(correo);
        mail.setSubject("Recordatorio de cita");

        mail.setText(
                "Tienes una cita programada.\n\n" +
                        "Fecha: " + fecha + "\n\n" +
                        "No olvides asistir."
        );

        mailSender.send(mail);
    }

}
