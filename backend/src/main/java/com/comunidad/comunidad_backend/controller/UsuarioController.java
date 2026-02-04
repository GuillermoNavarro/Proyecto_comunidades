package com.comunidad.comunidad_backend.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.comunidad.comunidad_backend.entity.Usuario;
import com.comunidad.comunidad_backend.service.UsuarioService;






@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> getAllUsuarios(){
        return usuarioService.findAll();
    }

    @PostMapping
    public Usuario createUsuario(@RequestBody Usuario usuario){
        return usuarioService.save(usuario);
    }

    @GetMapping("/comunidad/{idComunidad}")
    public List<Usuario> getUsuarioPorComunidad(@PathVariable Long idComunidad){
        return usuarioService.findByComunidadId(idComunidad);
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<String> deleteUsuario(@PathVariable Long idUsuario){
        boolean borrado = usuarioService.deleteUsuario(idUsuario);

        if(borrado){
            return ResponseEntity.ok("Usuario eliminado correctamente.");
        } else {
            return ResponseEntity.status(404).body("Usuario no encontrado.");
        }
    }
  
    
        
}
