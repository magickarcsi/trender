/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opst;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ArrayList;
import java.util.Arrays;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="kvsbean")
@SessionScoped
public class KvsBean implements Serializable{
    
    private static final long serialVersionUID = 1L;

    /**
     * @return the kvsList
     */
    public ArrayList<Kvs> getKvsList() {
        return kvsList;
    }

    /**
     * @return the MONDAY
     */
    
    int place;
    String name;
    BigDecimal kvs;
    BigDecimal dev;
    int target;
    
    private Calendar sDateCalendar = new GregorianCalendar();
    
    private int akarmi = sDateCalendar.get(Calendar.DAY_OF_WEEK);
    private int woy = sDateCalendar.get(Calendar.WEEK_OF_YEAR);
    // We know week number and year.
    int week = 3;
    int year = 2010;

    
    public Calendar firstDayOfWeek(){
     SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
     Calendar cal = (Calendar) sDateCalendar.clone();
     cal.set(Calendar.YEAR, 2015);
     cal.set(Calendar.MONTH, 0); // 11 = december
     cal.set(Calendar.DAY_OF_MONTH, 26);
     int day = cal.get(Calendar.DAY_OF_YEAR);
     while(cal.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY){
          cal.set(Calendar.DAY_OF_YEAR, --day);
          
     }
     
     return cal;
    }  
    public Calendar showme(){
        Calendar calorie = (Calendar) sDateCalendar.clone();
        calorie.set(Calendar.YEAR, 2015);
        calorie.set(Calendar.MONTH, 0); // 11 = december
        calorie.set(Calendar.DAY_OF_MONTH, 26);
        return calorie;
    }
    public Calendar lastDayOfWeek(){
     SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
     Calendar cal = (Calendar) sDateCalendar.clone();
     cal.set(Calendar.YEAR, 2015);
     cal.set(Calendar.MONTH, 0); // 11 = december
     cal.set(Calendar.DAY_OF_MONTH, 26);
     int day = cal.get(Calendar.DAY_OF_YEAR);
     while(cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){
          cal.set(Calendar.DAY_OF_YEAR, ++day);
     }
     return cal;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private String fdow = sdf.format(firstDayOfWeek().getTime());
    private String ldow = sdf.format(lastDayOfWeek().getTime());
    private String showme = sdf.format(showme().getTime());
    
    
    public ArrayList<Kvs> kvsList = 
		new ArrayList<Kvs>(Arrays.asList(
        new Kvs(1,"Marton",new BigDecimal("32.5"),new BigDecimal("-2.5"),25),
        new Kvs(2,"Barki",new BigDecimal("34.0"),new BigDecimal("+4"),30)
    ));
    
    public String deleteAction(Kvs kvs) {
 
		kvsList.remove(kvs);
		return null;
	}
    public String addAction() {
        Kvs kvs = new Kvs(this.place,this.name,this.kvs,this.dev,this.target);
        kvsList.add(kvs);
        return null;
    }

    /**
     * @return the woy
     */
    public int getWoy() {
        return woy;
    }

    /**
     * @param woy the woy to set
     */
    public void setWoy(int woy) {
        this.woy = woy;
    }


    /**
     * @return the fdow
     */
    public String getFdow() {
        return fdow;
    }

    /**
     * @return the ldow
     */
    public String getLdow() {
        return ldow;
    }

    /**
     * @return the akarmi
     */
    public int getAkarmi() {
        return akarmi;
    }

    /**
     * @return the showme
     */
    public String getShowme() {
        return showme;
    }
    
    public static class Kvs{
        
        private int place;
        private String name;
        private BigDecimal kvs;
        private BigDecimal dev;
        private int target;
        
        public Kvs(int place, String name, BigDecimal kvs,
                BigDecimal dev, int target) {
            
            this.place = place;
            this.name = name;
            this.kvs = kvs;
            this.dev = dev;
            this.target = target;
        }
        /**
         * @return the place
         */
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
         * @return the kvs
         */
        public BigDecimal getKvs() {
            return kvs;
        }

        /**
         * @param kvs the kvs to set
         */
        public void setKvs(BigDecimal kvs) {
            this.kvs = kvs;
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
    public KvsBean() {
    }
    
}
