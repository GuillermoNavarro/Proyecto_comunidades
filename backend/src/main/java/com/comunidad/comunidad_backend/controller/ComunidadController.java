package com.comunidad.comunidad_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.comunidad.comunidad_backend.entity.Comunidad;
import com.comunidad.comunidad_backend.repository.ComunidadRepository;

import java.util.List;


/**
 * Controlador que permite gestionar las comunidades
 */
@RestController
@RequestMapping("/api/comunidades")
public class ComunidadController {
    
    @Autowired
    private ComunidadRepository comunidadRepository;

    @GetMapping
    public List<Comunidad> getAllComunidades() {
        return comunidadRepository.findAll();
    }

    @PostMapping
    public Comunidad createComunidad(@RequestBody Comunidad comunidad) {
        return comunidadRepository.save(comunidad);
    }

}



