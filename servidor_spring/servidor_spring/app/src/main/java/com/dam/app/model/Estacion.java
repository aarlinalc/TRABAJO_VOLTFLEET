package com.dam.app.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "estacion") // Este nombre debe ser igual al de tu tabla en pgAdmin
public class Estacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    // Usamos BigDecimal porque en SQL pusiste NUMERIC(10,7)
    private BigDecimal latitud;
    private BigDecimal longitud;

    @Column(name = "estado_operativo")
    private Integer estadoOperativo;

    @Column(name = "demanda_zona_kwh")
    private BigDecimal demandaZonaKwh;

    // --- CONSTRUCTORES ---
    public Estacion() {}

    // --- GETTERS Y SETTERS (Imprescindibles para que Spring funcione) ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public BigDecimal getLatitud() { return latitud; }
    public void setLatitud(BigDecimal latitud) { this.latitud = latitud; }

    public BigDecimal getLongitud() { return longitud; }
    public void setLongitud(BigDecimal longitud) { this.longitud = longitud; }

    public Integer getEstadoOperativo() { return estadoOperativo; }
    public void setEstadoOperativo(Integer estadoOperativo) { this.estadoOperativo = estadoOperativo; }

    public BigDecimal getDemandaZonaKwh() { return demandaZonaKwh; }
    public void setDemandaZonaKwh(BigDecimal demandaZonaKwh) { this.demandaZonaKwh = demandaZonaKwh; }
}