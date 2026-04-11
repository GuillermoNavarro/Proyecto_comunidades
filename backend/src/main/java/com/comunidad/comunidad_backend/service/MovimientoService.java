package com.comunidad.comunidad_backend.service;

import org.springframework.stereotype.Service;
import com.comunidad.comunidad_backend.entity.Movimiento;
import com.comunidad.comunidad_backend.enus.TipoMovimiento;
import com.comunidad.comunidad_backend.repository.MovimientoRepository;

import jakarta.transaction.Transactional;

import java.util.List;


@Service
public class MovimientoService {

    private final ReciboService reciboService;
    private final MovimientoRepository movimientoRepository;

    public MovimientoService(
        MovimientoRepository movimientoRepository,
        ReciboService reciboService
    ){
        this.movimientoRepository = movimientoRepository;
        this.reciboService = reciboService;
    }


    public List<Movimiento> findAll() {
        return movimientoRepository.findAll();
    }

    @Transactional
    public Movimiento save(Movimiento movimiento) {
        if(movimiento.getRecibo() != null && movimiento.getTipo() != TipoMovimiento.INGRESO){
            throw new IllegalArgumentException("Solo los movimientos de tipo INGRESO pueden tener recibo asociado");
        }
        Movimiento nuevoMovimiento = movimientoRepository.save(movimiento);
        if(nuevoMovimiento.getRecibo() != null){
            reciboService.marcarPagado(nuevoMovimiento.getRecibo().getId());
        }
        return nuevoMovimiento;
    } 

    public List<Movimiento> findByComunidadId (Long idComunidad) {
        return movimientoRepository.findByComunidadId(idComunidad);
    }

    public List<Movimiento> findByUsuarioId (Long idUsuario) {
        return movimientoRepository.findByUsuarioId(idUsuario);
    }

    public Movimiento findById(Long idMovimiento){
        return movimientoRepository.findById(idMovimiento).orElse(null);
    }

    
}
