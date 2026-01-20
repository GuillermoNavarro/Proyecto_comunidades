package com.comunidad.comunidad_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.comunidad.comunidad_backend.entity.Movimiento;
import com.comunidad.comunidad_backend.repository.MovimientoRepository;







@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @GetMapping
    public List<Movimiento> getAllMovimientos(){
        return movimientoRepository.findAll();
    }

    @PostMapping
    public Movimiento createMovimiento(@RequestBody Movimiento movimiento){
        return movimientoRepository.save(movimiento);
    }

    @GetMapping("/comunidad/{idComunidad}")
    public List<Movimiento> getMovientosPorComunidad(@PathVariable Long idComunidad){
        return movimientoRepository.findByIdComunidad(idComunidad);
    }
    
    @GetMapping("/usuario/{idUsuario}")
    public List<Movimiento> getMovimientosPorUsuario(@PathVariable Long idUsuario){
        return movimientoRepository.findByIdUsuario(idUsuario);
    }
    
    
    
}
