package com.comunidad.comunidad_backend.dto;

public class ActivoComunidad {
    private Long id;
    private String nombre;
    private String codPostal;
    private Long usuarios;

    public ActivoComunidad(){}

    public ActivoComunidad(Long id, String nombre, String codPostal, Long usuarios){
        this.id = id;
        this.nombre = nombre;
        this.codPostal = codPostal;
        this.usuarios = usuarios;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    public Long getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Long usuarios) {
        this.usuarios = usuarios;
    }

    
    
    
}
