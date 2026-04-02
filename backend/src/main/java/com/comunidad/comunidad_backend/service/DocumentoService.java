package com.comunidad.comunidad_backend.service;

import java.util.List;
import java.util.UUID;
import java.nio.file.*;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.comunidad.comunidad_backend.repository.ComunidadRepository;
import com.comunidad.comunidad_backend.repository.DocumentoRepository;
import com.comunidad.comunidad_backend.dto.ModifDocumento;
import com.comunidad.comunidad_backend.entity.Documento;
import org.springframework.beans.factory.annotation.Value;

@Service
public class DocumentoService {

    @Autowired
    private DocumentoRepository documentoRespository;

    @Autowired
    private ComunidadRepository comunidadRepository;

    @Value("${app.upload.dir}")
    private String uploadDir;

    public Documento crearDocumento(MultipartFile file, Documento documento, Long idComunidad) throws java.io.IOException{
        Path directorio = Paths.get(uploadDir);
        if(!Files.exists(directorio)){
            Files.createDirectories(directorio);
        }   
        
        String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String nombreNuevo = UUID.randomUUID().toString() + extension;

        Path ruta = directorio.resolve(nombreNuevo);
        Files.copy(file.getInputStream(), ruta);

        documento.setDocumento(nombreNuevo);
        documento.setComunidad(comunidadRepository.getReferenceById(idComunidad));
        
        try{
            return documentoRespository.save(documento);
        }catch (Exception e){
            Files.deleteIfExists(ruta);
            throw e;
        }
        
    }

    public List<Documento> listarPorComunidad(Long idComunidad, Long idComunidadManual, String rol){

        Long idFinal;

        if("SUPER_ADMIN".equals(rol) && idComunidadManual !=null){
            idFinal = idComunidadManual;
        }else{
            idFinal = idComunidad;
        }
        return documentoRespository.findByComunidadId(idFinal);
    }


    public Documento actualizarCampos(Long id, ModifDocumento modifDocumento){
        Documento actual = documentoRespository.findById(id).orElseThrow(() -> new RuntimeException("No encontrado"));

        if(modifDocumento.getNombre() != null){
            actual.setNombre(modifDocumento.getNombre());
        }
        if(modifDocumento.getDescripcion() != null){
            actual.setDescripcion(modifDocumento.getDescripcion());
        }

        return documentoRespository.save(actual);
    }


    public boolean borrarDocumento(Long id){
        Documento doc = documentoRespository.findById(id).orElse(null);

        if(doc == null){
            return false;
        }

        try{
            Path ruta = Paths.get(uploadDir).resolve(doc.getDocumento());
            Files.deleteIfExists(ruta);
        }catch(IOException e){
            return false;
        }

        documentoRespository.delete(doc);
        return true;
    }
    
}
