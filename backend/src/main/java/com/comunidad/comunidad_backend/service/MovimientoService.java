package com.comunidad.comunidad_backend.service;

import org.springframework.stereotype.Service;
import com.comunidad.comunidad_backend.entity.Movimiento;
import com.comunidad.comunidad_backend.entity.Recibo;
import com.comunidad.comunidad_backend.entity.Usuario;
import com.comunidad.comunidad_backend.enums.TipoMovimiento;
import com.comunidad.comunidad_backend.repository.MovimientoRepository;
import com.comunidad.comunidad_backend.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.NoSuchElementException;


@Service
public class MovimientoService {

    private final ReciboService reciboService;
    private final MovimientoRepository movimientoRepository;
    private final UsuarioRepository usuarioRepository;

    public MovimientoService(
        MovimientoRepository movimientoRepository,
        ReciboService reciboService,
        UsuarioRepository usuarioRepository
    ){
        this.movimientoRepository = movimientoRepository;
        this.reciboService = reciboService;
        this.usuarioRepository = usuarioRepository;
    }


    public List<Movimiento> findAll() {
        return movimientoRepository.findAll();
    }

    @Transactional
    public Movimiento save(Movimiento movimiento, Usuario usuarioLogado) {
        movimiento.setId(null);
        movimiento.setComunidad((usuarioLogado.getComunidad()));

        if(movimiento.getUsuario() != null){
            Usuario vecino = usuarioRepository.findByIdAndComunidadId(
                movimiento.getUsuario().getId(),
                usuarioLogado.getComunidad().getId()
            ).orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));
            movimiento.setUsuario(vecino);
        }

        if(movimiento.getRecibo() != null){
            if(movimiento.getTipo() != TipoMovimiento.INGRESO){
                throw new IllegalArgumentException("Solo los movimientos de tipo INGRESO pueden tener recibo asociado");
            }
            Recibo recibo = reciboService.marcarPagado(movimiento.getRecibo().getId(), usuarioLogado.getComunidad().getId());
            movimiento.setRecibo(recibo);       
        }

        return movimientoRepository.save(movimiento);
    } 

    public List<Movimiento> findByComunidadId (Long idComunidad) {
        return movimientoRepository.findByComunidadId(idComunidad);
    }

    public List<Movimiento> findByUsuarioId (Long idUsuario) {
        return movimientoRepository.findByUsuarioId(idUsuario);
    }

    public Movimiento findById(Long idMovimiento, Usuario usuarioLogado){
        return movimientoRepository.findByIdAndComunidadId(idMovimiento, usuarioLogado.getComunidad().getId())
            .orElseThrow(() -> new NoSuchElementException("Movimiento no encontrado"));
    }

    
}
