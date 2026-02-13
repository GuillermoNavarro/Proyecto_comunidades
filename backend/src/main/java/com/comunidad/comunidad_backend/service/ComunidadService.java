package com.comunidad.comunidad_backend.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import java.util.List;
import com.comunidad.comunidad_backend.entity.Comunidad;
import com.comunidad.comunidad_backend.repository.ComunidadRepository;

@Service
public class ComunidadService {

    @Autowired
    private ComunidadRepository comunidadRepository;

    public List<Comunidad> findAll() {
        return comunidadRepository.findAll();
    }

    public Comunidad save(Comunidad comunidad) {
        return comunidadRepository.save(comunidad);
    }

    public Comunidad findById(Long id){
        return comunidadRepository.findById(id).orElse(null);
    }
    
    public Comunidad modificarComunidad(Long id, Comunidad comunidadNuevo){
        Comunidad comunidadActual = comunidadRepository.findById(id).orElse(null);
        if(comunidadActual==null){
            return null;
        }
        if(comunidadNuevo.getNombre() != null){
            comunidadActual.setNombre(comunidadNuevo.getNombre());
        }
        if(comunidadNuevo.getDireccion() != null){
            comunidadActual.setDireccion(comunidadNuevo.getDireccion());
        }
        if(comunidadNuevo.getCiudad() != null){
            comunidadActual.setCiudad(comunidadNuevo.getCiudad());
        }   
        if(comunidadNuevo.getCodPostal() != null){
            comunidadActual.setCodPostal(comunidadNuevo.getCodPostal());
        }
        comunidadRepository.save(comunidadActual);
        return comunidadActual;
    }

    
}
