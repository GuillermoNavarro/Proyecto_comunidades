package com.comunidad.comunidad_backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comunidad.comunidad_backend.security.JwtService;
import com.comunidad.comunidad_backend.service.PublicacionService;
import com.comunidad.comunidad_backend.entity.Comunidad;
import com.comunidad.comunidad_backend.entity.Publicacion;
import com.comunidad.comunidad_backend.entity.Usuario;
import com.comunidad.comunidad_backend.enus.TipoPublicacion;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("api/publicaciones")

public class PublicacionController {

    @Autowired
    private PublicacionService publicacionService;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/noticias")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> getPublicacion(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        Long idComunidad = jwtService.extraerIdComunidad(token);

        List<Publicacion> lista = publicacionService.listarPorComunidad(idComunidad, TipoPublicacion.NOTICIA);

        return ResponseEntity.ok(lista);
    }

    @PostMapping("/noticias")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity <?> crearPublicacion (@RequestBody Publicacion publicacion, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        Comunidad idComunidad = new Comunidad();
        Usuario idUsuario = new Usuario();
        idComunidad.setId(jwtService.extraerIdComunidad(token));
        idUsuario.setId(jwtService.extraerIdUsuario(token));
        publicacion.setComunidad(idComunidad);
        publicacion.setUsuario(idUsuario);
        try{
            Publicacion nuevaPublicacion = publicacionService.crearPublicacion(publicacion, TipoPublicacion.NOTICIA);
            return ResponseEntity.ok(nuevaPublicacion);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/noticias/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> modificarPublicacion(@PathVariable Long id, @RequestBody Publicacion publicacion) {
       try{
        Publicacion modificado = publicacionService.modPublicacion(id, publicacion);
        return ResponseEntity.ok(modificado);
       }catch(Exception err){
        return ResponseEntity.notFound().build();
       }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> borrarPublicacion(@PathVariable Long id){
        boolean eliminado = publicacionService.borrarPublicacion(id);
        if(eliminado){
            return ResponseEntity.ok("eliminado");
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    
    
    
    
    
    
}
