package com.ofv.ofv.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A Factura.
 */
@Entity
@Table(name = "factura")
public class Factura implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "suministro")
    private String suministro;

    @Column(name = "usuario")
    private String usuario;

    @Column(name = "inmueble")
    private String inmueble;

    @Column(name = "periodo")
    private String periodo;

    @Column(name = "numero")
    private String numero;

    @Column(name = "vencimiento_1")
    private LocalDate vencimiento1;

    @Column(name = "vencimiento_2")
    private LocalDate vencimiento2;

    @Column(name = "importe_1", precision = 21, scale = 2)
    private BigDecimal importe1;

    @Column(name = "importe_2", precision = 21, scale = 2)
    private BigDecimal importe2;

    @Column(name = "servicio")
    private String servicio;

    @Column(name = "tarifa")
    private String tarifa;

    @Column(name = "archivopdf")
    private String archivopdf;

    @Column(name = "estado")
    private String estado;

    @Column(name = "dni")
    private String dni;

    @Column(name = "socio")
    private String socio;

    @ManyToOne
    @JsonIgnoreProperties("facturas")
    private ArchivoFacturas archivoFacturas;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSuministro() {
        return suministro;
    }

    public Factura suministro(String suministro) {
        this.suministro = suministro;
        return this;
    }

    public void setSuministro(String suministro) {
        this.suministro = suministro;
    }

    public String getUsuario() {
        return usuario;
    }

    public Factura usuario(String usuario) {
        this.usuario = usuario;
        return this;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getInmueble() {
        return inmueble;
    }

    public Factura inmueble(String inmueble) {
        this.inmueble = inmueble;
        return this;
    }

    public void setInmueble(String inmueble) {
        this.inmueble = inmueble;
    }

    public String getPeriodo() {
        return periodo;
    }

    public Factura periodo(String periodo) {
        this.periodo = periodo;
        return this;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getNumero() {
        return numero;
    }

    public Factura numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDate getVencimiento1() {
        return vencimiento1;
    }

    public Factura vencimiento1(LocalDate vencimiento1) {
        this.vencimiento1 = vencimiento1;
        return this;
    }

    public void setVencimiento1(LocalDate vencimiento1) {
        this.vencimiento1 = vencimiento1;
    }

    public LocalDate getVencimiento2() {
        return vencimiento2;
    }

    public Factura vencimiento2(LocalDate vencimiento2) {
        this.vencimiento2 = vencimiento2;
        return this;
    }

    public void setVencimiento2(LocalDate vencimiento2) {
        this.vencimiento2 = vencimiento2;
    }

    public BigDecimal getImporte1() {
        return importe1;
    }

    public Factura importe1(BigDecimal importe1) {
        this.importe1 = importe1;
        return this;
    }

    public void setImporte1(BigDecimal importe1) {
        this.importe1 = importe1;
    }

    public BigDecimal getImporte2() {
        return importe2;
    }

    public Factura importe2(BigDecimal importe2) {
        this.importe2 = importe2;
        return this;
    }

    public void setImporte2(BigDecimal importe2) {
        this.importe2 = importe2;
    }

    public String getServicio() {
        return servicio;
    }

    public Factura servicio(String servicio) {
        this.servicio = servicio;
        return this;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getTarifa() {
        return tarifa;
    }

    public Factura tarifa(String tarifa) {
        this.tarifa = tarifa;
        return this;
    }

    public void setTarifa(String tarifa) {
        this.tarifa = tarifa;
    }

    public String getArchivopdf() {
        return archivopdf;
    }

    public Factura archivopdf(String archivopdf) {
        this.archivopdf = archivopdf;
        return this;
    }

    public void setArchivopdf(String archivopdf) {
        this.archivopdf = archivopdf;
    }

    public String getEstado() {
        return estado;
    }

    public Factura estado(String estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDni() {
        return dni;
    }

    public Factura dni(String dni) {
        this.dni = dni;
        return this;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getSocio() {
        return socio;
    }

    public Factura socio(String socio) {
        this.socio = socio;
        return this;
    }

    public void setSocio(String socio) {
        this.socio = socio;
    }

    public ArchivoFacturas getArchivoFacturas() {
        return archivoFacturas;
    }

    public Factura archivoFacturas(ArchivoFacturas archivoFacturas) {
        this.archivoFacturas = archivoFacturas;
        return this;
    }

    public void setArchivoFacturas(ArchivoFacturas archivoFacturas) {
        this.archivoFacturas = archivoFacturas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Factura)) {
            return false;
        }
        return id != null && id.equals(((Factura) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Factura{" +
            "id=" + getId() +
            ", suministro='" + getSuministro() + "'" +
            ", usuario='" + getUsuario() + "'" +
            ", inmueble='" + getInmueble() + "'" +
            ", periodo='" + getPeriodo() + "'" +
            ", numero='" + getNumero() + "'" +
            ", vencimiento1='" + getVencimiento1() + "'" +
            ", vencimiento2='" + getVencimiento2() + "'" +
            ", importe1=" + getImporte1() +
            ", importe2=" + getImporte2() +
            ", servicio='" + getServicio() + "'" +
            ", tarifa='" + getTarifa() + "'" +
            ", archivopdf='" + getArchivopdf() + "'" +
            ", estado='" + getEstado() + "'" +
            ", dni='" + getDni() + "'" +
            ", socio='" + getSocio() + "'" +
            "}";
    }
}
