package com.comunidad.comunidad_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import com.comunidad.comunidad_backend.entity.Usuario;
import java.util.List;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    List<Usuario> findByComunidadId(Long idComunidad);

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByDni(String dni);

    Optional<Usuario> findByIdAndComunidadId(Long id, Long idComunidad);
}

