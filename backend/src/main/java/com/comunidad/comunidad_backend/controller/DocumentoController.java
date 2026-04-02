package com.comunidad.comunidad_backend.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.comunidad.comunidad_backend.dto.ModifDocumento;
import com.comunidad.comunidad_backend.entity.Documento;
import com.comunidad.comunidad_backend.service.DocumentoService;
import com.comunidad.comunidad_backend.security.JwtService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;



@RestController
@RequestMapping("/api/documentos")

public class DocumentoController {

    @Autowired
    private DocumentoService documentoService;

    @Autowired
    private JwtService jwtService;

    @PostMapping(consumes = {"multipart/form-data"})
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity <?> createDocumento(
        @ModelAttribute Documento documento, @RequestParam("archivo") MultipartFile file, @RequestParam(value = "idComunidadManual", required = false)Long idComunidadManual, HttpServletRequest request){
       try{
        String token = request.getHeader("Authorization").substring(7);
        String rol = jwtService.extraerRol(token);
        Long idComunidadFinal;

        if(rol.equals("ROLE_SUPER_ADMIN") && idComunidadManual != null){
            idComunidadFinal = idComunidadManual;
        }else{
            idComunidadFinal = jwtService.extraerIdComunidad(token);
        } 

        if(file.isEmpty() || !file.getContentType().equals("application/pdf")){
            throw new IllegalArgumentException("Solo se permiten PDF");
        }

        documentoService.crearDocumento(file, documento, idComunidadFinal);
        return ResponseEntity.ok("guardado");
       }catch (Exception err){
        return ResponseEntity.internalServerError().body("Error: " +err.getMessage());
       }
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> getDocumentos( @RequestParam(value = "idComunidadManual", required = false)Long idComunidadManual, HttpServletRequest request){
        
        String token = request.getHeader("Authorization").substring(7);
        String rol = jwtService.extraerRol(token);
        Long idComunidad = jwtService.extraerIdComunidad(token);

        List<Documento> lista = documentoService.listarPorComunidad(idComunidad, idComunidadManual, rol);
        
        return ResponseEntity.ok(lista);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> modificarDocumento(@PathVariable Long id, @RequestBody ModifDocumento modifDocumento){
        try{
            Documento modificado = documentoService.actualizarCampos(id,modifDocumento);
            return ResponseEntity.ok(modificado);
        }catch(Exception err){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> borrarDocumento(@PathVariable Long id){
        boolean eliminado = documentoService.borrarDocumento(id);

        if(eliminado){
            return ResponseEntity.ok("eliminado");
        }else{
            return ResponseEntity.notFound().build();
        }
    }



   
    
    
    
}
