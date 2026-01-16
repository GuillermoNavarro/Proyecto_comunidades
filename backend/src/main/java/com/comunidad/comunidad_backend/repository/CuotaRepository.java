package com.comunidad.comunidad_backend.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.comunidad.comunidad_backend.entity.Cuota;

@Repository
public interface CuotaRepository extends JpaRepository<Cuota, Long> {
    
}
