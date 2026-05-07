package org.example.com.caredate.repository;

import org.example.com.caredate.model.entity.Bienestar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BienestarRepository extends JpaRepository<Bienestar, Long> {
}