package com.comunidad.comunidad_backend.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.comunidad.comunidad_backend.enus.TipoCuota;

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

    @Column(name="fecha_vencimiento")
    private LocalDate fechaVencimiento;

    @Column(name="importe_total")
    private BigDecimal importe;

    @Enumerated(EnumType.STRING)
    @Column(name="tipo")
    private TipoCuota tipo;

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
    
    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public TipoCuota getTipo() {
        return tipo;
    }

    public void setTipo(TipoCuota tipo) {
        this.tipo = tipo;
    }

    public Comunidad getComunidad() {
        return comunidad;
    }

    public void setComunidad(Comunidad comunidad) {
        this.comunidad = comunidad;
    }


}
