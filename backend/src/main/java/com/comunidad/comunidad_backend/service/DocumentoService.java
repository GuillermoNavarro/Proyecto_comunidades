package com.comunidad.comunidad_backend.service;

import java.util.List;
import java.util.UUID;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.comunidad.comunidad_backend.repository.ComunidadRepository;
import com.comunidad.comunidad_backend.repository.DocumentoRepository;
import com.comunidad.comunidad_backend.dto.ModifDocumento;
import com.comunidad.comunidad_backend.entity.Documento;
import com.comunidad.comunidad_backend.entity.Usuario;
import com.comunidad.comunidad_backend.enums.Rol;
import com.comunidad.comunidad_backend.exception.ArchivoInvalidoException;
import com.comunidad.comunidad_backend.exception.SistemaArchivoException;

import org.springframework.beans.factory.annotation.Value;

@Service
public class DocumentoService {

    private final DocumentoRepository documentoRepository;
    private final ComunidadRepository comunidadRepository;

    public DocumentoService(
        DocumentoRepository documentoRepository,
        ComunidadRepository comunidadRepository
    ){
        this.documentoRepository = documentoRepository;
        this.comunidadRepository = comunidadRepository;
    }

    

    @Value("${app.upload.dir}")
    private String uploadDir;

    private void validarPdf(MultipartFile file){
        if(file.isEmpty()){
            throw new ArchivoInvalidoException("El archivo esta vacío");
        }
        byte[] cabecera = new byte[5];
        try (InputStream is = file.getInputStream()){
            is.read(cabecera);
        }catch(IOException e){
            throw new ArchivoInvalidoException("Error al procesar el PDF");
        }
        String firma = new String(cabecera, StandardCharsets.US_ASCII);
        if(!firma.equals("%PDF-")){
            throw new ArchivoInvalidoException("El fichero no es un PDF valido");
        }
    }

    private void archivoRollback(Path archivo){
        try{
            Files.deleteIfExists(archivo);
        }catch (IOException e){
            System.err.println("Atención: No se pudo borrar el archivo huérfano: " + archivo);
        }
    }

    public Documento crearDocumento(MultipartFile file, Documento documento, Usuario usuarioLogado){
        Path directorio = Paths.get(uploadDir);
        if(!Files.exists(directorio)){
            try{
                Files.createDirectories(directorio);
            }catch(IOException e){
                throw new SistemaArchivoException("Error al crear el directorio");
            }            
        } 
        
        validarPdf(file);
        
        String nombreOriginal = file.getOriginalFilename();
        int indexOf = nombreOriginal != null ? nombreOriginal.lastIndexOf(".") : -1;
        String extension = indexOf >= 0 ? nombreOriginal.substring(indexOf) : ".pdf";
        String nombreNuevo = UUID.randomUUID().toString() + extension;

        Path ruta = directorio.resolve(nombreNuevo);
        try{
            Files.copy(file.getInputStream(), ruta);
        }catch(IOException e){
            throw new SistemaArchivoException("Error al crear el archivo");
        }        

        documento.setDocumento(nombreNuevo);
        documento.setComunidad(comunidadRepository.getReferenceById(usuarioLogado.getComunidad().getId()));
        
        try{
            return documentoRepository.save(documento);
        }catch (Exception e){
            archivoRollback(ruta);
            throw e;
        }
        
    }

    public List<Documento> listarPorComunidad(Long idComunidadManual, Usuario usuarioLogado){

        Long idFinal;

        if(usuarioLogado.getRol() == Rol.SUPER_ADMIN && idComunidadManual != null){
            idFinal = idComunidadManual;
        }else{
            idFinal = usuarioLogado.getComunidad().getId();
        }
        return documentoRepository.findByComunidadId(idFinal);
    }


    public Documento actualizarCampos(Long id, ModifDocumento modifDocumento, Usuario usuarioLogado){
        Documento actual = documentoRepository.findByIdAndComunidadId(id, usuarioLogado.getComunidad().getId()).orElseThrow(() -> new NoSuchElementException("Documento no encontrado"));
        
        if(modifDocumento.getNombre() != null){
            actual.setNombre(modifDocumento.getNombre());
        }
        if(modifDocumento.getDescripcion() != null){
            actual.setDescripcion(modifDocumento.getDescripcion());
        }

        return documentoRepository.save(actual);
    }


    public void borrarDocumento(Long id, Usuario usuarioLogado){
        Documento doc;

        if(usuarioLogado.getRol() == Rol.SUPER_ADMIN){
            doc = documentoRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Documento no encontrado"));
        }else{
            doc = documentoRepository.findByIdAndComunidadId(id, usuarioLogado.getComunidad().getId()).orElseThrow(() -> new NoSuchElementException("Documento no encontrado"));
        }

        try{
            Path ruta = Paths.get(uploadDir).resolve(doc.getDocumento());
            Files.deleteIfExists(ruta);
        }catch(IOException e){
            throw new SistemaArchivoException("Error del servidor al eliminar el archivo");
        }

        documentoRepository.delete(doc);
    }
    
}
