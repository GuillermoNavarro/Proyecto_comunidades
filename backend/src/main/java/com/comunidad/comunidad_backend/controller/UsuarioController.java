package com.comunidad.comunidad_backend.controller;

import com.comunidad.comunidad_backend.dto.CambioPass;


import java.util.List;
import java.util.NoSuchElementException;
import java.security.Principal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.comunidad.comunidad_backend.entity.Comunidad;
import com.comunidad.comunidad_backend.entity.Usuario;
import com.comunidad.comunidad_backend.security.JwtService;
import com.comunidad.comunidad_backend.service.UsuarioService;


@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final JwtService jwtService;

    public UsuarioController(
        UsuarioService usuarioService, 
        JwtService jwtService
    ){
        this.usuarioService = usuarioService;
        this.jwtService = jwtService;
    }

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public List<Usuario> getAllUsuarios(){
        return usuarioService.findAll();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    ResponseEntity <?> createUsuario(@RequestBody Usuario usuario, @AuthenticationPrincipal Usuario usuarioLogado){
        Comunidad idComunidad = new Comunidad();
        idComunidad.setId(usuarioLogado.getComunidad().getId());
        usuario.setComunidad(idComunidad);
        try{
            Usuario nuevoUsuario = usuarioService.crearUsuario(usuario);
            return ResponseEntity.status(201).body(nuevoUsuario);
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @GetMapping("/comunidad")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public List<Usuario> getUsuarioPorComunidad(@AuthenticationPrincipal Usuario usuarioLogado){
        return usuarioService.findByComunidadId(usuarioLogado.getComunidad().getId());
    }

    @GetMapping("/{idUsuario}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<Usuario> getUsuarioPorId(@PathVariable Long idUsuario, @AuthenticationPrincipal Usuario usuarioLogado){
        Usuario usuario = usuarioService.findById(idUsuario, usuarioLogado);
        if(usuario != null){
            return ResponseEntity.ok(usuario);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/me")
    public ResponseEntity<Usuario> obtenerPerfil(Principal principal){
        String email = principal.getName();

        Usuario usuario = usuarioService.findByEmail(email);

        return ResponseEntity.ok(usuario);
    }
   /*@GetMapping("/me")
    public ResponseEntity<Usuario> obtenerPerfil(@AuthenticationPrincipal Usuario usuario){
        
        return ResponseEntity.ok(usuario);
    }*/
    
    

    @DeleteMapping("/{idUsuario}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<String> deleteUsuario(@PathVariable Long idUsuario, @AuthenticationPrincipal Usuario usuarioLogado){
        boolean borrado = usuarioService.deleteUsuario(idUsuario, usuarioLogado);

        if(borrado){
            return ResponseEntity.ok("Usuario eliminado correctamente.");
        } else {
            return ResponseEntity.status(404).body("Usuario no encontrado.");
        }
    }

    
    @PutMapping("/modificar")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> modificarUsuario(@RequestBody Usuario usuarioNuevo, @AuthenticationPrincipal Usuario usuarioLogado) {
        Long idUsuario = usuarioLogado.getId();
        try{
            Usuario modificado = usuarioService.modificarUsuario(idUsuario, usuarioNuevo);
            return ResponseEntity.status(200).body(modificado);
        }catch(NoSuchElementException e){
            return ResponseEntity.status(404).body(e.getMessage());
        }         
    }
    
    @PutMapping("/admin/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> modificarUsuarioAdmin(@PathVariable Long id, @RequestBody Usuario usuarioNuevo, @AuthenticationPrincipal Usuario usuarioLogado) {
        try{
            Usuario modificado = usuarioService.modificarUsuarioAdmin(id, usuarioNuevo, usuarioLogado);
            return ResponseEntity.status(200).body(modificado);
        }catch(NoSuchElementException e){
            return ResponseEntity.status(404).body(e.getMessage());
        }        
    }
    
    @PatchMapping("/pass")
    public ResponseEntity<?> cambioPasswordUser(@RequestBody CambioPass cambioPass, @AuthenticationPrincipal Usuario usuarioLogado){
        Long idUsuario = usuarioLogado.getId();
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
    public ResponseEntity<?> cambioPassAdmin(@PathVariable Long id, @AuthenticationPrincipal Usuario usuarioLogado){
        try{
            usuarioService.cambioPassAdmin(id, usuarioLogado);
            return ResponseEntity.status(200).build();
        }catch (NoSuchElementException e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }    
}

