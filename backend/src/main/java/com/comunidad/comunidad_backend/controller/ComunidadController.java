package com.comunidad.comunidad_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.comunidad.comunidad_backend.entity.Comunidad;
import com.comunidad.comunidad_backend.service.ComunidadService;

import java.util.List;





/**
 * Controlador que permite gestionar las comunidades
 */
@RestController
@RequestMapping("/api/comunidades")
public class ComunidadController {
    
    @Autowired
    private ComunidadService comunidadService;

    @GetMapping
    public List<Comunidad> getAllComunidades() {
        return comunidadService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comunidad> getComunidadPorId(@PathVariable Long id){
        Comunidad comunidad = comunidadService.findById(id);
        if(comunidad != null){
            return ResponseEntity.ok(comunidad);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    

    @PostMapping
    public Comunidad createComunidad(@RequestBody Comunidad comunidad) {
        return comunidadService.save(comunidad);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modificarComunidad(@PathVariable Long id, @RequestBody Comunidad comunidadNuevo){
        Comunidad modificado = comunidadService.modificarComunidad(id, comunidadNuevo);
        if(modificado != null){
            return ResponseEntity.ok(modificado);
        } else {
            return ResponseEntity.status(404).body("Comunidad no encontrada.");
        }
    }

}



