package com.comunidad.comunidad_backend.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.comunidad.comunidad_backend.dto.ModifDocumento;
import com.comunidad.comunidad_backend.entity.Documento;
import com.comunidad.comunidad_backend.entity.Usuario;
import com.comunidad.comunidad_backend.service.DocumentoService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;



@RestController
@RequestMapping("/api/documentos")

public class DocumentoController {

    private final DocumentoService documentoService;

    public DocumentoController(
        DocumentoService documentoService
    ){
        this.documentoService = documentoService;
    }


    @PostMapping(consumes = {"multipart/form-data"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity <?> createDocumento(@ModelAttribute Documento documento, @RequestParam("archivo") MultipartFile file, @AuthenticationPrincipal Usuario usuarioLogado){
       
        Documento nuevo = documentoService.crearDocumento(file, documento, usuarioLogado);
        return ResponseEntity.status(201).body(nuevo);

    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> getDocumentos( @RequestParam(value = "idComunidadManual", required = false)Long idComunidadManual, @AuthenticationPrincipal Usuario usuarioLogado){
        
        List<Documento> lista = documentoService.listarPorComunidad(idComunidadManual, usuarioLogado);
        return ResponseEntity.status(200).body(lista);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> modificarDocumento(@PathVariable Long id, @RequestBody ModifDocumento modifDocumento, @AuthenticationPrincipal Usuario usuarioLogado){
   
        Documento modificado = documentoService.actualizarCampos(id, modifDocumento, usuarioLogado);
        return ResponseEntity.status(201).body(modificado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> borrarDocumento(@PathVariable Long id, @AuthenticationPrincipal Usuario usuarioLogado){
        documentoService.borrarDocumento(id, usuarioLogado);
        return ResponseEntity.status(200).body("Documento eliminado correctamente");
    }
}
