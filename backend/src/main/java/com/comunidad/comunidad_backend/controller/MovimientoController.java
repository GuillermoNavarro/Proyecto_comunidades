package com.comunidad.comunidad_backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.comunidad.comunidad_backend.entity.Movimiento;
import com.comunidad.comunidad_backend.entity.Usuario;
import com.comunidad.comunidad_backend.service.MovimientoService;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

    private final MovimientoService movimientoService;

    public MovimientoController(
        MovimientoService movimientoService
    ){
        this.movimientoService = movimientoService;
    }

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public List<Movimiento> getAllMovimientos(){
        return movimientoService.findAll();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    ResponseEntity <?> createMovimiento(@RequestBody Movimiento movimiento, @AuthenticationPrincipal Usuario usuarioLogado){
        Movimiento nuevoMovimiento = movimientoService.save(movimiento, usuarioLogado);
        return ResponseEntity.status(201).body(nuevoMovimiento);
    }

    @GetMapping("/comunidad")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<Movimiento> getMovientosPorComunidad(@AuthenticationPrincipal Usuario usuarioLogado){
        return movimientoService.findByComunidadId(usuarioLogado.getComunidad().getId());
    }
    
    
    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<Movimiento> getMovimientosPorUsuario(@AuthenticationPrincipal Usuario usuarioLogado){
        return movimientoService.findByUsuarioId(usuarioLogado.getId());
    }

    
    @GetMapping("/{idMovimiento}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getMovimientoPorId(@PathVariable Long idMovimiento, @AuthenticationPrincipal Usuario usuarioLogado){
        Movimiento movimiento = movimientoService.findById(idMovimiento, usuarioLogado);
        return ResponseEntity.status(200).body(movimiento);
    }
    
}
