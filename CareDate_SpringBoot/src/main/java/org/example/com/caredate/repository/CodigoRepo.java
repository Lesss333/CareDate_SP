package org.example.com.caredate.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.example.com.caredate.model.entity.CodigoVerificacion;
import java.util.Optional;
public interface CodigoRepo extends JpaRepository<CodigoVerificacion, Long> {
    Optional<CodigoVerificacion> findTopByCorreoOrderByExpiracionDesc(String correo);
}