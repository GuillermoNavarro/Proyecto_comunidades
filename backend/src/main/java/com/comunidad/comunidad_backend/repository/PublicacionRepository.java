package com.comunidad.comunidad_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.comunidad.comunidad_backend.entity.Publicacion;
import com.comunidad.comunidad_backend.enums.TipoPublicacion;

import java.util.List;


@Repository
public interface PublicacionRepository extends JpaRepository<Publicacion, Long>{

    List<Publicacion> findByComunidadIdAndTipo(Long idcomunidad, TipoPublicacion tipo);

}
