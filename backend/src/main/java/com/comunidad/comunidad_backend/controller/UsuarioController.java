package com.comunidad.comunidad_backend.controller;

import com.comunidad.comunidad_backend.dto.CambioPass;


import java.util.List;
import java.util.NoSuchElementException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.comunidad.comunidad_backend.entity.Comunidad;
import com.comunidad.comunidad_backend.entity.Usuario;
import com.comunidad.comunidad_backend.security.JwtService;
import com.comunidad.comunidad_backend.service.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtService jwtService;

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public List<Usuario> getAllUsuarios(){
        return usuarioService.findAll();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    ResponseEntity <?> createUsuario(@RequestBody Usuario usuario, HttpServletRequest request){
        String token = request.getHeader("Authorization").substring(7);
        Comunidad idComunidad = new Comunidad();
        idComunidad.setId(jwtService.extraerIdComunidad(token));
        usuario.setComunidad(idComunidad);
        try{
            Usuario nuevoUsuario = usuarioService.crearUsuario(usuario);
            return ResponseEntity.ok(nuevoUsuario);
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @GetMapping("/comunidad")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public List<Usuario> getUsuarioPorComunidad(HttpServletRequest request){
        String token = request.getHeader("Authorization").substring(7);
        Long idComunidad = jwtService.extraerIdComunidad(token);
        return usuarioService.findByComunidadId(idComunidad);
    }

    @GetMapping("/{idUsuario}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<Usuario> getUsuarioPorId(@PathVariable Long idUsuario){
        Usuario usuario = usuarioService.findById(idUsuario);
        if(usuario != null){
            return ResponseEntity.ok(usuario);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/me")
    public ResponseEntity<Usuario> obtenerPerfil(Principal princpal){
        String email = princpal.getName();

        Usuario usuario = usuarioService.findByEmail(email);

        return ResponseEntity.ok(usuario);
    }
    
    

    @DeleteMapping("/{idUsuario}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<String> deleteUsuario(@PathVariable Long idUsuario){
        boolean borrado = usuarioService.deleteUsuario(idUsuario);

        if(borrado){
            return ResponseEntity.ok("Usuario eliminado correctamente.");
        } else {
            return ResponseEntity.status(404).body("Usuario no encontrado.");
        }
    }

    @PutMapping("/modificar")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> modificarUsuario(@RequestBody Usuario usuarioNuevo, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        Long idUsuario = jwtService.extraerIdUsuario(token);
        Usuario modificado = usuarioService.modificarUsuario(idUsuario, usuarioNuevo);
        if(modificado != null){
            return ResponseEntity.ok(modificado);
        }else{
            return ResponseEntity.status(404).body("Usuario no encontrado.");
        }
        
    }

    @PutMapping("/admin/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> modificarUsuarioAdmin(@PathVariable Long id, @RequestBody Usuario usuarioNuevo) {
        Usuario modificado = usuarioService.modificarUsuarioAdmin(id, usuarioNuevo);
        if(modificado != null){
            return ResponseEntity.ok(modificado);
        }else{
            return ResponseEntity.status(404).body("Usuario no encontrado.");
        }
        
    }
    
    @PatchMapping("/pass")
    public ResponseEntity<?> cambioPasswordUser(@RequestBody CambioPass cambioPass, HttpServletRequest request){
        String token = request.getHeader("Authorization").substring(7);
        Long idUsuario = jwtService.extraerIdUsuario(token);
        if(cambioPass.getOldPassword() == null || cambioPass.getOldPassword().isBlank() ||
            cambioPass.getNewPassword() == null || cambioPass.getNewPassword().isBlank()){
            return ResponseEntity.status(400).body("Los datos de cambio de contraseña son obligatorios");
        }
        try{
            usuarioService.cambioPassword(idUsuario, cambioPass);
            return ResponseEntity.ok("Contraseña modificada");
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(401).body(e.getMessage());
        }catch (NoSuchElementException e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
       
    }

    @PatchMapping("/admin/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> cambioPassAdmin(@PathVariable Long id){
        try{
            usuarioService.cambioPassAdmin(id);
            return ResponseEntity.status(200).build();
        }catch (NoSuchElementException e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
    
  
    
        
}

