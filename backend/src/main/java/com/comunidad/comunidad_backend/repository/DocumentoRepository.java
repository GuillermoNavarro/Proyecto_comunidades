package com.comunidad.comunidad_backend.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


import com.comunidad.comunidad_backend.entity.Documento;

import java.util.List;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long>{

    List<Documento> findByComunidadId(Long idComunidad);
    
}
