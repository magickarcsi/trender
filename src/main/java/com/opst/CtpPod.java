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
@Table(name = "ctp_pod")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CtpPod.findAll", query = "SELECT c FROM CtpPod c"),
    @NamedQuery(name = "CtpPod.findById", query = "SELECT c FROM CtpPod c WHERE c.id = :id"),
    @NamedQuery(name = "CtpPod.findByDate", query = "SELECT c FROM CtpPod c WHERE c.date = :date"),
    @NamedQuery(name = "CtpPod.findByName", query = "SELECT c FROM CtpPod c WHERE c.name = :name"),
    @NamedQuery(name = "CtpPod.findByDaypart", query = "SELECT c FROM CtpPod c WHERE c.daypart = :daypart"),
    @NamedQuery(name = "CtpPod.findByCtp", query = "SELECT c FROM CtpPod c WHERE c.ctp = :ctp"),
    @NamedQuery(name = "CtpPod.findByLastUpdated", query = "SELECT c FROM CtpPod c WHERE c.lastUpdated = :lastUpdated"),
    @NamedQuery(name = "CtpPod.findByUpdatedBy", query = "SELECT c FROM CtpPod c WHERE c.updatedBy = :updatedBy")})
public class CtpPod implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @Column(name = "name")
    private int name;
    @Basic(optional = false)
    @Column(name = "daypart")
    private int daypart;
    @Basic(optional = false)
    @Column(name = "ctp")
    private double ctp;
    @Basic(optional = false)
    @Column(name = "last_updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated;
    @Basic(optional = false)
    @Column(name = "updated_by")
    private int updatedBy;

    public CtpPod() {
    }

    public CtpPod(Integer id) {
        this.id = id;
    }

    public CtpPod(Integer id, Date date, int name, int daypart, double ctp, Date lastUpdated, int updatedBy) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.daypart = daypart;
        this.ctp = ctp;
        this.lastUpdated = lastUpdated;
        this.updatedBy = updatedBy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getDaypart() {
        return daypart;
    }

    public void setDaypart(int daypart) {
        this.daypart = daypart;
    }

    public double getCtp() {
        return ctp;
    }

    public void setCtp(double ctp) {
        this.ctp = ctp;
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
        if (!(object instanceof CtpPod)) {
            return false;
        }
        CtpPod other = (CtpPod) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.opst.CtpPod[ id=" + id + " ]";
    }
    
}
