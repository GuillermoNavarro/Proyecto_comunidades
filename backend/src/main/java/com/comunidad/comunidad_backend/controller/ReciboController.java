package com.comunidad.comunidad_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.comunidad.comunidad_backend.security.JwtService;

import com.comunidad.comunidad_backend.service.ReciboService;
import com.comunidad.comunidad_backend.entity.Recibo;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;




@RestController
@RequestMapping("/api/recibos")
public class ReciboController {

    @Autowired
    private ReciboService reciboService;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> getRecibosId(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        Long idUsuario = jwtService.extraerIdUsuario(token);

        List<Recibo> lista = reciboService.listarPorUsuario(idUsuario);

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/comunidad")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> getRecibosComunidadId(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        Long idComunidad = jwtService.extraerIdComunidad(token);

        List<Recibo> lista = reciboService.listarPorComunidad(idComunidad);

        return ResponseEntity.ok(lista);
    }
    
    @GetMapping("/comunidad/morosos")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getRecibosMorosos(HttpServletRequest request){
        String token = request.getHeader("Authorization").substring(7);
        Long idComunidad = jwtService.extraerIdComunidad(token);

        List<Recibo> lista = reciboService.listarMorososPorComunidad(idComunidad);
        
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/usuario/{idUsuario}/pendientes")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getReciboPendiente(@PathVariable Long idUsuario, HttpServletRequest request){
        String token = request.getHeader("Authorization").substring(7);
        Long idComunidad = jwtService.extraerIdComunidad(token);

        List<Recibo> lista = reciboService.listarPendientePorUsuario(idUsuario, idComunidad);

        return ResponseEntity.ok(lista);    
    }    
    
}
