package com.ofv.ofv.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Suministro.
 */
@Entity
@Table(name = "suministro")
public class Suministro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "suministro")
    private String suministro;

    @Column(name = "servicio")
    private String servicio;

    @Column(name = "inmueble")
    private String inmueble;

    @Column(name = "usuario")
    private String usuario;

    @Column(name = "dni")
    private String dni;

    @Column(name = "tarifa")
    private String tarifa;

    @ManyToMany
    @JoinTable(name = "suministro_user",
               joinColumns = @JoinColumn(name = "suministro_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<User> users = new HashSet<>();

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

    public Suministro suministro(String suministro) {
        this.suministro = suministro;
        return this;
    }

    public void setSuministro(String suministro) {
        this.suministro = suministro;
    }

    public String getServicio() {
        return servicio;
    }

    public Suministro servicio(String servicio) {
        this.servicio = servicio;
        return this;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getInmueble() {
        return inmueble;
    }

    public Suministro inmueble(String inmueble) {
        this.inmueble = inmueble;
        return this;
    }

    public void setInmueble(String inmueble) {
        this.inmueble = inmueble;
    }

    public String getUsuario() {
        return usuario;
    }

    public Suministro usuario(String usuario) {
        this.usuario = usuario;
        return this;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getDni() {
        return dni;
    }

    public Suministro dni(String dni) {
        this.dni = dni;
        return this;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTarifa() {
        return tarifa;
    }

    public Suministro tarifa(String tarifa) {
        this.tarifa = tarifa;
        return this;
    }

    public void setTarifa(String tarifa) {
        this.tarifa = tarifa;
    }

    public Set<User> getUsers() {
        return users;
    }

    public Suministro users(Set<User> users) {
        this.users = users;
        return this;
    }

    public Suministro addUser(User user) {
        this.users.add(user);
        return this;
    }

    public Suministro removeUser(User user) {
        this.users.remove(user);
        return this;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Suministro)) {
            return false;
        }
        return id != null && id.equals(((Suministro) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Suministro{" +
            "id=" + getId() +
            ", suministro='" + getSuministro() + "'" +
            ", servicio='" + getServicio() + "'" +
            ", inmueble='" + getInmueble() + "'" +
            ", usuario='" + getUsuario() + "'" +
            ", dni='" + getDni() + "'" +
            ", tarifa='" + getTarifa() + "'" +
            "}";
    }
}
