package com.comunidad.comunidad_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

/**
 * Entidad que representa a un usuario en el sistema.
 * Esta clase está mapeada a la tabla "usuarios" en la base de datos.
 * Se ha implementado un sistema de borrado lógico mediante el campo "estado".
 * @author Guillermo Navarro
 */
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @Column(name = "dni")
    private String dni;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "puerta")
    private String puerta;

    @Column(name = "telefono")
    private String telefono;

    /**
     * La contraseña del usuario encriptada. (actualmente en texto plano, para pruebas iniciales)
     * Este campo está anotado con @JsonIgnore para evitar que se exponga en las respuestas JSON.
    */
    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Column(name = "rol")
    private String rol;

    @Column(name = "id_comunidad")
    private Long idComunidad;

    @Column(name = "email")
    private String email;

    @Column(name = "coeficiente")
    private Double coeficiente;  
    
    /**
     * indica si el usuario está activo o ha sido "borrado" lógicamente.
     * true = activo, false = inactivo (borrado lógico).
     * Por defecto, el valor es true.
    */
    @Column(name = "estado")
    private boolean estado = true;

    public Usuario(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getPuerta() {
        return puerta;
    }

    public void setPuerta(String puerta) {
        this.puerta = puerta;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Long getIdComunidad() {
        return idComunidad;
    }

    public void setIdComunidad(Long idComunidad) {
        this.idComunidad = idComunidad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getCoeficiente() {
        return coeficiente;
    }

    public void setCoeficiente(Double coeficiente) {
        this.coeficiente = coeficiente;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    
    }



    
