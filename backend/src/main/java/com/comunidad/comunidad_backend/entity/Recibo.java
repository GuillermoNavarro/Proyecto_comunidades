package com.comunidad.comunidad_backend.entity;

import java.math.BigDecimal;

import com.comunidad.comunidad_backend.enus.EstadoRecibo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(name = "recibos")
public class Recibo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_recibo")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name="id_cuota", nullable = false)
    @JsonIgnoreProperties("comunidad")
    private Cuota cuota;

    @ManyToOne(optional = false)
    @JoinColumn(name="id_comunidad", nullable=false)
    private Comunidad comunidad;

    @ManyToOne(optional = false)
    @JoinColumn(name="id_usuario", nullable = false)
    @JsonIgnoreProperties({"password", "comunidad"})
    private Usuario usuario;

    @Column(name="importe")
    private BigDecimal importe;

    @Enumerated(EnumType.STRING)
    @Column(name="estado")
    private EstadoRecibo estadoRecibo;

    public Recibo() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cuota getCuota() {
        return cuota;
    }

    public void setCuota(Cuota cuota) {
        this.cuota = cuota;
    }

    public Comunidad getComunidad() {
        return comunidad;
    }

    public void setComunidad(Comunidad comunidad) {
        this.comunidad = comunidad;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public EstadoRecibo getEstadoRecibo() {
        return estadoRecibo;
    }

    public void setEstadoRecibo(EstadoRecibo estadoRecibo) {
        this.estadoRecibo = estadoRecibo;
    }
    
}
