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
@Table(name = "ctp_weekly_by_pod")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CtpWeeklyByPod.findAll", query = "SELECT c FROM CtpWeeklyByPod c"),
    @NamedQuery(name = "CtpWeeklyByPod.findById", query = "SELECT c FROM CtpWeeklyByPod c WHERE c.id = :id"),
    @NamedQuery(name = "CtpWeeklyByPod.findByDaypart", query = "SELECT c FROM CtpWeeklyByPod c WHERE c.daypart = :daypart"),
    @NamedQuery(name = "CtpWeeklyByPod.findByCtp", query = "SELECT c FROM CtpWeeklyByPod c WHERE c.ctp = :ctp"),
    @NamedQuery(name = "CtpWeeklyByPod.findByWeek", query = "SELECT c FROM CtpWeeklyByPod c WHERE c.week = :week"),
    @NamedQuery(name = "CtpWeeklyByPod.findByYear", query = "SELECT c FROM CtpWeeklyByPod c WHERE c.year = :year"),
    @NamedQuery(name = "CtpWeeklyByPod.findByDev", query = "SELECT c FROM CtpWeeklyByPod c WHERE c.dev = :dev"),
    @NamedQuery(name = "CtpWeeklyByPod.findByTarget", query = "SELECT c FROM CtpWeeklyByPod c WHERE c.target = :target"),
    @NamedQuery(name = "CtpWeeklyByPod.findByLastUpdated", query = "SELECT c FROM CtpWeeklyByPod c WHERE c.lastUpdated = :lastUpdated"),
    @NamedQuery(name = "CtpWeeklyByPod.findByUpdatedBy", query = "SELECT c FROM CtpWeeklyByPod c WHERE c.updatedBy = :updatedBy")})
public class CtpWeeklyByPod implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "daypart")
    private int daypart;
    @Basic(optional = false)
    @Column(name = "ctp")
    private double ctp;
    @Basic(optional = false)
    @Column(name = "week")
    private int week;
    @Basic(optional = false)
    @Column(name = "year")
    private int year;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "dev")
    private Double dev;
    @Column(name = "target")
    private Double target;
    @Basic(optional = false)
    @Column(name = "last_updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated;
    @Basic(optional = false)
    @Column(name = "updated_by")
    private int updatedBy;

    public CtpWeeklyByPod() {
    }

    public CtpWeeklyByPod(Integer id) {
        this.id = id;
    }

    public CtpWeeklyByPod(Integer id, int daypart, double ctp, int week, int year, Date lastUpdated, int updatedBy) {
        this.id = id;
        this.daypart = daypart;
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
        if (!(object instanceof CtpWeeklyByPod)) {
            return false;
        }
        CtpWeeklyByPod other = (CtpWeeklyByPod) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.opst.CtpWeeklyByPod[ id=" + id + " ]";
    }
    
}
