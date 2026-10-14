package com.comunidad.comunidad_backend.repository;
import com.comunidad.comunidad_backend.entity.Documento;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DocumentoRepository extends JpaRepository<Documento, Long>{

    List<Documento> findByComunidadId(Long idComunidad);

    Optional<Documento> findByIdAndComunidadId(Long id, Long idComunidad);
    
}
