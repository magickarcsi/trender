
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opst;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author karci
 */
@ManagedBean(name="datebean")
@RequestScoped
public class dateBean implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private Calendar sDateCalendar = new GregorianCalendar();
    
    private int akarmi = sDateCalendar.get(Calendar.DAY_OF_WEEK);
    private int woy = sDateCalendar.get(Calendar.WEEK_OF_YEAR);
    // We know week number and year.
    int week = 3;
    int year = 2010;

    public Calendar showme(int y, int m, int d){
        Calendar calorie = (Calendar) sDateCalendar.clone();
        calorie.set(Calendar.YEAR, y);
        calorie.set(Calendar.MONTH, m); // 11 = december
        calorie.set(Calendar.DAY_OF_MONTH, d);
        setWoy(calorie.get(Calendar.WEEK_OF_YEAR));
        return calorie;
    }  
    public Calendar firstDayOfWeek(int y, int m, int d){
     Calendar cal = (Calendar) sDateCalendar.clone();
     cal.set(Calendar.YEAR, y);
     cal.set(Calendar.MONTH, m); // 11 = december
     cal.set(Calendar.DAY_OF_MONTH, d);
     int day = cal.get(Calendar.DAY_OF_YEAR);
     while(cal.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY){
          cal.set(Calendar.DAY_OF_YEAR, --day);
     
     }
     
     return cal;
    }  
    public Calendar lastDayOfWeek( int y, int m, int d){
     Calendar cal = (Calendar) sDateCalendar.clone();
     cal.set(Calendar.YEAR, y);
     cal.set(Calendar.MONTH, m); // 11 = december
     cal.set(Calendar.DAY_OF_MONTH, d);
     int day = cal.get(Calendar.DAY_OF_YEAR);
     while(cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){
          cal.set(Calendar.DAY_OF_YEAR, ++day);
     }
     return cal;
    }
    
    public void getadate(){
        ymd=getYmd();
        if (!"".equals(ymd))
        {
        String[] parts = ymd.split("/");
        String d = parts[0]; // day
        String m = parts[1]; //month
        String y = parts[2]; //year
        if (!"dd".equals(d))
        {
        setD(Integer.parseInt(d));
        setM(Integer.parseInt(m)-1);
        setY(Integer.parseInt(y));
        fdow = sdf.format(firstDayOfWeek(getY(), getM(), getD()).getTime());
        ldow = sdf.format(lastDayOfWeek(getY(), getM(), getD()).getTime());
        showme = sdf.format(showme(getY(), getM(), getD()).getTime());
        }}
    }
    Calendar cal = (Calendar) sDateCalendar.clone();
    private int y = cal.get(Calendar.YEAR);
    private int m = cal.get(Calendar.MONTH);
    private int d = cal.get(Calendar.DAY_OF_MONTH);
    private String ymd = "dd/MM/yyyy";
    /*private int y = 2015;
    private int m = 0;
    private int d = 26;*/
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private String fdow = sdf.format(firstDayOfWeek(getY(), getM(), getD()).getTime());
    private String ldow = sdf.format(lastDayOfWeek(getY(), getM(), getD()).getTime());
    private String showme = sdf.format(showme(getY(), getM(), getD()).getTime());
    
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
    
    /**
     * Creates a new instance of dateBean
     */
    public dateBean() {
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the m
     */
    public int getM() {
        return m;
    }

    /**
     * @param m the m to set
     */
    public void setM(int m) {
        this.m = m;
    }

    /**
     * @return the d
     */
    public int getD() {
        return d;
    }

    /**
     * @param d the d to set
     */
    public void setD(int d) {
        this.d = d;
    }

    /**
     * @return the ymd
     */
    public String getYmd() {
        return ymd;
    }

    /**
     * @param ymd the ymd to set
     */
    public void setYmd(String ymd) {
        this.ymd = ymd;
    }
    
}
