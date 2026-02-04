package com.comunidad.comunidad_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.comunidad.comunidad_backend.entity.Movimiento;
import com.comunidad.comunidad_backend.service.MovimientoService;







@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;

    @GetMapping
    public List<Movimiento> getAllMovimientos(){
        return movimientoService.findAll();
    }

    @PostMapping
    public Movimiento createMovimiento(@RequestBody Movimiento movimiento){
        return movimientoService.save(movimiento);
    }

    @GetMapping("/comunidad/{idComunidad}")
    public List<Movimiento> getMovientosPorComunidad(@PathVariable Long idComunidad){
        return movimientoService.findByComunidadId(idComunidad);
    }
    
    @GetMapping("/usuario/{idUsuario}")
    public List<Movimiento> getMovimientosPorUsuario(@PathVariable Long idUsuario){
        return movimientoService.findByUsuarioId(idUsuario);
    }
    
    
    
}
