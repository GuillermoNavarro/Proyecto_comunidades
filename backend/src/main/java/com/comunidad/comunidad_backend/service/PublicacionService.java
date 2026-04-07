package com.comunidad.comunidad_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import com.comunidad.comunidad_backend.repository.PublicacionRepository;
import com.comunidad.comunidad_backend.entity.Publicacion;
import com.comunidad.comunidad_backend.enus.TipoPublicacion;

import java.io.IOException;
import java.util.List;
import java.nio.file.*;

@Service
public class PublicacionService {

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Value("${app.upload.images.dir}")
    private String uploadDir;

    public List<Publicacion> listarPorComunidad(Long idComunidad, TipoPublicacion tipo){
        return publicacionRepository.findByComunidadIdAndTipo(idComunidad, tipo);
    }

    public Publicacion crearPublicacion(Publicacion publicacion, TipoPublicacion tipo){

        publicacion.setId(null);
        publicacion.setTipo(tipo);
        

        publicacionRepository.save(publicacion);
        return publicacion;
    }

    public Publicacion modPublicacion (Long id, Publicacion publicacion){
        Publicacion actual = publicacionRepository.findById(id).orElseThrow(() -> new RuntimeException("No encontrado"));
        
        if(publicacion.getTitulo() != null){actual.setTitulo(publicacion.getTitulo());}
        if(publicacion.getDescripcion() != null){actual.setDescripcion(publicacion.getDescripcion());}
        if(publicacion.getFechaFin() != null){actual.setFechaFin(publicacion.getFechaFin());}
        if(publicacion.getDocumento() != null){actual.setDocumento(publicacion.getDocumento());}


        return publicacionRepository.save(actual);
    }

    public boolean borrarPublicacion(Long id){
        Publicacion pub = publicacionRepository.findById(id).orElse(null);

        if(pub == null){
            return false;
        }
        if(pub.getImagen() != null){
            try{
                Path ruta = Paths.get(uploadDir).resolve(pub.getImagen());
                Files.deleteIfExists(ruta);
            }catch(IOException e){
                return false;
            }
        }
        publicacionRepository.delete(pub);
        return true;

    }
    
}
