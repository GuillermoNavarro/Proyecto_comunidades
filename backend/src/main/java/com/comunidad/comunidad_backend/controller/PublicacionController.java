package com.comunidad.comunidad_backend.controller;

import com.comunidad.comunidad_backend.service.PublicacionService;
import com.comunidad.comunidad_backend.entity.Publicacion;
import com.comunidad.comunidad_backend.entity.Usuario;
import com.comunidad.comunidad_backend.enums.TipoPublicacion;
import com.comunidad.comunidad_backend.dto.ListarPublicaciones;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/publicaciones")

public class PublicacionController {

    private final PublicacionService publicacionService;
    
    public PublicacionController(
        PublicacionService publicacionService
    ){
        this.publicacionService = publicacionService;
    }

    @GetMapping("/noticias")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> getPublicacion(@AuthenticationPrincipal Usuario usuarioLogado) {
        
        List<ListarPublicaciones> lista = publicacionService.listarPorComunidad(usuarioLogado.getComunidad().getId(), TipoPublicacion.NOTICIA);

        return ResponseEntity.status(200).body(lista);
    }

    @PostMapping("/noticias")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity <?> crearPublicacion (@RequestBody Publicacion publicacion, @AuthenticationPrincipal Usuario usuarioLogado) {
    
        Publicacion nuevaPublicacion = publicacionService.crearPublicacion(publicacion, TipoPublicacion.NOTICIA, usuarioLogado);
        return ResponseEntity.status(201).body(nuevaPublicacion);
    }

    @PutMapping("/noticias/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> modificarPublicacion(@PathVariable Long id, @RequestBody Publicacion publicacion, @AuthenticationPrincipal Usuario usuarioLogado) {
    
        Publicacion modificado = publicacionService.modPublicacion(id, publicacion, usuarioLogado);
        return ResponseEntity.ok(modificado);
       
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> borrarPublicacion(@PathVariable Long id, @AuthenticationPrincipal Usuario usuarioLogado){
        publicacionService.borrarPublicacion(id, usuarioLogado);
        return ResponseEntity.status(200).body("eliminado");
    }

    
    
    
    
    
    
}
