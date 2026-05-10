package com.dam.app.repository;

import com.dam.app.model.Cargador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CargadorRepository extends JpaRepository<Cargador, Long> {

    /**
     * Busca todos los cargadores que pertenecen a una estación específica.
     * Spring genera automáticamente: SELECT * FROM cargador WHERE estacion_id = ?
     */
    List<Cargador> findByEstacionId(Long estacionId);
}