package com.dam.app.repository;

import com.dam.app.model.Estacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstacionRepository extends JpaRepository<Estacion, Long> {
    // Aquí no hace falta escribir nada, JpaRepository ya trae los métodos para buscar
}