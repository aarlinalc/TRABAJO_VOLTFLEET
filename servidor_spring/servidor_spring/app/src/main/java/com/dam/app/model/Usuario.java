package com.dam.app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    private String dni;
    private String contrasena;
    private String nombre;
    private Integer rol;

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Integer getRol() { return rol; }
    public void setRol(Integer rol) { this.rol = rol; }
}