package com.dam.app.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cargador")
public class Cargador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con la tabla estación
    @Column(name = "estacion_id")
    private Long estacionId;

    private String tipo; // Ej: 'CCS2', 'Type 2'

    @Column(name = "potencia_kw")
    private Integer potenciaKw;

    @Column(name = "temp_inversor")
    private BigDecimal tempInversor;

    @Column(name = "carga_actual")
    private BigDecimal cargaActual;

    // Constructores, Getters y Setters
    public Cargador() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getEstacionId() { return estacionId; }
    public void setEstacionId(Long estacionId) { this.estacionId = estacionId; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public Integer getPotenciaKw() { return potenciaKw; }
    public void setPotenciaKw(Integer potenciaKw) { this.potenciaKw = potenciaKw; }
}