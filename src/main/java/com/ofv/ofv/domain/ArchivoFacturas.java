package com.ofv.ofv.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;

/**
 * A ArchivoFacturas.
 */
@Entity
@Table(name = "archivo_facturas")
public class ArchivoFacturas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Lob
    @Column(name = "archivo_blob")
    private byte[] archivoBlob;

    @Column(name = "archivo_blob_content_type")
    private String archivoBlobContentType;

    @Lob
    @Column(name = "archivo_csv")
    private byte[] archivoCsv;

    @Column(name = "archivo_csv_content_type")
    private String archivoCsvContentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public ArchivoFacturas nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public ArchivoFacturas fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public byte[] getArchivoBlob() {
        return archivoBlob;
    }

    public ArchivoFacturas archivoBlob(byte[] archivoBlob) {
        this.archivoBlob = archivoBlob;
        return this;
    }

    public void setArchivoBlob(byte[] archivoBlob) {
        this.archivoBlob = archivoBlob;
    }

    public String getArchivoBlobContentType() {
        return archivoBlobContentType;
    }

    public ArchivoFacturas archivoBlobContentType(String archivoBlobContentType) {
        this.archivoBlobContentType = archivoBlobContentType;
        return this;
    }

    public void setArchivoBlobContentType(String archivoBlobContentType) {
        this.archivoBlobContentType = archivoBlobContentType;
    }

    public byte[] getArchivoCsv() {
        return archivoCsv;
    }

    public ArchivoFacturas archivoCsv(byte[] archivoCsv) {
        this.archivoCsv = archivoCsv;
        return this;
    }

    public void setArchivoCsv(byte[] archivoCsv) {
        this.archivoCsv = archivoCsv;
    }

    public String getArchivoCsvContentType() {
        return archivoCsvContentType;
    }

    public ArchivoFacturas archivoCsvContentType(String archivoCsvContentType) {
        this.archivoCsvContentType = archivoCsvContentType;
        return this;
    }

    public void setArchivoCsvContentType(String archivoCsvContentType) {
        this.archivoCsvContentType = archivoCsvContentType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArchivoFacturas)) {
            return false;
        }
        return id != null && id.equals(((ArchivoFacturas) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ArchivoFacturas{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", archivoBlob='" + getArchivoBlob() + "'" +
            ", archivoBlobContentType='" + getArchivoBlobContentType() + "'" +
            ", archivoCsv='" + getArchivoCsv() + "'" +
            ", archivoCsvContentType='" + getArchivoCsvContentType() + "'" +
            "}";
    }
}
