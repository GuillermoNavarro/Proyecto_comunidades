package com.comunidad.comunidad_backend.entity;

import java.math.BigDecimal;
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
    private LocalDate fechaEmision;

    @Column(name="importe_total")
    private BigDecimal importe;

    @Column(name="tipo")
    private String tipo;

    //@Column(name="id_comunidad")
    //private Long idComunidad;
    /**
     * Relación Many-to-One con la entidad Comunidad
     * JoinColumn especifica la columna que actúa como clave foránea     * 
     */
    @ManyToOne(optional = false) 
    @JoinColumn(name="id_comunidad", nullable = false)
    private Comunidad comunidad;
    
   
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

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Comunidad getComunidad() {
        return comunidad;
    }

    public void setComunidad(Comunidad comunidad) {
        this.comunidad = comunidad;
    }


}
