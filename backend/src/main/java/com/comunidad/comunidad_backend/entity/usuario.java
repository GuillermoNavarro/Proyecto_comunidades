package com.comunidad.cominidad_backend.entity;
import javax.annotation.processing.Generated;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class usuario {

    @Id
    @GeneratedValue(strategy = GeneratedType.IDENTITY)
    @Column(name = "ID_Usuario")
    private long id;
    
}



    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario") // En MySQL se llama id_usuario
    private Long id;             // En Java lo llamamos id (más corto)

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "email")
    private String email;

    @Column(name = "password") // O "contraseña" si lo pusiste así en la BBDD
    private String password;

    @Column(name = "rol") // Ej: "vecino", "admin", "presidente"
    private String rol;

    // --- CONSTRUCTOR VACÍO (Obligatorio para Spring) ---
    public Usuario() {
    }

    // --- GETTERS Y SETTERS ---
    // (¡No los escribas a mano! Mira el truco abajo)
}