package com.dam.app.controller;

import com.dam.app.model.Usuario;
import com.dam.app.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UsuarioRepository repository;

    @GetMapping("/auth")
    public Usuario login(@RequestParam String dni, @RequestParam String contrasena) {
        return repository.findByDniAndContrasena(dni, contrasena);
    }
}