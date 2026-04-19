package com.comunidad.comunidad_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comunidad.comunidad_backend.repository.ReciboRepository;
import com.comunidad.comunidad_backend.repository.UsuarioRepository;
import com.comunidad.comunidad_backend.entity.Recibo;
import com.comunidad.comunidad_backend.entity.Cuota;
import com.comunidad.comunidad_backend.entity.Usuario;
import com.comunidad.comunidad_backend.enus.EstadoRecibo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReciboService {

    @Autowired
    private ReciboRepository reciboRepository;

    @Autowired UsuarioRepository usuarioRepository;

    private void generarRecibo(Usuario usuario, Cuota cuota, BigDecimal coeficiente){
        
        BigDecimal divisor = new BigDecimal("100");

        BigDecimal importeRecibo = cuota.getImporte().multiply(coeficiente).divide(divisor,2 ,RoundingMode.HALF_UP);

        Recibo recibo = new Recibo();
        recibo.setCuota(cuota);
        recibo.setComunidad(cuota.getComunidad());
        recibo.setUsuario(usuario);
        recibo.setImporte(importeRecibo);
        recibo.setEstadoRecibo(EstadoRecibo.PENDIENTE);

        reciboRepository.save(recibo); 
    }

    public List<Recibo> listarPorUsuario(Long idUsuario){
        return reciboRepository.findByUsuarioId(idUsuario);
    }

    public List<Recibo> listarPorComunidad(Long idComunidad){
        return reciboRepository.findByComunidadId(idComunidad);
    }

    public void crearRecibos(Cuota cuota){

        List<Usuario> vecinos = usuarioRepository.findByComunidadId(cuota.getComunidad().getId());

        for(Usuario vecino : vecinos){
            if(vecino.getCoeficiente() == null || vecino.getCoeficiente() <= 0){
                continue;
            }
            BigDecimal coeficiente  = BigDecimal.valueOf(vecino.getCoeficiente());
            generarRecibo(vecino, cuota, coeficiente);
        }        
    }

    public void crearReciboIndividual(Cuota cuota, Usuario vecino){
        BigDecimal coeficiente = new BigDecimal("100");
        generarRecibo(vecino, cuota, coeficiente);       
    }

    public List<Recibo> listarMorososPorComunidad(Long idComunidad){
        return reciboRepository.findByComunidadIdAndEstadoReciboAndCuotaFechaVencimientoBefore(idComunidad, EstadoRecibo.PENDIENTE, LocalDate.now());
    }

    public List<Recibo> listarPendientePorUsuario(Long idUsuario, Long idComunidad){
        return reciboRepository.findByUsuarioIdAndComunidadIdAndEstadoRecibo(idUsuario, idComunidad, EstadoRecibo.PENDIENTE);
    }

    public void marcarPagado(Long idRecibo){
        Recibo recibo = reciboRepository.findById(idRecibo).orElseThrow(() -> new IllegalArgumentException("Recibo no encontrado"));
        if(recibo.getEstadoRecibo() == EstadoRecibo.PAGADO){
            throw new IllegalArgumentException("El recibo ya esta pagado");
        }
        recibo.setEstadoRecibo(EstadoRecibo.PAGADO);
        reciboRepository.save(recibo);
    }

    public void borrarRecibos(Cuota cuota){
        List<Recibo> recibos = reciboRepository.findByCuotaId(cuota.getId());
        for (Recibo recibo : recibos){
            reciboRepository.delete(recibo);
        }
    }

    public List<Recibo> listarRecibosPorCuotas(Long idCuota, Long idComunidad){
        List<Recibo> recibo = reciboRepository.findByCuotaIdAndComunidadId(idCuota, idComunidad);
        return recibo;
    }

}
