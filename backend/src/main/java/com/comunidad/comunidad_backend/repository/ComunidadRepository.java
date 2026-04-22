package com.comunidad.comunidad_backend.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.comunidad.comunidad_backend.entity.Comunidad;
import com.comunidad.comunidad_backend.dto.ActivoComunidad;

import java.util.List;

@Repository
public interface ComunidadRepository extends JpaRepository<Comunidad, Long> {

    @Query("SELECT new com.comunidad.comunidad_backend.dto.ActivoComunidad(c.id, c.nombre, c.codPostal, COUNT(u.id)) " +
            "FROM Comunidad c " +
            "LEFT JOIN Usuario u ON u.comunidad.id = c.id AND u.estado = true " +
            "GROUP BY c.id, c.nombre")
    List<ActivoComunidad> obtenerComunidadesConActivos();
    
    
}
