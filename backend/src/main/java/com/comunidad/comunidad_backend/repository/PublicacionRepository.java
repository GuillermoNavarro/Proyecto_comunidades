package com.comunidad.comunidad_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.comunidad.comunidad_backend.entity.Publicacion;
import com.comunidad.comunidad_backend.enums.TipoPublicacion;

import java.util.List;
import java.util.Optional;

public interface PublicacionRepository extends JpaRepository<Publicacion, Long>{

    List<Publicacion> findByComunidadIdAndTipo(Long idcomunidad, TipoPublicacion tipo);

    Optional<Publicacion> findByIdAndComunidadId(Long id, Long idComunidad);

}
