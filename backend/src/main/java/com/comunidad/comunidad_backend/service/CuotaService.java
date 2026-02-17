package com.comunidad.comunidad_backend.service;
import com.comunidad.comunidad_backend.repository.MovimientoRepository;
import org.springframework.stereotype.Service;
import com.comunidad.comunidad_backend.entity.Cuota;
import com.comunidad.comunidad_backend.repository.CuotaRepository;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CuotaService {

    private final MovimientoRepository movimientoRepository;
    private final CuotaRepository cuotaRepository;

    public CuotaService(CuotaRepository cuotaRepository, MovimientoRepository movimientoRepository) {
        this.cuotaRepository = cuotaRepository;
        this.movimientoRepository = movimientoRepository;
    }

    public List<Cuota> findAll() {
        return cuotaRepository.findAll();
    }

    public Cuota save(Cuota cuota) {
        return cuotaRepository.save(cuota);
    }  

    public  List<Cuota> findByComunidadId (Long idComunidad) {
        return cuotaRepository.findByComunidadId(idComunidad);
    }

    public Cuota findById(Long id){
        return cuotaRepository.findById(id).orElse(null);
    }
    
    public Cuota modificarCuota(Long id, Cuota cuotaModificada){
        Cuota cuotaActual = cuotaRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Cuota no encontrada"));
        if(movimientoRepository.existsByCuotaId(id)){
            throw new IllegalArgumentException("La cuota ya tiene movimientos, no se puede modificar");
        }

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
        }
        if(cuotaModificada.getTipo() != null){
            cuotaActual.setTipo(cuotaModificada.getTipo());
        }
        return cuotaRepository.save(cuotaActual);
       
    }
}
