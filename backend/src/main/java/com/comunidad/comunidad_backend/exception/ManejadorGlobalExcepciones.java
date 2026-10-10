package com.comunidad.comunidad_backend.exception;

import java.util.NoSuchElementException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice 
public class ManejadorGlobalExcepciones {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> manejarNoEncontrado(NoSuchElementException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(RecursoDuplicadoException.class)
    public ResponseEntity<String> manejarDuplicado(RecursoDuplicadoException e){
        return ResponseEntity.status(409).body(e.getMessage());
    }

    @ExceptionHandler(CredencialesInvalidasException.class)
    public ResponseEntity<String> manejarCredenciaels(CredencialesInvalidasException e){
        return ResponseEntity.status(401).body(e.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> manejarIntegridad(DataIntegrityViolationException e){
        return ResponseEntity.status(409).body("No se pudo completar la operacion: el dato ya existe o viola una restricción.");
    }
    
}
