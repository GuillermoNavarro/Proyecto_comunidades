package com.comunidad.comunidad_backend.exception;

public class EstadoInvalidoException extends RuntimeException {
    public EstadoInvalidoException(String mensaje){
        super(mensaje);
    }
}
