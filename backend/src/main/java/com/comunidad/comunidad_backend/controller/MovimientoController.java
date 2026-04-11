package com.comunidad.comunidad_backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.comunidad.comunidad_backend.entity.Comunidad;
import com.comunidad.comunidad_backend.entity.Movimiento;
import com.comunidad.comunidad_backend.security.JwtService;
import com.comunidad.comunidad_backend.service.MovimientoService;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

    private final MovimientoService movimientoService;
    private final JwtService jwtService;

    public MovimientoController(
        MovimientoService movimientoService,
        JwtService jwtService
    ){
        this.movimientoService = movimientoService;
        this.jwtService = jwtService;
    }

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public List<Movimiento> getAllMovimientos(){
        return movimientoService.findAll();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public Movimiento createMovimiento(@RequestBody Movimiento movimiento, HttpServletRequest request){
        String token = request.getHeader("Authorization").substring(7);
        Long idComunidad = jwtService.extraerIdComunidad(token);
        Comunidad comunidad = new Comunidad();
        comunidad.setId(idComunidad);
        movimiento.setComunidad(comunidad);
        return movimientoService.save(movimiento);
    }

    @GetMapping("/comunidad")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<Movimiento> getMovientosPorComunidad(HttpServletRequest request){
        String token = request.getHeader("Authorization").substring(7);
        Long idComunidad = jwtService.extraerIdComunidad(token);
        return movimientoService.findByComunidadId(idComunidad);
    }
    
    
    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<Movimiento> getMovimientosPorUsuario(HttpServletRequest request){
        String token = request.getHeader("Authorization").substring(7);
        Long idUsuario = jwtService.extraerIdUsuario(token);
        return movimientoService.findByUsuarioId(idUsuario);
    }

    
    @GetMapping("/{idMovimiento}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getMovimientoPorId(@PathVariable Long idMovimiento, HttpServletRequest request){
        Movimiento movimiento = movimientoService.findById(idMovimiento);
        if(movimiento == null){
            return ResponseEntity.notFound().build();
        }
        String token = request.getHeader("Authorization").substring(7);
        Long idComunidad = jwtService.extraerIdComunidad(token);
        if(!movimiento.getComunidad().getId().equals(idComunidad)){
            return ResponseEntity.status(403).body("No tienes permiso para ver movimientos de otra comunidad");    
        }      
        return ResponseEntity.ok(movimiento);
    }
    
}
