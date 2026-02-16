package com.comunidad.comunidad_backend.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;


@Entity
@Table(name = "publicaciones")
public class Publicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_publicacion")
    private Long id;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name="imagen")
    private String imagen;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_comunidad", nullable = false)
    private Comunidad comunidad;

    @Column(name = "fecha_creacion", insertable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "estado")
    private boolean estado = true;

    @Column(name = "moderado")
    private boolean moderado = false;
    

    public Publicacion(){ 
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getTipo() {
        return tipo;
    }


    public void setTipo(String tipo) {
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


    public String getImagen() {
        return imagen;
    }


    public void setImagen(String imagen) {
        this.imagen = imagen;
    }


    public Usuario getUsuario() {
        return usuario;
    }


    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


    public Comunidad getComunidad() {
        return comunidad;
    }


    public void setComunidad(Comunidad comunidad) {
        this.comunidad = comunidad;
    }


    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }


    public boolean isEstado() {
        return estado;
    }


    public void setEstado(boolean estado) {
        this.estado = estado;
    }


    public boolean isModerado() {
        return moderado;
    }


    public void setModerado(boolean moderado) {
        this.moderado = moderado;
    }

    
}
