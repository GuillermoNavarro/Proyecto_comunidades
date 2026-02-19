package com.comunidad.comunidad_backend.entity;

import com.comunidad.comunidad_backend.enus.Rol;
//import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;

/**
 * Entidad que representa a un usuario en el sistema.
 * Esta clase está mapeada a la tabla "usuarios" en la base de datos.
 * Se ha implementado un sistema de borrado lógico mediante el campo "estado".
 * 
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
     * La contraseña del usuario encriptada. (actualmente en texto plano, para
     * pruebas iniciales)
     * Utiliza @JsonProperty con Access.WRITE_ONLY para permitir que se envie en    
     * registros(POST), pero no se incluya en las respuestas JSON (GET).
     */
    @Column(name = "password")
    //@JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name="cambiar_pass")
    private boolean cambiarPass = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol")
    private Rol rol = Rol.USER;

    /**
     * Relación Many-to-One con la entidad Comunidad.
     * Un usuario pertenece a una única comunidad.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_comunidad", nullable = false) 
    private Comunidad comunidad;   

    @Column(name = "email")
    private String email;

    @Column(name = "coeficiente")
    private Double coeficiente;

    /**
     * indica si el usuario está activo o ha sido "borrado" lógicamente.
     * true = activo, false = inactivo (borrado lógico).
     * Por defecto, el valor es true.
     */
    @Column(name = "estado", nullable = false)
    private Boolean estado = true;

    public Usuario() {
    }

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

    public boolean getCambiarPass(){
        return cambiarPass;
    }

    public void setCambiarPass(boolean cambiarPass){
        this.cambiarPass = cambiarPass;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Comunidad getComunidad() {
        return comunidad;
    }

    public void setComunidad(Comunidad comunidad) {
        this.comunidad = comunidad;
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

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

}
