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
    
}
