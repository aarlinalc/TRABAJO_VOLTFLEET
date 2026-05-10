package com.dam.app.repository;

import com.dam.app.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    Usuario findByDniAndContrasena(String dni, String contrasena);
}