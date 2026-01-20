package com.comunidad.comunidad_backend.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.comunidad.comunidad_backend.entity.Usuario;
import com.comunidad.comunidad_backend.repository.UsuarioRepository;






@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public List<Usuario> getAllUsuarios(){
        return usuarioRepository.findAll();
    }

    @PostMapping
    public Usuario createUsuario(@RequestBody Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    @GetMapping("/comunidad/{idComunidad}")
    public List<Usuario> getUsuarioPorComunidad(@PathVariable Long idComunidad){
        return usuarioRepository.findByIdComunidad(idComunidad);
    }

  
    
        
}
