package com.comunidad.comunidad_backend.dto;

import java.time.LocalDateTime;

import com.comunidad.comunidad_backend.enums.TipoPublicacion;

public class ListarPublicaciones {
    private Long id;
    private TipoPublicacion tipo;
    private String titulo;
    private String descripcion;
    private String documentoNombre;
    private String documentoArchivo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaFin;

    public ListarPublicaciones(){}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public TipoPublicacion getTipo() {
        return tipo;
    }
    public void setTipo(TipoPublicacion tipo) {
        this.tipo = tipo;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getDocumentoNombre() {
        return documentoNombre;
    }
    public void setDocumentoNombre(String documentoNombre) {
        this.documentoNombre = documentoNombre;
    }
    public String getDocumentoArchivo() {
        return documentoArchivo;
    }
    public void setDocumentoArchivo(String documentoArchivo) {
        this.documentoArchivo = documentoArchivo;
    }
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    public LocalDateTime getFechaFin() {
        return fechaFin;
    }
    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }
}

