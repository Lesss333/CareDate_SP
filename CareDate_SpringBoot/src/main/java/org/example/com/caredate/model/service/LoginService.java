package org.example.com.caredate.model.service;

import org.example.com.caredate.model.entity.IntentoLogin;
import org.example.com.caredate.repository.IntentoLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LoginService {

    @Autowired
    private IntentoLoginRepository repo;

    public void registrarFallo(String email) {
        IntentoLogin intento = repo.findByEmail(email).orElse(new IntentoLogin());
        intento.setEmail(email);
        intento.setIntentos(intento.getIntentos() + 1);

        if (intento.getIntentos() >= 3) {
            intento.setBloqueadoHasta(LocalDateTime.now().plusMinutes(10));
            intento.setIntentos(0);
        }
        repo.save(intento);
    }

    public boolean estaBloqueado(String email) {
        return repo.findByEmail(email)
                .map(i -> i.getBloqueadoHasta() != null && i.getBloqueadoHasta().isAfter(LocalDateTime.now()))
                .orElse(false);
    }
}
