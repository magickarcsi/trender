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
@Table(name = "ctp_weekly_by_manager")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CtpWeeklyByManager.findAll", query = "SELECT c FROM CtpWeeklyByManager c"),
    @NamedQuery(name = "CtpWeeklyByManager.findById", query = "SELECT c FROM CtpWeeklyByManager c WHERE c.id = :id"),
    @NamedQuery(name = "CtpWeeklyByManager.findByName", query = "SELECT c FROM CtpWeeklyByManager c WHERE c.name = :name"),
    @NamedQuery(name = "CtpWeeklyByManager.findByCtp", query = "SELECT c FROM CtpWeeklyByManager c WHERE c.ctp = :ctp"),
    @NamedQuery(name = "CtpWeeklyByManager.findByDev", query = "SELECT c FROM CtpWeeklyByManager c WHERE c.dev = :dev"),
    @NamedQuery(name = "CtpWeeklyByManager.findByTarget", query = "SELECT c FROM CtpWeeklyByManager c WHERE c.target = :target"),
    @NamedQuery(name = "CtpWeeklyByManager.findByWeek", query = "SELECT c FROM CtpWeeklyByManager c WHERE c.week = :week"),
    @NamedQuery(name = "CtpWeeklyByManager.findByYear", query = "SELECT c FROM CtpWeeklyByManager c WHERE c.year = :year"),
    @NamedQuery(name = "CtpWeeklyByManager.findByLastUpdated", query = "SELECT c FROM CtpWeeklyByManager c WHERE c.lastUpdated = :lastUpdated"),
    @NamedQuery(name = "CtpWeeklyByManager.findByUpdatedBy", query = "SELECT c FROM CtpWeeklyByManager c WHERE c.updatedBy = :updatedBy")})
public class CtpWeeklyByManager implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private int name;
    @Basic(optional = false)
    @Column(name = "ctp")
    private double ctp;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "dev")
    private Double dev;
    @Column(name = "target")
    private Double target;
    @Basic(optional = false)
    @Column(name = "week")
    private int week;
    @Basic(optional = false)
    @Column(name = "year")
    private int year;
    @Basic(optional = false)
    @Column(name = "last_updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated;
    @Basic(optional = false)
    @Column(name = "updated_by")
    private int updatedBy;

    public CtpWeeklyByManager() {
    }

    public CtpWeeklyByManager(Integer id) {
        this.id = id;
    }

    public CtpWeeklyByManager(Integer id, int name, double ctp, int week, int year, Date lastUpdated, int updatedBy) {
        this.id = id;
        this.name = name;
        this.ctp = ctp;
        this.week = week;
        this.year = year;
        this.lastUpdated = lastUpdated;
        this.updatedBy = updatedBy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public double getCtp() {
        return ctp;
    }

    public void setCtp(double ctp) {
        this.ctp = ctp;
    }

    public Double getDev() {
        return dev;
    }

    public void setDev(Double dev) {
        this.dev = dev;
    }

    public Double getTarget() {
        return target;
    }

    public void setTarget(Double target) {
        this.target = target;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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
        if (!(object instanceof CtpWeeklyByManager)) {
            return false;
        }
        CtpWeeklyByManager other = (CtpWeeklyByManager) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.opst.CtpWeeklyByManager[ id=" + id + " ]";
    }
    
}
