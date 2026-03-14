package com.comunidad.comunidad_backend.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.*;

@Entity
@Table(name = "documentos")
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_documento")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_comunidad", nullable = false)
    private Comunidad comunidad;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha", insertable = false, updatable = true)
    private LocalDateTime fecha;
    
    @Column(name = "documento")
    private String documento;


    public Documento(){
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public Comunidad getComunidad() {
        return comunidad;
    }


    public void setComunidad(Comunidad comunidad) {
        this.comunidad = comunidad;
    }


    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getDescripcion() {
        return descripcion;
    }


    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public LocalDateTime getFecha() {
        return fecha;
    }


    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }


    public String getDocumento() {
        return documento;
    }


    public void setDocumento(String documento) {
        this.documento = documento;
    }

    

}
