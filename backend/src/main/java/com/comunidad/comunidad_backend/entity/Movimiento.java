package com.comunidad.comunidad_backend.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.comunidad.comunidad_backend.enus.TipoMovimiento;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(name = "movimientos")
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_movimiento")
    private Long id;

    @Column(name="nombre")
    private String nombre;

    /**
     * Relación Many-to-One con la entidad Comunidad
     */
    @ManyToOne(optional = false)
    @JoinColumn(name="id_comunidad", nullable = false)
    private Comunidad comunidad;

    /**
     * Relación Many-to-One con la entidad Usuario
     * Puede ser nulo en caso de movimientos de gastos
     * Ignoramos ciertas propiedades para evitar ciclos de serialización
     */
    @ManyToOne(optional = true)
    @JoinColumn(name="id_usuario", nullable = true)
    @JsonIgnoreProperties({"password", "movimientos", "comunidad"})
    private Usuario usuario;

    @Column(name="fecha")
    private LocalDate fecha;

    @Column(name="importe")
    private BigDecimal importe;

    @Enumerated(EnumType.STRING)
    @Column(name="tipo")
    private TipoMovimiento tipo;

    //@Column(name="id_cuota")
    //private Long idCuota;
    @ManyToOne(optional = true)
    @JoinColumn(name="id_cuota", nullable = true)
    @JsonIgnoreProperties("comunidad")
    private Cuota cuota;


    public Movimiento(){}

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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public TipoMovimiento getTipo() {
        return tipo;
    }

    public void setTipo(TipoMovimiento tipo) {
        this.tipo = tipo;
    }

    public Cuota getCuota() {
        return cuota;
    }

    public void setCuota(Cuota cuota) {
        this.cuota = cuota;
    }

    
    
}
