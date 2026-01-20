package com.comunidad.comunidad_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.comunidad.comunidad_backend.entity.Cuota;
import com.comunidad.comunidad_backend.repository.CuotaRepository;

import java.util.List;

@RestController
@RequestMapping
public class CuotaController {

    @Autowired
    private CuotaRepository cuotaRepository;

    @GetMapping
    public List<Cuota> getAllCuotas(){
        return cuotaRepository.findAll();
    }

    @PostMapping
    public Cuota createCuota(@RequestBody Cuota cuota){
        return cuotaRepository.save(cuota);
    }
    
    
}
