package com.comunidad.comunidad_backend.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.comunidad.comunidad_backend.entity.Movimiento;


import java.util.List;



@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    
    List<Movimiento> findByComunidadId(Long idComunidad);
    List<Movimiento> findByUsuarioId(Long idUsuario);
    List<Movimiento> findByCuotaId(Long idCuota);
    boolean existsByCuotaId(Long idCuota);

}