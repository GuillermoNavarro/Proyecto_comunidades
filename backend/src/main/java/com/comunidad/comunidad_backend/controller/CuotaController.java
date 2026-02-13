package com.comunidad.comunidad_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.comunidad.comunidad_backend.entity.Cuota;
import com.comunidad.comunidad_backend.service.CuotaService;

import java.util.List;








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

    @GetMapping("/{idUsuario}")
    public ResponseEntity<Cuota> getCuotaPorId(@PathVariable Long idUsuario){
        Cuota cuota = cuotaService.findById(idUsuario);
        if(cuota != null){
            return ResponseEntity.ok(cuota);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    
    
    
    
    
}
