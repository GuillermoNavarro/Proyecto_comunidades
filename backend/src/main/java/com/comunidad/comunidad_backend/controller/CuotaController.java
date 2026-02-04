package com.comunidad.comunidad_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.comunidad.comunidad_backend.entity.Cuota;
import com.comunidad.comunidad_backend.service.CuotaService;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/cuotas")
public class CuotaController {

    @Autowired
    private CuotaService cuotaService;

    @GetMapping
    public List<Cuota> getAllCuotas(){
        return cuotaService.findAll();
    }

    @PostMapping
    public Cuota createCuota(@RequestBody Cuota cuota){
        return cuotaService.save(cuota);
    }

    @GetMapping("/comunidad/{idComunidad}")
    public List<Cuota> getCuotasPorComunidad(@PathVariable Long idComunidad){
        return cuotaService.findByComunidadId(idComunidad);
    }
    
    
    
}
