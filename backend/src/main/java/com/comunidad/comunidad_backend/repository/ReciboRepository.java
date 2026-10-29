package com.comunidad.comunidad_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.comunidad.comunidad_backend.entity.Recibo;
import com.comunidad.comunidad_backend.enums.EstadoRecibo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface ReciboRepository extends JpaRepository<Recibo, Long> {

    List<Recibo> findByUsuarioId(Long idUsuario);

    List<Recibo> findByComunidadId(Long idComunidad);

    List<Recibo> findByCuotaId(Long idCuota);

    Optional<Recibo> findByIdAndComunidadId(Long idRecibo, Long idComunidad);

    List<Recibo> findByCuotaIdAndComunidadId(Long idCuota, Long idComunidad);

    List<Recibo> findByUsuarioIdAndComunidadIdAndEstadoRecibo(Long idUsuario, Long idComunidad, EstadoRecibo estadoRecibo);

    List<Recibo> findByComunidadIdAndEstadoReciboAndCuotaFechaVencimientoBefore(Long idComunidad, EstadoRecibo estadoRecibo, LocalDate fechaActual);
    
}
