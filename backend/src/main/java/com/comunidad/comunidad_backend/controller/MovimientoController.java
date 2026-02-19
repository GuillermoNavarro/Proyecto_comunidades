package com.comunidad.comunidad_backend.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.comunidad.comunidad_backend.entity.Movimiento;
import com.comunidad.comunidad_backend.service.MovimientoService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


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

    @GetMapping("/{idMovimiento}")
    public ResponseEntity<Movimiento> getMovimientoPorId(@PathVariable Long idMovimiento){
        Movimiento movimiento = movimientoService.findById(idMovimiento);
        if(movimiento != null){
            return ResponseEntity.ok(movimiento);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    

    @PutMapping("/{id}")
    public ResponseEntity<?> modificarMovimiento(@PathVariable Long id, @RequestBody Movimiento movimiento){
        try{
            Movimiento movimientoModif = movimientoService.modificarMovimiento(id, movimiento);
            return ResponseEntity.ok(movimientoModif);
        }catch (NoSuchElementException e){
            return ResponseEntity.status(404).body(e.getMessage()); 
        }
    }
    
    
    
}
