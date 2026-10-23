package com.comunidad.comunidad_backend.controller;

import com.comunidad.comunidad_backend.dto.CambioPass;


import java.util.List;
import java.security.Principal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.comunidad.comunidad_backend.entity.Comunidad;
import com.comunidad.comunidad_backend.entity.Usuario;
import com.comunidad.comunidad_backend.service.UsuarioService;


@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(
        UsuarioService usuarioService
    ){
        this.usuarioService = usuarioService;
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
        Usuario nuevoUsuario = usuarioService.crearUsuario(usuario);
        return ResponseEntity.status(201).body(nuevoUsuario);
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
        return ResponseEntity.status(200).body(usuario);
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
        usuarioService.deleteUsuario(idUsuario, usuarioLogado);
        return ResponseEntity.status(200).body("Usuario eliminado correctamente."); 
    }

    
    @PutMapping("/modificar")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> modificarUsuario(@RequestBody Usuario usuarioNuevo, @AuthenticationPrincipal Usuario usuarioLogado) {
        Long idUsuario = usuarioLogado.getId();
        Usuario modificado = usuarioService.modificarUsuario(idUsuario, usuarioNuevo);
        return ResponseEntity.status(200).body(modificado);      
    }
    
    @PutMapping("/admin/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> modificarUsuarioAdmin(@PathVariable Long id, @RequestBody Usuario usuarioNuevo, @AuthenticationPrincipal Usuario usuarioLogado) {
        Usuario modificado = usuarioService.modificarUsuarioAdmin(id, usuarioNuevo, usuarioLogado);
        return ResponseEntity.status(200).body(modificado);     
    }
    
    @PatchMapping("/pass")
    public ResponseEntity<?> cambioPasswordUser(@RequestBody CambioPass cambioPass, @AuthenticationPrincipal Usuario usuarioLogado){
        Long idUsuario = usuarioLogado.getId();
        if(cambioPass.getOldPassword() == null || cambioPass.getOldPassword().isBlank() ||
            cambioPass.getNewPassword() == null || cambioPass.getNewPassword().isBlank()){
            return ResponseEntity.status(400).body("Los datos de cambio de contraseña son obligatorios");
        }
        usuarioService.cambioPassword(idUsuario, cambioPass);
        return ResponseEntity.status(201).body("Contraseña modificada");       
    }

    @PatchMapping("/admin/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> cambioPassAdmin(@PathVariable Long id, @AuthenticationPrincipal Usuario usuarioLogado){
        usuarioService.cambioPassAdmin(id, usuarioLogado);
        return ResponseEntity.status(204).build();
    }    
}

