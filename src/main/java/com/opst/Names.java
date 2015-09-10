/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opst;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author karci
 */
@Entity
@Table(name = "names")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Names.findAll", query = "SELECT n FROM Names n"),
    @NamedQuery(name = "Names.findById", query = "SELECT n FROM Names n WHERE n.id = :id"),
    @NamedQuery(name = "Names.findByStore", query = "SELECT n FROM Names n WHERE n.store = :store"),
    @NamedQuery(name = "Names.findBySurname", query = "SELECT n FROM Names n WHERE n.surname = :surname"),
    @NamedQuery(name = "Names.findByFirstname", query = "SELECT n FROM Names n WHERE n.firstname = :firstname"),
    @NamedQuery(name = "Names.findByRole", query = "SELECT n FROM Names n WHERE n.role = :role"),
    @NamedQuery(name = "Names.findByBonus", query = "SELECT n FROM Names n WHERE n.bonus = :bonus"),
    @NamedQuery(name = "Names.findByIsActive", query = "SELECT n FROM Names n WHERE n.isActive = :isActive"),
    @NamedQuery(name = "Names.findByLastUpdated", query = "SELECT n FROM Names n WHERE n.lastUpdated = :lastUpdated"),
    @NamedQuery(name = "Names.findByUpdatedBy", query = "SELECT n FROM Names n WHERE n.updatedBy = :updatedBy")})
public class Names implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "store")
    private int store;
    @Column(name = "surname")
    private String surname;
    @Column(name = "firstname")
    private String firstname;
    @Basic(optional = false)
    @Column(name = "role")
    private int role;
    @Column(name = "bonus")
    private Integer bonus;
    @Basic(optional = false)
    @Column(name = "is_active")
    private boolean isActive;
    @Basic(optional = false)
    @Column(name = "last_updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated;
    @Basic(optional = false)
    @Column(name = "updated_by")
    private int updatedBy;

    public Names() {
    }

    public Names(Integer id) {
        this.id = id;
    }

    public Names(Integer id, int store, int role, boolean isActive, Date lastUpdated, int updatedBy) {
        this.id = id;
        this.store = store;
        this.role = role;
        this.isActive = isActive;
        this.lastUpdated = lastUpdated;
        this.updatedBy = updatedBy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getStore() {
        return store;
    }

    public void setStore(int store) {
        this.store = store;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public Integer getBonus() {
        return bonus;
    }

    public void setBonus(Integer bonus) {
        this.bonus = bonus;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
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
        if (!(object instanceof Names)) {
            return false;
        }
        Names other = (Names) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.opst.Names[ id=" + id + " ]";
    }
    
}
