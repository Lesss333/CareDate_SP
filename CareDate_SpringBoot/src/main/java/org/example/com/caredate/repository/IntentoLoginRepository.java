package org.example.com.caredate.repository;

import org.example.com.caredate.model.entity.IntentoLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IntentoLoginRepository extends JpaRepository<IntentoLogin, Long> {
    Optional<IntentoLogin> findByEmail(String email);
}
