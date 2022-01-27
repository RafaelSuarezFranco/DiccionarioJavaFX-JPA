/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ies.javafxdiccionario2.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "palabra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Palabra.findAll", query = "SELECT p FROM Palabra p"),
    @NamedQuery(name = "Palabra.findById", query = "SELECT p FROM Palabra p WHERE p.id = :id"),
    @NamedQuery(name = "Palabra.findByPalabra", query = "SELECT p FROM Palabra p WHERE p.palabra = :palabra"),
    @NamedQuery(name = "Palabra.findByBaja", query = "SELECT p FROM Palabra p WHERE p.baja = :baja"),
    @NamedQuery(name = "Palabra.findByDescripcion", query = "SELECT p FROM Palabra p WHERE p.descripcion = :descripcion")})
public class Palabra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "PALABRA")
    private String palabra;
    @Basic(optional = false)
    @Column(name = "BAJA")
    private int baja;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;

    public Palabra() {
    }

    public Palabra(Integer id) {
        this.id = id;
    }

    public Palabra(Integer id, String palabra, int baja, String descripcion) {
        this.id = id;
        this.palabra = palabra;
        this.baja = baja;
        this.descripcion = descripcion;
    }
    
    public Palabra(String palabra, int baja, String descripcion) {
        this.palabra = palabra;
        this.baja = baja;
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public int getBaja() {
        return baja;
    }

    public void setBaja(int baja) {
        this.baja = baja;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Palabra)) {
            return false;
        }
        Palabra other = (Palabra) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ies.javafxdiccionario2.model.Palabra[ id=" + id + " ]";
    }
    
}
