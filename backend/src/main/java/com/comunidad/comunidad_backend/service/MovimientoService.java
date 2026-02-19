package com.comunidad.comunidad_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.comunidad.comunidad_backend.entity.Movimiento;
import com.comunidad.comunidad_backend.repository.MovimientoRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    public List<Movimiento> findAll() {
        return movimientoRepository.findAll();
    }

    public Movimiento save(Movimiento movimiento) {
        return movimientoRepository.save(movimiento);
    } 

    public  List<Movimiento> findByComunidadId (Long idComunidad) {
        return movimientoRepository.findByComunidadId(idComunidad);
    }

    public  List<Movimiento> findByUsuarioId (Long idUsuario) {
        return movimientoRepository.findByUsuarioId(idUsuario);
    }

    public Movimiento findById(Long idMovimiento){
        return movimientoRepository.findById(idMovimiento).orElse(null);
    }

    public Movimiento modificarMovimiento(Long id, Movimiento movimientoModificado){
        Movimiento movimientoActual = movimientoRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Movimiento no encontrado"));
        
        if(movimientoActual.getNombre() != null){
            movimientoActual.setNombre(movimientoModificado.getNombre());
        }
        if(movimientoActual.getUsuario() != null){
            movimientoActual.setUsuario(movimientoModificado.getUsuario());
        }
        if(movimientoActual.getCuota() != null){
            movimientoActual.setCuota(movimientoModificado.getCuota());
        }
        return movimientoRepository.save(movimientoActual);
    }
    
}
