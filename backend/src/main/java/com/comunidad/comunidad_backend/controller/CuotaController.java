package com.comunidad.comunidad_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.comunidad.comunidad_backend.entity.Comunidad;
import com.comunidad.comunidad_backend.entity.Cuota;
import com.comunidad.comunidad_backend.security.JwtService;
import com.comunidad.comunidad_backend.service.CuotaService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/cuotas")
public class CuotaController {

    @Autowired
    private CuotaService cuotaService;

    @Autowired 
    private JwtService jwtService;

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public List<Cuota> getAllCuotas(){
        return cuotaService.findAll();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public Cuota createCuota(@RequestBody Cuota cuota, @RequestParam(required = false) Long idUsuario, HttpServletRequest request){
        String token = request.getHeader("Authorization").substring(7);
        Long idComunidad = jwtService.extraerIdComunidad(token);
        Comunidad comunidad = new Comunidad();
        comunidad.setId(idComunidad);
        cuota.setComunidad(comunidad);
        return cuotaService.save(cuota, idUsuario);
    }
    
    @GetMapping("/comunidad")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Cuota> getCuotasPorComunidad(HttpServletRequest request){
        String token = request.getHeader("Authorization").substring(7);
        Long idComunidad = jwtService.extraerIdComunidad(token);

        return cuotaService.findByComunidadId(idComunidad);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getCuotasPorId(@PathVariable Long id, HttpServletRequest request){
        Cuota cuota = cuotaService.findById(id);
        if(cuota == null){
            return ResponseEntity.notFound().build();
        }
        String token = request.getHeader("Authorization").substring(7);
        Long idComunidad = jwtService.extraerIdComunidad(token);
        if(!cuota.getComunidad().getId().equals(idComunidad)){
            return ResponseEntity.status(403).body("No tienes permiso para ver cuotas de otra comunidad");
        }
        return ResponseEntity.ok(cuota);
    }
 
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> modificarCuota(@PathVariable Long id, @RequestBody Cuota cuota, HttpServletRequest request){
        String token = request.getHeader("Authorization").substring(7);
        Long idComunidad = jwtService.extraerIdComunidad(token);
        try{
            Cuota cuotaModificada = cuotaService.modificarCuota(id, cuota, idComunidad);
            return ResponseEntity.ok(cuotaModificada);
        }catch (NoSuchElementException e){
            return ResponseEntity.status(404).body(e.getMessage());
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(409).body(e.getMessage());
        }    
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> borrarCuota(@PathVariable Long id, HttpServletRequest request){
        String token = request.getHeader("Authorization").substring(7);
        Long idComunidad = jwtService.extraerIdComunidad(token);
        try{
            cuotaService.borrarCuota(id, idComunidad);
            return ResponseEntity.ok().body("Cuota eliminada correctamente");
        }catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }catch( IllegalArgumentException e){
            return ResponseEntity.status(409). body(e.getMessage());
        }
    }


    

    
    
    
    
    
}
