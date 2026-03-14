package com.comunidad.comunidad_backend.controller;

import com.comunidad.comunidad_backend.dto.LoginRequest;
import com.comunidad.comunidad_backend.entity.Usuario;
import com.comunidad.comunidad_backend.service.UsuarioService;

import com.comunidad.comunidad_backend.security.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
       Usuario usuario = usuarioService.login(loginRequest.getEmail(), loginRequest.getPassword());

        if(usuario != null){
            return ResponseEntity.ok(jwtService.generarToken(usuario));
        } else {
            return ResponseEntity.status(401).body("Credenciales incorrectas o usuario inactivo.");
        }
    }
    
    
}
