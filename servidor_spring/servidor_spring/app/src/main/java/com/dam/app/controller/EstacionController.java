package com.dam.app.controller;

import com.dam.app.model.Estacion;
import com.dam.app.model.Cargador;
import com.dam.app.repository.EstacionRepository;
import com.dam.app.repository.CargadorRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/estaciones")
public class EstacionController {

    private final EstacionRepository estacionRepository;
    private final CargadorRepository cargadorRepository;

    // Constructor para inyectar ambos repositorios
    public EstacionController(EstacionRepository estacionRepository, CargadorRepository cargadorRepository) {
        this.estacionRepository = estacionRepository;
        this.cargadorRepository = cargadorRepository;
    }

    @GetMapping
    public List<Estacion> obtenerTodas() {
        return estacionRepository.findAll();
    }

    @GetMapping("/{id}/cargadores")
    public List<Cargador> obtenerCargadores(@PathVariable Long id) {
        return cargadorRepository.findByEstacionId(id);
    }
}