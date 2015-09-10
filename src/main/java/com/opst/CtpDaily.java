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
@Table(name = "ctp_daily")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CtpDaily.findAll", query = "SELECT c FROM CtpDaily c"),
    @NamedQuery(name = "CtpDaily.findById", query = "SELECT c FROM CtpDaily c WHERE c.id = :id"),
    @NamedQuery(name = "CtpDaily.findByDate", query = "SELECT c FROM CtpDaily c WHERE c.date = :date"),
    @NamedQuery(name = "CtpDaily.findByA", query = "SELECT c FROM CtpDaily c WHERE c.a = :a"),
    @NamedQuery(name = "CtpDaily.findByA1", query = "SELECT c FROM CtpDaily c WHERE c.a1 = :a1"),
    @NamedQuery(name = "CtpDaily.findByA2", query = "SELECT c FROM CtpDaily c WHERE c.a2 = :a2"),
    @NamedQuery(name = "CtpDaily.findByA3", query = "SELECT c FROM CtpDaily c WHERE c.a3 = :a3"),
    @NamedQuery(name = "CtpDaily.findByA4", query = "SELECT c FROM CtpDaily c WHERE c.a4 = :a4"),
    @NamedQuery(name = "CtpDaily.findByA5", query = "SELECT c FROM CtpDaily c WHERE c.a5 = :a5"),
    @NamedQuery(name = "CtpDaily.findByA6", query = "SELECT c FROM CtpDaily c WHERE c.a6 = :a6"),
    @NamedQuery(name = "CtpDaily.findByA7", query = "SELECT c FROM CtpDaily c WHERE c.a7 = :a7"),
    @NamedQuery(name = "CtpDaily.findByA8", query = "SELECT c FROM CtpDaily c WHERE c.a8 = :a8"),
    @NamedQuery(name = "CtpDaily.findByA9", query = "SELECT c FROM CtpDaily c WHERE c.a9 = :a9"),
    @NamedQuery(name = "CtpDaily.findByA10", query = "SELECT c FROM CtpDaily c WHERE c.a10 = :a10"),
    @NamedQuery(name = "CtpDaily.findByA11", query = "SELECT c FROM CtpDaily c WHERE c.a11 = :a11"),
    @NamedQuery(name = "CtpDaily.findByA12", query = "SELECT c FROM CtpDaily c WHERE c.a12 = :a12"),
    @NamedQuery(name = "CtpDaily.findByA13", query = "SELECT c FROM CtpDaily c WHERE c.a13 = :a13"),
    @NamedQuery(name = "CtpDaily.findByA14", query = "SELECT c FROM CtpDaily c WHERE c.a14 = :a14"),
    @NamedQuery(name = "CtpDaily.findByA15", query = "SELECT c FROM CtpDaily c WHERE c.a15 = :a15"),
    @NamedQuery(name = "CtpDaily.findByA16", query = "SELECT c FROM CtpDaily c WHERE c.a16 = :a16"),
    @NamedQuery(name = "CtpDaily.findByA17", query = "SELECT c FROM CtpDaily c WHERE c.a17 = :a17"),
    @NamedQuery(name = "CtpDaily.findByA18", query = "SELECT c FROM CtpDaily c WHERE c.a18 = :a18"),
    @NamedQuery(name = "CtpDaily.findByA19", query = "SELECT c FROM CtpDaily c WHERE c.a19 = :a19"),
    @NamedQuery(name = "CtpDaily.findByA20", query = "SELECT c FROM CtpDaily c WHERE c.a20 = :a20"),
    @NamedQuery(name = "CtpDaily.findByA21", query = "SELECT c FROM CtpDaily c WHERE c.a21 = :a21"),
    @NamedQuery(name = "CtpDaily.findByA22", query = "SELECT c FROM CtpDaily c WHERE c.a22 = :a22"),
    @NamedQuery(name = "CtpDaily.findByA23", query = "SELECT c FROM CtpDaily c WHERE c.a23 = :a23"),
    @NamedQuery(name = "CtpDaily.findByAvg", query = "SELECT c FROM CtpDaily c WHERE c.avg = :avg"),
    @NamedQuery(name = "CtpDaily.findByLastUpdated", query = "SELECT c FROM CtpDaily c WHERE c.lastUpdated = :lastUpdated"),
    @NamedQuery(name = "CtpDaily.findByUpdatedBy", query = "SELECT c FROM CtpDaily c WHERE c.updatedBy = :updatedBy")})
public class CtpDaily implements Serializable {
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
    @Column(name = "1")
    private double a;
    @Basic(optional = false)
    @Column(name = "2")
    private double a1;
    @Basic(optional = false)
    @Column(name = "3")
    private double a2;
    @Basic(optional = false)
    @Column(name = "4")
    private double a3;
    @Basic(optional = false)
    @Column(name = "5")
    private double a4;
    @Basic(optional = false)
    @Column(name = "6")
    private double a5;
    @Basic(optional = false)
    @Column(name = "7")
    private double a6;
    @Basic(optional = false)
    @Column(name = "8")
    private double a7;
    @Basic(optional = false)
    @Column(name = "9")
    private double a8;
    @Basic(optional = false)
    @Column(name = "10")
    private double a9;
    @Basic(optional = false)
    @Column(name = "11")
    private double a10;
    @Basic(optional = false)
    @Column(name = "12")
    private double a11;
    @Basic(optional = false)
    @Column(name = "13")
    private double a12;
    @Basic(optional = false)
    @Column(name = "14")
    private double a13;
    @Basic(optional = false)
    @Column(name = "15")
    private double a14;
    @Basic(optional = false)
    @Column(name = "16")
    private double a15;
    @Basic(optional = false)
    @Column(name = "17")
    private double a16;
    @Basic(optional = false)
    @Column(name = "18")
    private double a17;
    @Basic(optional = false)
    @Column(name = "19")
    private double a18;
    @Basic(optional = false)
    @Column(name = "20")
    private double a19;
    @Basic(optional = false)
    @Column(name = "21")
    private double a20;
    @Basic(optional = false)
    @Column(name = "22")
    private double a21;
    @Basic(optional = false)
    @Column(name = "23")
    private double a22;
    @Basic(optional = false)
    @Column(name = "24")
    private double a23;
    @Basic(optional = false)
    @Column(name = "avg")
    private double avg;
    @Basic(optional = false)
    @Column(name = "last_updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated;
    @Basic(optional = false)
    @Column(name = "updated_by")
    private int updatedBy;

    public CtpDaily() {
    }

    public CtpDaily(Integer id) {
        this.id = id;
    }

    public CtpDaily(Integer id, Date date, double a, double a1, double a2, double a3, double a4, double a5, double a6, double a7, double a8, double a9, double a10, double a11, double a12, double a13, double a14, double a15, double a16, double a17, double a18, double a19, double a20, double a21, double a22, double a23, double avg, Date lastUpdated, int updatedBy) {
        this.id = id;
        this.date = date;
        this.a = a;
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
        this.a4 = a4;
        this.a5 = a5;
        this.a6 = a6;
        this.a7 = a7;
        this.a8 = a8;
        this.a9 = a9;
        this.a10 = a10;
        this.a11 = a11;
        this.a12 = a12;
        this.a13 = a13;
        this.a14 = a14;
        this.a15 = a15;
        this.a16 = a16;
        this.a17 = a17;
        this.a18 = a18;
        this.a19 = a19;
        this.a20 = a20;
        this.a21 = a21;
        this.a22 = a22;
        this.a23 = a23;
        this.avg = avg;
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

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getA1() {
        return a1;
    }

    public void setA1(double a1) {
        this.a1 = a1;
    }

    public double getA2() {
        return a2;
    }

    public void setA2(double a2) {
        this.a2 = a2;
    }

    public double getA3() {
        return a3;
    }

    public void setA3(double a3) {
        this.a3 = a3;
    }

    public double getA4() {
        return a4;
    }

    public void setA4(double a4) {
        this.a4 = a4;
    }

    public double getA5() {
        return a5;
    }

    public void setA5(double a5) {
        this.a5 = a5;
    }

    public double getA6() {
        return a6;
    }

    public void setA6(double a6) {
        this.a6 = a6;
    }

    public double getA7() {
        return a7;
    }

    public void setA7(double a7) {
        this.a7 = a7;
    }

    public double getA8() {
        return a8;
    }

    public void setA8(double a8) {
        this.a8 = a8;
    }

    public double getA9() {
        return a9;
    }

    public void setA9(double a9) {
        this.a9 = a9;
    }

    public double getA10() {
        return a10;
    }

    public void setA10(double a10) {
        this.a10 = a10;
    }

    public double getA11() {
        return a11;
    }

    public void setA11(double a11) {
        this.a11 = a11;
    }

    public double getA12() {
        return a12;
    }

    public void setA12(double a12) {
        this.a12 = a12;
    }

    public double getA13() {
        return a13;
    }

    public void setA13(double a13) {
        this.a13 = a13;
    }

    public double getA14() {
        return a14;
    }

    public void setA14(double a14) {
        this.a14 = a14;
    }

    public double getA15() {
        return a15;
    }

    public void setA15(double a15) {
        this.a15 = a15;
    }

    public double getA16() {
        return a16;
    }

    public void setA16(double a16) {
        this.a16 = a16;
    }

    public double getA17() {
        return a17;
    }

    public void setA17(double a17) {
        this.a17 = a17;
    }

    public double getA18() {
        return a18;
    }

    public void setA18(double a18) {
        this.a18 = a18;
    }

    public double getA19() {
        return a19;
    }

    public void setA19(double a19) {
        this.a19 = a19;
    }

    public double getA20() {
        return a20;
    }

    public void setA20(double a20) {
        this.a20 = a20;
    }

    public double getA21() {
        return a21;
    }

    public void setA21(double a21) {
        this.a21 = a21;
    }

    public double getA22() {
        return a22;
    }

    public void setA22(double a22) {
        this.a22 = a22;
    }

    public double getA23() {
        return a23;
    }

    public void setA23(double a23) {
        this.a23 = a23;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
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
        if (!(object instanceof CtpDaily)) {
            return false;
        }
        CtpDaily other = (CtpDaily) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.opst.CtpDaily[ id=" + id + " ]";
    }
    
}
