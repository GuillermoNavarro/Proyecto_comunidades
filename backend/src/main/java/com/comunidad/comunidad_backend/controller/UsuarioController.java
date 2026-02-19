package com.comunidad.comunidad_backend.controller;

import com.comunidad.comunidad_backend.dto.CambioPass;
import com.comunidad.comunidad_backend.dto.LoginRequest;

import java.util.List;
import java.util.NoSuchElementException;

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
    ResponseEntity <?> createUsuario(@RequestBody Usuario usuario){
        try{
            Usuario nuevoUsuario = usuarioService.crearUsuario(usuario);
            return ResponseEntity.ok(nuevoUsuario);
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @GetMapping("/comunidad/{idComunidad}")
    public List<Usuario> getUsuarioPorComunidad(@PathVariable Long idComunidad){
        return usuarioService.findByComunidadId(idComunidad);
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<Usuario> getUsuarioPorId(@PathVariable Long idUsuario){
        Usuario usuario = usuarioService.findById(idUsuario);
        if(usuario != null){
            return ResponseEntity.ok(usuario);
        }else{
            return ResponseEntity.notFound().build();
        }
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

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest){
        boolean loginExitoso = usuarioService.login(loginRequest.getEmail(), loginRequest.getPassword());

        if(loginExitoso){
            return ResponseEntity.ok("Login exitoso.");
        } else {
            return ResponseEntity.status(401).body("Credenciales incorrectas o usuario inactivo.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modificarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioNuevo) {
        Usuario modificado = usuarioService.modificarUsuario(id, usuarioNuevo);
        if(modificado != null){
            return ResponseEntity.ok(modificado);
        }else{
            return ResponseEntity.status(404).body("Usuario no encontrado.");
        }
        
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<?> modificarUsuarioAdmin(@PathVariable Long id, @RequestBody Usuario usuarioNuevo) {
        Usuario modificado = usuarioService.modificarUsuarioAdmin(id, usuarioNuevo);
        if(modificado != null){
            return ResponseEntity.ok(modificado);
        }else{
            return ResponseEntity.status(404).body("Usuario no encontrado.");
        }
        
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<?> cambioPasswordUser(@PathVariable Long id, @RequestBody CambioPass cambioPass){
        if(cambioPass.getOldPassword() == null || cambioPass.getOldPassword().isBlank() ||
            cambioPass.getNewPassword() == null || cambioPass.getNewPassword().isBlank()){
            return ResponseEntity.status(400).body("Los datos de cambio de contraseña son obligatorios");
        }
        try{
            usuarioService.cambioPassword(id, cambioPass);
            return ResponseEntity.ok("Contraseña modificada");
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(401).body(e.getMessage());
        }catch (NoSuchElementException e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
       
    }

    @PatchMapping("/admin/{id}")
    public ResponseEntity<?> cambioPassAdmin(@PathVariable Long id){
        try{
            usuarioService.cambioPassAdmin(id);
            return ResponseEntity.status(200).build();
        }catch (NoSuchElementException e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
    
  
    
        
}

