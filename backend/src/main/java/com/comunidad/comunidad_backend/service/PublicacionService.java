package com.comunidad.comunidad_backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.comunidad.comunidad_backend.repository.PublicacionRepository;
import com.comunidad.comunidad_backend.entity.Publicacion;
import com.comunidad.comunidad_backend.entity.Usuario;
import com.comunidad.comunidad_backend.enums.TipoPublicacion;
import com.comunidad.comunidad_backend.exception.SistemaArchivoException;
import com.comunidad.comunidad_backend.dto.ListarPublicaciones;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.nio.file.*;

@Service
public class PublicacionService {

    private final PublicacionRepository publicacionRepository;

    public PublicacionService(
        PublicacionRepository publicacionRepository
    ){
        this.publicacionRepository = publicacionRepository;
    }

    @Value("${app.upload.images.dir}")
    private String uploadDir;

    private ListarPublicaciones aDTO(Publicacion p){
        ListarPublicaciones dto = new ListarPublicaciones();
        dto.setId(p.getId());
        dto.setTipo(p.getTipo());
        dto.setTitulo(p.getTitulo());
        dto.setDescripcion(p.getDescripcion());
        dto.setFechaCreacion(p.getFechaCreacion());
        dto.setFechaFin(p.getFechaFin());
        if(p.getDocumento() != null){
            dto.setDocumentoNombre(p.getDocumento().getNombre());
            dto.setDocumentoArchivo(p.getDocumento().getDocumento());
        }
        return dto;
    }

    public List<ListarPublicaciones> listarPorComunidad(Long idComunidad, TipoPublicacion tipo){
        return publicacionRepository.findByComunidadIdAndTipo(idComunidad, tipo)
            .stream()
            .map(this::aDTO)
            .toList();
    }

    public Publicacion crearPublicacion(Publicacion publicacion, TipoPublicacion tipo, Usuario usuarioLogado){

        publicacion.setId(null);
        publicacion.setTipo(tipo);
        publicacion.setComunidad(usuarioLogado.getComunidad());
        publicacion.setUsuario(usuarioLogado);        

        publicacionRepository.save(publicacion);
        return publicacion;
    }

    public Publicacion modPublicacion (Long id, Publicacion publicacion, Usuario usuarioLogado){
        Publicacion actual = publicacionRepository.findByIdAndComunidadId(id, usuarioLogado.getComunidad().getId()).orElseThrow(() -> new NoSuchElementException("Publicación no encontrada"));
        
        if(publicacion.getTitulo() != null){actual.setTitulo(publicacion.getTitulo());}
        if(publicacion.getDescripcion() != null){actual.setDescripcion(publicacion.getDescripcion());}
        if(publicacion.getFechaFin() != null){actual.setFechaFin(publicacion.getFechaFin());}
        if(publicacion.getDocumento() != null){actual.setDocumento(publicacion.getDocumento());}


        return publicacionRepository.save(actual);
    }

    public void borrarPublicacion(Long id, Usuario usuarioLogado){
        Publicacion pub = publicacionRepository.findByIdAndComunidadId(id, usuarioLogado.getComunidad().getId()).orElseThrow(() -> new NoSuchElementException("Publicación no encontrada"));

        if(pub.getImagen() != null){
            try{
                Path ruta = Paths.get(uploadDir).resolve(pub.getImagen());
                Files.deleteIfExists(ruta);
            }catch(IOException e){
                throw new SistemaArchivoException("Error del servidor al eliminar el archivo");
            }
        }
        publicacionRepository.delete(pub);

    }
    
}
