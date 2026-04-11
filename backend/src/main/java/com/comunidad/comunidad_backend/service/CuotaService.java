package com.comunidad.comunidad_backend.service;
import com.comunidad.comunidad_backend.repository.MovimientoRepository;
import com.comunidad.comunidad_backend.repository.UsuarioRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comunidad.comunidad_backend.entity.Cuota;
import com.comunidad.comunidad_backend.entity.Usuario;
import com.comunidad.comunidad_backend.enus.TipoCuota;
import com.comunidad.comunidad_backend.repository.CuotaRepository;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CuotaService {

    private final MovimientoRepository movimientoRepository;
    private final CuotaRepository cuotaRepository;
    private final ReciboService reciboService;
    private final UsuarioRepository usuarioRepository;

    public CuotaService(CuotaRepository cuotaRepository, MovimientoRepository movimientoRepository, ReciboService reciboService, UsuarioRepository usuarioRepository) {
        this.cuotaRepository = cuotaRepository;
        this.movimientoRepository = movimientoRepository;
        this.reciboService = reciboService;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Cuota> findAll() {
        return cuotaRepository.findAll();
    }

    @Transactional
    public Cuota save(Cuota cuota, Long idUsuario) {
        Cuota cuotaGuardada = cuotaRepository.save(cuota);
        if(cuota.getTipo() == TipoCuota.INDIVIDUAL){
            if(idUsuario == null){
                throw new IllegalArgumentException("Falta seleccionar el vecino para asociarle esta cuota");
            }

            Usuario vecino = usuarioRepository.findById(idUsuario).orElseThrow();
            reciboService.crearReciboIndividual(cuotaGuardada, vecino);
        }else{
            reciboService.crearRecibos(cuotaGuardada);
        }
        
        return cuotaGuardada;
    }  

    public  List<Cuota> findByComunidadId (Long idComunidad) {
        return cuotaRepository.findByComunidadId(idComunidad);
    }

    public Cuota findById(Long id){
        return cuotaRepository.findById(id).orElse(null);
    }
    
    @Transactional
    public Cuota modificarCuota(Long id, Cuota cuotaModificada, Long idComunidad){
        Cuota cuotaActual = cuotaRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Cuota no encontrada"));
        if(!cuotaActual.getComunidad().getId().equals(idComunidad)){
            throw new IllegalArgumentException("No tienes permiso para modificar cuotas de otra comunidad");
        }
        if(movimientoRepository.existsByReciboCuotaId(id)){
            throw new IllegalArgumentException("La cuota ya tiene movimientos, no se puede modificar");
        }
        boolean recalculo = false;
        

        if(cuotaModificada.getNombre() != null){
            cuotaActual.setNombre(cuotaModificada.getNombre());
        }
        if(cuotaModificada.getFechaEmision() != null){
            cuotaActual.setFechaEmision(cuotaModificada.getFechaEmision());
        }
        if(cuotaModificada.getFechaVencimiento() != null){
            cuotaActual.setFechaVencimiento(cuotaModificada.getFechaVencimiento());
        }
        if(cuotaModificada.getImporte() != null){
            cuotaActual.setImporte(cuotaModificada.getImporte());
            recalculo = true;
        }
        
        Cuota cuotaGuardada = cuotaRepository.save(cuotaActual);

        if(recalculo){
            reciboService.borrarRecibos(cuotaGuardada);
            reciboService.crearRecibos(cuotaGuardada);
        }
        
        
        return cuotaGuardada;     
    }

    @Transactional
    public void borrarCuota(Long idCuota, Long idComunidad){
        Cuota cuotaActual = cuotaRepository.findById(idCuota).orElseThrow(() -> new NoSuchElementException("Cuota no encontrada"));
        if(!cuotaActual.getComunidad().getId().equals(idComunidad)){
            throw new IllegalArgumentException("No tienes permiso para borrar cuotas de otra comunidad");
        }
        if(movimientoRepository.existsByReciboCuotaId(idCuota)){
            throw new IllegalArgumentException("La cuota ya tiene movimientos, no se puede borrar");
        }
        reciboService.borrarRecibos(cuotaActual);
        cuotaRepository.delete(cuotaActual);

    }
}
