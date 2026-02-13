package com.comunidad.comunidad_backend.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import com.comunidad.comunidad_backend.entity.Cuota;
import com.comunidad.comunidad_backend.repository.CuotaRepository;
import java.util.List;

@Service
public class CuotaService {

    @Autowired
    private CuotaRepository cuotaRepository;

    public List<Cuota> findAll() {
        return cuotaRepository.findAll();
    }

    public Cuota save(Cuota cuota) {
        return cuotaRepository.save(cuota);
    }  

    public  List<Cuota> findByComunidadId (Long idComunidad) {
        return cuotaRepository.findByComunidadId(idComunidad);
    }

    public Cuota findById(Long id){
        return cuotaRepository.findById(id).orElse(null);
    }
    
}
