/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opst;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="ctpbean")
@SessionScoped
public class CtpBean implements Serializable{
    
    private static final long serialVersionUID = 1L;

    /**
     * @return the ctpList
     */
    public ArrayList<Ctp> getCtpList() {
        return ctpList;
    }
    
    int place;
    String name;
    BigDecimal ctp;
    BigDecimal dev;
    int target;
    
    
    
    private ArrayList<Ctp> ctpList = 
		new ArrayList<Ctp>(Arrays.asList(
        new Ctp(1,"Marton",new BigDecimal("32.5"),new BigDecimal("-2.5"),25),
        new Ctp(2,"Barki",new BigDecimal("34.0"),new BigDecimal("+4"),30),
                        new Ctp(2,"Barki",new BigDecimal("34.0"),new BigDecimal("+4"),30),
                                new Ctp(2,"Barki",new BigDecimal("34.0"),new BigDecimal("+4"),30),
                                new Ctp(2,"Barki",new BigDecimal("34.0"),new BigDecimal("+4"),30),
                                new Ctp(2,"Barki",new BigDecimal("34.0"),new BigDecimal("+4"),30)
    ));
    
    public String deleteAction(Ctp ctp) {
 
		ctpList.remove(ctp);
		return null;
	}
    public String addAction() {
        Ctp ctp = new Ctp(this.place,this.name,this.ctp,this.dev,this.target);
        ctpList.add(ctp);
        return null;
    }
    
    public static class Ctp{
        
        private int place;
        private String name;
        private BigDecimal ctp;
        private BigDecimal dev;
        private int target;
        
        public Ctp(int place, String name, BigDecimal ctp,
                BigDecimal dev, int target) {
            this.place = place;
            this.name = name;
            this.ctp = ctp;
            this.dev = dev;
            this.target = target;
        }
        
        public int getPlace() {
            return place;
        }

        /**
         * @param place the place to set
         */
        public void setPlace(int place) {
            this.place = place;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return the ctp
         */
        public BigDecimal getCtp() {
            return ctp;
        }

        /**
         * @param ctp the ctp to set
         */
        public void setCtp(BigDecimal ctp) {
            this.ctp = ctp;
        }

        /**
         * @return the dev
         */
        public BigDecimal getDev() {
            return dev;
        }

        /**
         * @param dev the dev to set
         */
        public void setDev(BigDecimal dev) {
            this.dev = dev;
        }

        /**
         * @return the target
         */
        public int getTarget() {
            return target;
        }

        /**
         * @param target the target to set
         */
        public void setTarget(int target) {
            this.target = target;
        }
    }
    public CtpBean() {
    }
    
}
