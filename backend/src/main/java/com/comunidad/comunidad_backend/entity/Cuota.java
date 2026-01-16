package com.comunidad.comunidad_backend.entity;

import java.time.LocalDate;
import jakarta.persistence.*;



@Entity
@Table(name = "cuotas")
public class Cuota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cuota")
    private Long id;

    @Column(name="nombre")
    private String nombre;

    @Column(name="fecha_emision")
    private LocalDate fchEmision;

    @Column(name="importe_total")
    private Double importe;

    @Column(name="tipo")
    private String tipo;

    @Column(name="id_comunidad")
    private Long idComunidad;
    
    public Cuota(){}

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

    public LocalDate getFchEmision() {
        return fchEmision;
    }

    public void setFchEmision(LocalDate fchEmision) {
        this.fchEmision = fchEmision;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getIdComunidad() {
        return idComunidad;
    }

    public void setIdComunidad(Long idComunidad) {
        this.idComunidad = idComunidad;
    }
}
