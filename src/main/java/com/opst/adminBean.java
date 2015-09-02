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
import com.opst.CtpBean;
import com.opst.KvsBean;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.w3c.dom.Document;


/**
 *
 * @author karci
 */
@ManagedBean(name="adminbean")
@SessionScoped
public class adminBean implements Serializable{
    
    private static final long serialVersionUID = 1L;

    /**
     * @return the week2Value
     */
    public Map<String,Object> getWeek2Value() {
        return week2Value;
    }

    /**
     * @param aWeek2Value the week2Value to set
     */
    public void previousWeek() throws Exception{
        if (getWeek()==1){
            getCtpByWeek(week,year);
        }
        else
        setWeek(week-1);
        getCtpByWeek(week,year);
    }
    public void nextWeek() throws Exception{
        if (getWeek()==currentweek)
        {
            getCtpByWeek(week,year);
        }
        else
        setWeek(week+1);
        getCtpByWeek(week,year);
    }
    public  void setWeek2Value(Map<String,Object> aWeek2Value) {
        week2Value = aWeek2Value;
    }
    
    public int id;
    public String surname;
    public String lastname;
    private String first;
    private String last;
    private String firstandlast;
    private int role = 1;
    private static Calendar sDateCalendar = new GregorianCalendar();
    private static int currentweek = sDateCalendar.get(Calendar.WEEK_OF_YEAR);
    private int week = 0;
    private int year = 2015;
    public int bonus;
    private String searchfield;
    public static String db_table = "names";
    //public Person p = null;
    Connection conn = null;
    Statement stmt = null;
    Person LD = new Person(0, 0, "Lusta", "Diszno", 6, 0, "Now", 48, "mkoran");
    private Person person = null;
    private Ctp ctp = null;
    private ArrayList<Ctp> c = new ArrayList<Ctp>();
    //private ArrayList<Person> lis = new ArrayList<Person>(Arrays.asList(LD));
    private ArrayList<Person> p = new ArrayList<Person>(Arrays.asList(LD));
    private String personrole = null;
    private Boolean detailsCollapsed = true;
    private Boolean leaderboardShouldBeCollapsed = true;
    SimpleDateFormat ddmm = new SimpleDateFormat("dd/MM");
    
    private Day monday = new Day();
    private Day tuesday = new Day();
    private Day wednesday = new Day();
    private Day thursday = new Day();
    private Day friday = new Day();
    private Day saturday = new Day();
    private Day sunday = new Day();
    
    public static uploadData udata = new uploadData();
    
    private Boolean serviceOn = true;
    private Boolean productionOn = true;
    private Boolean trainingOn = true;
    private Boolean messageBoardOn = false;

    public String dateToString(Date date){
        String datestring = ddmm.format(date);
        return datestring;
    }
    
    public String twoDecimal(Double szam){
        DecimalFormat df = new DecimalFormat("0.##"); 
        String twodecimal = df.format(szam);
        return twodecimal;
    }
    
    public void service() {
    
    }
    
    public void production() {
        
    }
    
    public void training() {
        
    }
    
    public void messageboard() {
        
    }
    /**
     * @return the role
     */
    public int getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(int role) {
        this.role = role;
    }

    /**
     * @return the person
     */
    public Person getPerson() {
        return person;
    }

    /**
     * @param person the person to set
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * @return the personrole
     */
    public String getPersonrole() {
        return personrole;
    }

    /**
     * @param personrole the personrole to set
     */
    public void setPersonrole(String personrole) {
        this.personrole = personrole;
    }

    /**
     * @return the ctp
     */
    public Ctp getCtp() {
        return ctp;
    }

    /**
     * @param ctp the ctp to set
     */
    public void setCtp(Ctp ctp) {
        this.ctp = ctp;
    }

    /**
     * @return the c
     */
    public ArrayList<Ctp> getC() {
        return c;
    }

    /**
     * @param c the c to set
     */
    public void setC(ArrayList<Ctp> c) {
        this.c = c;
    }

    /**
     * @return the week
     */
    public int getWeek() {
        return week;
    }

    /**
     * @param week the week to set
     */
    public void setWeek(int week) {
        this.week = week;
    }

    /**
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * @return the currentweek
     */
    public static int getCurrentweek() {
        return currentweek;
    }

    /**
     * @param currentweek the currentweek to set
     */
    public void setCurrentweek(int currentweek) {
        this.currentweek = currentweek;
    }

    /**
     * @return the favWeek2
     */
    public String getFavWeek2() {
        return favWeek2;
    }

    /**
     * @param favWeek2 the favWeek2 to set
     */
    public void setFavWeek2(String favWeek2) {
        this.favWeek2 = favWeek2;
    }

    /**
     * @return the first
     */
    public String getFirst() {
        return first;
    }

    /**
     * @param first the first to set
     */
    public void setFirst(String first) {
        this.first = first;
    }

    /**
     * @return the last
     */
    public String getLast() {
        return last;
    }

    /**
     * @param last the last to set
     */
    public void setLast(String last) {
        this.last = last;
    }

    /**
     * @return the firstandlast
     */
    public String getFirstandlast() {
        return firstandlast;
    }

    /**
     * @param firstandlast the firstandlast to set
     */
    public void setFirstandlast(String firstandlast) {
        this.firstandlast = firstandlast;
    }

    /**
     * @return the searchfield
     */
    public String getSearchfield() {
        return searchfield;
    }

    /**
     * @param searchfield the searchfield to set
     */
    public void setSearchfield(String searchfield) {
        this.searchfield = searchfield;
    }

    /**
     * @return the detailsCollapsed
     */
    public Boolean getDetailsCollapsed() {
        return detailsCollapsed;
    }

    /**
     * @param detailsCollapsed the detailsCollapsed to set
     */
    public void setDetailsCollapsed(Boolean detailsCollapsed) {
        this.detailsCollapsed = detailsCollapsed;
    }

    /**
     * @return the leaderboardShouldBeCollapsed
     */
    public Boolean getLeaderboardShouldBeCollapsed() {
        return leaderboardShouldBeCollapsed;
    }

    /**
     * @param leaderboardShouldBeCollapsed the leaderboardShouldBeCollapsed to set
     */
    public void setLeaderboardShouldBeCollapsed(Boolean leaderboardShouldBeCollapsed) {
        this.leaderboardShouldBeCollapsed = leaderboardShouldBeCollapsed;
    }

    /**
     * @return the serviceOn
     */
    public Boolean getServiceOn() {
        return serviceOn;
    }

    /**
     * @param serviceOn the serviceOn to set
     */
    public void setServiceOn(Boolean serviceOn) {
        this.serviceOn = serviceOn;
    }

    /**
     * @return the productionOn
     */
    public Boolean getProductionOn() {
        return productionOn;
    }

    /**
     * @param productionOn the productionOn to set
     */
    public void setProductionOn(Boolean productionOn) {
        this.productionOn = productionOn;
    }

    /**
     * @return the trainingOn
     */
    public Boolean getTrainingOn() {
        return trainingOn;
    }

    /**
     * @param trainingOn the trainingOn to set
     */
    public void setTrainingOn(Boolean trainingOn) {
        this.trainingOn = trainingOn;
    }

    /**
     * @return the messageBoardOn
     */
    public Boolean getMessageBoardOn() {
        return messageBoardOn;
    }

    /**
     * @param messageBoardOn the messageBoardOn to set
     */
    public void setMessageBoardOn(Boolean messageBoardOn) {
        this.messageBoardOn = messageBoardOn;
    }

    /**
     * @return the managers
     */
    public Map<String,Object> getManagers() {
        return managers;
    }

    /**
     * @param managers the managers to set
     */
    public void setManagers(Map<String,Object> managers) {
        this.managers = managers;
    }

    /**
     * @return the monday
     */
    public Day getMonday() {
        return monday;
    }

    /**
     * @param monday the monday to set
     */
    public void setMonday(Day monday) {
        this.monday = monday;
    }

    /**
     * @return the tuesday
     */
    public Day getTuesday() {
        return tuesday;
    }

    /**
     * @param tuesday the tuesday to set
     */
    public void setTuesday(Day tuesday) {
        this.tuesday = tuesday;
    }

    /**
     * @return the wednesday
     */
    public Day getWednesday() {
        return wednesday;
    }

    /**
     * @param wednesday the wednesday to set
     */
    public void setWednesday(Day wednesday) {
        this.wednesday = wednesday;
    }

    /**
     * @return the thursday
     */
    public Day getThursday() {
        return thursday;
    }

    /**
     * @param thursday the thursday to set
     */
    public void setThursday(Day thursday) {
        this.thursday = thursday;
    }

    /**
     * @return the friday
     */
    public Day getFriday() {
        return friday;
    }

    /**
     * @param friday the friday to set
     */
    public void setFriday(Day friday) {
        this.friday = friday;
    }

    /**
     * @return the saturday
     */
    public Day getSaturday() {
        return saturday;
    }

    /**
     * @param saturday the saturday to set
     */
    public void setSaturday(Day saturday) {
        this.saturday = saturday;
    }

    /**
     * @return the sunday
     */
    public Day getSunday() {
        return sunday;
    }

    /**
     * @param sunday the sunday to set
     */
    public void setSunday(Day sunday) {
        this.sunday = sunday;
    }

    /**
     * @return the managernames
     */
    public Map<Object,String> getManagernames() {
        return managernames;
    }

    /**
     * @param managernames the managernames to set
     */
    public void setManagernames(Map<Object,String> managernames) {
        this.managernames = managernames;
    }
    public static class Ctp{
        private int id;
        private String name;
        private int ctp;
        private int dev;
        private int target;
        private int week;
        private int year;
        
        public Ctp(int id, String name, int ctp, int dev, int target, int week, int year){
            this.id = id;
            this.name = name;
            this.ctp = ctp;
            this.dev = dev;
            this.target = target;
            this.week = week;
            this.year = year;
        }

        /**
         * @return the id
         */
        public int getId() {
            return id;
        }

        /**
         * @param id the id to set
         */
        public void setId(int id) {
            this.id = id;
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
        public int getCtp() {
            return ctp;
        }

        /**
         * @param ctp the ctp to set
         */
        public void setCtp(int ctp) {
            this.ctp = ctp;
        }

        /**
         * @return the dev
         */
        public int getDev() {
            return dev;
        }

        /**
         * @param dev the dev to set
         */
        public void setDev(int dev) {
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

        /**
         * @return the week
         */
        public int getWeek() {
            return week;
        }

        /**
         * @param week the week to set
         */
        public void setWeek(int week) {
            this.week = week;
        }

        /**
         * @return the year
         */
        public int getYear() {
            return year;
        }

        /**
         * @param year the year to set
         */
        public void setYear(int year) {
            this.year = year;
        }
                
    }
    
    public static class PoD {
        private int man;
        private Double ctp;
        
        
        public void put(int man, Double ctp) {
            this.man = man;
            this.ctp = ctp;
            
        }
        public Double get(int man) {
            if (man == this.man){
                return this.ctp;
            }
            else
            return null;
        }
        
    }
    
    public static class uploadData {
        private int week;
        private Day monday = new Day();
        private Day tuesday = new Day();
        private Day wednesday = new Day();
        private Day thursday = new Day();
        private Day friday = new Day();
        private Day saturday = new Day();
        private Day sunday = new Day();
        
        private Double[] mondayctp = new Double[24];
        private Double[] tuesdayctp = new Double[24];
        private Double[] wednesdayctp = new Double[24];
        private Double[] thursdayctp = new Double[24];
        private Double[] fridayctp = new Double[24];
        private Double[] saturdayctp = new Double[24];
        private Double[] sundayctp = new Double[24];
        
        private Double[] mondaypod = new Double[4];
        private Double[] tuesdaypod = new Double[4];
        private Double[] wednesdaypod = new Double[4];
        private Double[] thursdaypod = new Double[4];
        private Double[] fridaypod = new Double[4];
        private Double[] saturdaypod = new Double[4];
        private Double[] sundaypod = new Double[4];
        
        private Double mondayavg;
        private Double tuesdayavg;
        private Double wednesdayavg;
        private Double thursdayavg;
        private Double fridayavg;
        private Double saturdayavg;
        private Double sundayavg;
        private Double weekavg;
        public uploadData(){
            
        }

        /**
         * @return the week
         */
        public void clear() {
         int week = 0;
         Day monday = null;
         Day tuesday = null;
         Day wednesday = null;
         Day thursday = null;
         Day friday = null;
         Day saturday = null;
         Day sunday = null;
         Double[] mondaypod = null;
         Double[] tuesdaypod = null;
         Double[] wednesdaypod = null;
         Double[] thursdaypod = null;
         Double[] fridaypod = null;
         Double[] saturdaypod = null;
         Double[] sundaypod = null;
        
         Double mondayavg = null;
         Double tuesdayavg = null;
         Double wednesdayavg = null;
         Double thursdayavg = null;
         Double fridayavg = null;
         Double saturdayavg = null;
         Double sundayavg = null;
         Double weekavg = null;
        }
        
        public int getWeek() {
            return week;
        }

        /**
         * @return the monday
         */
        public Day getMonday() {
            return monday;
        }

        /**
         * @return the tuesday
         */
        public Day getTuesday() {
            return tuesday;
        }

        /**
         * @return the wednesday
         */
        public Day getWednesday() {
            return wednesday;
        }

        /**
         * @return the thursday
         */
        public Day getThursday() {
            return thursday;
        }

        /**
         * @return the friday
         */
        public Day getFriday() {
            return friday;
        }

        /**
         * @return the saturday
         */
        public Day getSaturday() {
            return saturday;
        }

        /**
         * @return the sunday
         */
        public Day getSunday() {
            return sunday;
        }

        /**
         * @return the mondaypod
         */
        public Double[] getMondaypod() {
            return mondaypod;
        }

        /**
         * @return the tuesdaypod
         */
        public Double[] getTuesdaypod() {
            return tuesdaypod;
        }

        /**
         * @return the wednesdaypod
         */
        public Double[] getWednesdaypod() {
            return wednesdaypod;
        }

        /**
         * @return the thursdaypod
         */
        public Double[] getThursdaypod() {
            return thursdaypod;
        }

        /**
         * @return the fridaypod
         */
        public Double[] getFridaypod() {
            return fridaypod;
        }

        /**
         * @return the saturdaypod
         */
        public Double[] getSaturdaypod() {
            return saturdaypod;
        }

        /**
         * @return the sundaypod
         */
        public Double[] getSundaypod() {
            return sundaypod;
        }

        /**
         * @return the mondayavg
         */
        public Double getMondayavg() {
            return mondayavg;
        }

        /**
         * @return the tuesdayavg
         */
        public Double getTuesdayavg() {
            return tuesdayavg;
        }

        /**
         * @return the wednesdayavg
         */
        public Double getWednesdayavg() {
            return wednesdayavg;
        }

        /**
         * @return the thursdayavg
         */
        public Double getThursdayavg() {
            return thursdayavg;
        }

        /**
         * @return the fridayavg
         */
        public Double getFridayavg() {
            return fridayavg;
        }

        /**
         * @return the saturdayavg
         */
        public Double getSaturdayavg() {
            return saturdayavg;
        }

        /**
         * @return the sundayavg
         */
        public Double getSundayavg() {
            return sundayavg;
        }

        /**
         * @return the weekavg
         */
        public Double getWeekavg() {
            return weekavg;
        }

        /**
         * @param week the week to set
         */
        public void setWeek(int week) {
            this.week = week;
        }

        /**
         * @param monday the monday to set
         */
        public void setMonday(Day monday) {
            this.monday = monday;
        }

        /**
         * @param tuesday the tuesday to set
         */
        public void setTuesday(Day tuesday) {
            this.tuesday = tuesday;
        }

        /**
         * @param wednesday the wednesday to set
         */
        public void setWednesday(Day wednesday) {
            this.wednesday = wednesday;
        }

        /**
         * @param thursday the thursday to set
         */
        public void setThursday(Day thursday) {
            this.thursday = thursday;
        }

        /**
         * @param friday the friday to set
         */
        public void setFriday(Day friday) {
            this.friday = friday;
        }

        /**
         * @param saturday the saturday to set
         */
        public void setSaturday(Day saturday) {
            this.saturday = saturday;
        }

        /**
         * @param sunday the sunday to set
         */
        public void setSunday(Day sunday) {
            this.sunday = sunday;
        }

        /**
         * @param mondaypod the mondaypod to set
         */
        public void setMondaypod(Double[] mondaypod) {
            this.mondaypod = mondaypod;
        }

        /**
         * @param tuesdaypod the tuesdaypod to set
         */
        public void setTuesdaypod(Double[] tuesdaypod) {
            this.tuesdaypod = tuesdaypod;
        }

        /**
         * @param wednesdaypod the wednesdaypod to set
         */
        public void setWednesdaypod(Double[] wednesdaypod) {
            this.wednesdaypod = wednesdaypod;
        }

        /**
         * @param thursdaypod the thursdaypod to set
         */
        public void setThursdaypod(Double[] thursdaypod) {
            this.thursdaypod = thursdaypod;
        }

        /**
         * @param fridaypod the fridaypod to set
         */
        public void setFridaypod(Double[] fridaypod) {
            this.fridaypod = fridaypod;
        }

        /**
         * @param saturdaypod the saturdaypod to set
         */
        public void setSaturdaypod(Double[] saturdaypod) {
            this.saturdaypod = saturdaypod;
        }

        /**
         * @param sundaypod the sundaypod to set
         */
        public void setSundaypod(Double[] sundaypod) {
            this.sundaypod = sundaypod;
        }

        /**
         * @param mondayavg the mondayavg to set
         */
        public void setMondayavg(Double mondayavg) {
            this.mondayavg = mondayavg;
        }

        /**
         * @param tuesdayavg the tuesdayavg to set
         */
        public void setTuesdayavg(Double tuesdayavg) {
            this.tuesdayavg = tuesdayavg;
        }

        /**
         * @param wednesdayavg the wednesdayavg to set
         */
        public void setWednesdayavg(Double wednesdayavg) {
            this.wednesdayavg = wednesdayavg;
        }

        /**
         * @param thursdayavg the thursdayavg to set
         */
        public void setThursdayavg(Double thursdayavg) {
            this.thursdayavg = thursdayavg;
        }

        /**
         * @param fridayavg the fridayavg to set
         */
        public void setFridayavg(Double fridayavg) {
            this.fridayavg = fridayavg;
        }

        /**
         * @param saturdayavg the saturdayavg to set
         */
        public void setSaturdayavg(Double saturdayavg) {
            this.saturdayavg = saturdayavg;
        }

        /**
         * @param sundayavg the sundayavg to set
         */
        public void setSundayavg(Double sundayavg) {
            this.sundayavg = sundayavg;
        }

        /**
         * @param weekavg the weekavg to set
         */
        public void setWeekavg(Double weekavg) {
            this.weekavg = weekavg;
        }

        /**
         * @return the mondayctp
         */
        public Double[] getMondayctp() {
            return mondayctp;
        }

        /**
         * @param mondayctp the mondayctp to set
         */
        public void setMondayctp(Double[] mondayctp) {
            this.mondayctp = mondayctp;
        }

        /**
         * @return the tuesdayctp
         */
        public Double[] getTuesdayctp() {
            return tuesdayctp;
        }

        /**
         * @param tuesdayctp the tuesdayctp to set
         */
        public void setTuesdayctp(Double[] tuesdayctp) {
            this.tuesdayctp = tuesdayctp;
        }

        /**
         * @return the wednesdayctp
         */
        public Double[] getWednesdayctp() {
            return wednesdayctp;
        }

        /**
         * @param wednesdayctp the wednesdayctp to set
         */
        public void setWednesdayctp(Double[] wednesdayctp) {
            this.wednesdayctp = wednesdayctp;
        }

        /**
         * @return the thursdayctp
         */
        public Double[] getThursdayctp() {
            return thursdayctp;
        }

        /**
         * @param thursdayctp the thursdayctp to set
         */
        public void setThursdayctp(Double[] thursdayctp) {
            this.thursdayctp = thursdayctp;
        }

        /**
         * @return the fridayctp
         */
        public Double[] getFridayctp() {
            return fridayctp;
        }

        /**
         * @param fridayctp the fridayctp to set
         */
        public void setFridayctp(Double[] fridayctp) {
            this.fridayctp = fridayctp;
        }

        /**
         * @return the saturdayctp
         */
        public Double[] getSaturdayctp() {
            return saturdayctp;
        }

        /**
         * @param saturdayctp the saturdayctp to set
         */
        public void setSaturdayctp(Double[] saturdayctp) {
            this.saturdayctp = saturdayctp;
        }

        /**
         * @return the sundayctp
         */
        public Double[] getSundayctp() {
            return sundayctp;
        }

        /**
         * @param sundayctp the sundayctp to set
         */
        public void setSundayctp(Double[] sundayctp) {
            this.sundayctp = sundayctp;
        }
        
    }
    
    public static class Day{
        private Date date;
        private int overnight;
        private int open;
        private int day;
        private int evening;
        private int updated_by;
        
        public Day(Date date, int overnight, int open, int day, int evening, int updated_by) {
            this.date = date;
            this.overnight = overnight;
            this.open = open;
            this.day = day;
            this.evening = evening;
            this.updated_by = updated_by;
        }
        
        public Day(){
            
        }

        /**
         * @return the date
         */
        public Date getDate() {
            return date;
        }

        /**
         * @param date the date to set
         */
        public void setDate(Date date) {
            this.date = date;
        }

        /**
         * @return the overnight
         */
        public int getOvernight() {
            return overnight;
        }

        /**
         * @param overnight the overnight to set
         */
        public void setOvernight(int overnight) {
            this.overnight = overnight;
        }

        /**
         * @return the open
         */
        public int getOpen() {
            return open;
        }

        /**
         * @param open the open to set
         */
        public void setOpen(int open) {
            this.open = open;
        }

        /**
         * @return the day
         */
        public int getDay() {
            return day;
        }

        /**
         * @param day the day to set
         */
        public void setDay(int day) {
            this.day = day;
        }

        /**
         * @return the evening
         */
        public int getEvening() {
            return evening;
        }

        /**
         * @param evening the evening to set
         */
        public void setEvening(int evening) {
            this.evening = evening;
        }

        /**
         * @return the updated_by
         */
        public int getUpdated_by() {
            return updated_by;
        }

        /**
         * @param updated_by the updated_by to set
         */
        public void setUpdated_by(int updated_by) {
            this.updated_by = updated_by;
        }
        
    }
    public static class Person{
        
        private int id;
        private int store;
        private String surname;
        private String firstname;
        private int role;
        private int bonus;
        private String lastupdated;
        private int updatedby;
        private String updatedby_s;
        
        public Person(int id, int store, String surname, String firstname, int role, int bonus, String lastupdated, int updatedby, String updatedby_s) {
        this.id = id;
        this.store = store;
        this.surname = surname;
        this.firstname = firstname;
        this.role = role;
        this.bonus = bonus;
        this.lastupdated = lastupdated;
        this.updatedby = updatedby;
        this.updatedby_s = updatedby_s;
    }

        /**
         * @return the id
         */
        public int getId() {
            return id;
        }

        /**
         * @param id the id to set
         */
        public void setId(int id) {
            this.id = id;
        }

        /**
         * @return the surname
         */
        public String getSurname() {
            return surname;
        }

        /**
         * @param surname the surname to set
         */
        public void setSurname(String surname) {
            this.surname = surname;
        }

        /**
         * @return the firstname
         */
        public String getFirstname() {
            return firstname;
        }

        /**
         * @param firstname the firstname to set
         */
        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        /**
         * @return the role
         */
        public int getRole() {
            return role;
        }

        /**
         * @param role the role to set
         */
        public void setRole(int role) {
            this.role = role;
        }

        /**
         * @return the bonus
         */
        public int getBonus() {
            return bonus;
        }

        /**
         * @param bonus the bonus to set
         */
        public void setBonus(int bonus) {
            this.bonus = bonus;
        }

        /**
         * @return the store
         */
        public int getStore() {
            return store;
        }

        /**
         * @param store the store to set
         */
        public void setStore(int store) {
            this.store = store;
        }

        /**
         * @return the lastupdated
         */
        public String getLastupdated() {
            return lastupdated;
        }

        /**
         * @param lastupdated the lastupdated to set
         */
        public void setLastupdated(String lastupdated) {
            this.lastupdated = lastupdated;
        }

        /**
         * @return the updatedby
         */
        public int getUpdatedby() {
            return updatedby;
        }

        /**
         * @param updatedby the updatedby to set
         */
        public void setUpdatedby(int updatedby) {
            this.updatedby = updatedby;
        }

        /**
         * @return the updatedby_s
         */
        public String getUpdatedby_s() {
            return updatedby_s;
        }

        /**
         * @param updatedby_s the updatedby_s to set
         */
        public void setUpdatedby_s(String updatedby_s) {
            this.updatedby_s = updatedby_s;
        }
        
    }
    private String favWeek2;
    
    private Map<Object,String> managernames;
    {
        setManagernames(new LinkedHashMap<Object,String>());
    }
    
    
    private Map<String,Object> managers;
        {
        try {
            setManagers(new LinkedHashMap<String,Object>());
            Connection conn = com.opst.MySqlDAOFactory.createConnection();
            Statement stmt = null;
            ResultSet rs = null;
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select id,firstname,surname from `names` WHERE role in (6,7,8) AND is_active in (1)");
                while (rs.next())
                {
                    int id = rs.getInt(1);
                    String fn = rs.getNString(2);
                    String ln = rs.getNString(3);
                    String name = fn + " " + ln.substring(0, 1);
                    getManagers().put(name, id);
                    getManagernames().put(id, name);
                    
                }
            
        } catch (SQLException ex) {
            Logger.getLogger(adminBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(adminBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(adminBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(adminBean.class.getName()).log(Level.SEVERE, null, ex);
        }   
        }
        
        
    
        public void spread_dates(int wk) {
            getMonday().date = firstDayOfWeekByWN(wk).getTime();
        }
    
        private  Map<String,Object> week2Value;
	{
		setWeek2Value(new LinkedHashMap<String,Object>());
                getWeek2Value().put("Please choose a Week", 0);
                for (int i=1; i<=getCurrentweek(); i++) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
                String wc = sdf.format(firstDayOfWeekByWN(i).getTime());
                getWeek2Value().put("Week "+i+" - W/c: "+wc, i); //label, value    
                }
		
		
	}
 
	public Map<String,Object> getFavWeek2Value() {
		return getWeek2Value();
	}
    
   
    /**
     * Creates a new instance of adminBean
     */
    public adminBean() {
    }
    
    public Calendar firstDayOfWeekByWN(int week){
        Calendar cal = (Calendar) sDateCalendar.clone();
        int wk = week;
        cal.set(Calendar.WEEK_OF_YEAR, wk);
        int day = cal.get(Calendar.DAY_OF_YEAR);
     while(cal.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY){
          cal.set(Calendar.DAY_OF_YEAR, --day);
     }
        return cal;
    }
    
    public Calendar lastDayOfWeekByWN(int week){
        Calendar cal = (Calendar) sDateCalendar.clone();
        int wk = week;
        cal.set(Calendar.WEEK_OF_YEAR, wk);
        int day = cal.get(Calendar.DAY_OF_YEAR);
     while(cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){
          cal.set(Calendar.DAY_OF_YEAR, ++day);
     }
        return cal;
    }
    
    public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
    
    public void getFandL(int week) {
        setWeek(this.week);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        monday.date = firstDayOfWeekByWN(week).getTime();
        tuesday.date = addDays(monday.date, 1);
        wednesday.date = addDays(monday.date, 2);
        thursday.date = addDays(monday.date, 3);
        friday.date = addDays(monday.date, 4);
        saturday.date = addDays(monday.date, 5);
        sunday.date = lastDayOfWeekByWN(week).getTime();
        setFirst(sdf.format(firstDayOfWeekByWN(week).getTime()));
        setLast(sdf.format(lastDayOfWeekByWN(week).getTime()));
        setFirstandlast(getFirst()+" - "+getLast());
    }
     
    public void getCtpByWeek(int week,int year) throws Exception {
        this.setWeek(week);
        this.setYear(year);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        setFirst(sdf.format(firstDayOfWeekByWN(week).getTime()));
        setLast(sdf.format(lastDayOfWeekByWN(week).getTime()));
        setFirstandlast(getFirst()+" - "+getLast());
        getC().clear();
        Connection conn = com.opst.MySqlDAOFactory.createConnection();
        Connection conn1 = com.opst.MySqlDAOFactory.createConnection();
        Statement stmt = null;
        Statement stmt1 = null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        stmt = conn.createStatement();
        stmt1 = conn1.createStatement();
        rs = stmt.executeQuery("select id,name_id,ctp,dev,target from `ctp_weekly_by_manager` WHERE week in ("+getWeek()+") and year in ("+getYear()+") ORDER BY ctp ASC;");
        System.out.println("[INFO] - ADMINBEAN: CTP leaderboard "+getYear()+" "+getWeek());
                while (rs.next())
                {
                    rs1 = stmt1.executeQuery("select surname,firstname from `names` WHERE id in ("+rs.getInt(2)+")");
                    rs1.first();
                    String firstname = rs1.getNString(2);
                    String lastname = rs1.getNString(1);
                    String name = firstname + " " + lastname.substring(0, 1);
                    getC().add(new Ctp(rs.getInt(1),name,rs.getInt(3),rs.getInt(4),rs.getInt(5),getWeek(),getYear()));
                }
                if (getC().isEmpty()){
                    setLeaderboardShouldBeCollapsed(true);
                    FacesMessage msg = new FacesMessage("Error", "There is no leaderboard uploaded for Week "+getWeek()+".");
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    //FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
                } 
                else {
                    setLeaderboardShouldBeCollapsed(false);
                }
        
    }
    public void getDetailsById(int id) throws Exception {
        int detailsId = id;
        setDetailsCollapsed(false);
        setPerson(null);
        Connection conn = com.opst.MySqlDAOFactory.createConnection();
        Connection conn1 = com.opst.MySqlDAOFactory.createConnection();
        Statement stmt = null;
        ResultSet rs = null;
        stmt = conn.createStatement();
        try {
        rs = stmt.executeQuery("select store, surname,firstname,role,bonus,last_updated,updated_by from `names` WHERE id in ("+(detailsId)+") ORDER BY surname ASC;");
        rs.first();
                System.out.println("[INFO] - ADMINBEAN: View details: "+rs.getNString(2)+", "+rs.getNString(3)+" from "+rs.getInt(1));
                Statement stmt1 = null;
                ResultSet rs1 = null;
                stmt1 = conn1.createStatement();
                String luby = null;
                try {
                    rs1 = stmt1.executeQuery("select surname,firstname from `names` WHERE id in ("+rs.getInt(7)+")");
                    rs1.first();
                    luby = rs1.getNString(1)+", "+rs1.getNString(2);
                }
                catch(SQLException e) {
                System.out.println("[EXCEPTION] - ADMINBEAN: "+e.getMessage());
                }
                setPerson(new Person(detailsId,rs.getInt(1),rs.getNString(2),rs.getNString(3),rs.getInt(4),rs.getInt(5),rs.getTimestamp(6).toString(),rs.getInt(7),luby));
                
        }
        catch(SQLException e) {
            System.out.println("[EXCEPTION] - ADMINBEAN: "+e.getMessage());
        }
        
        switch(role) {
            case 1: setPersonrole("Crew Member"); break;
            case 2: setPersonrole("Lobby Hostess"); break;
            case 3: setPersonrole("Delivery"); break;
            case 4: setPersonrole("Crew Trainer"); break;
            case 5: setPersonrole("Floor Manager"); break;
            case 6: setPersonrole("Shift Runner"); break;
            case 7: setPersonrole("Salaried Manager"); break;
            case 8: setPersonrole("Business Manager"); break;
        }
    }
    
    public static class uploadhandler{
        
        public void getUploadedTxt(String file){
        String filename = file;
        String envVar = System.getenv("OPENSHIFT_ENV_VAR");    
        }
        
    }
    
    public void getNamesByRole(int role) throws Exception{
        this.setRole(role);
        p.clear();
        Connection conn = com.opst.MySqlDAOFactory.createConnection();
        Statement stmt = null;
        ResultSet rs = null;
        stmt = conn.createStatement();
        String roll = null;
        switch(role) {
            case 1: roll = "Crew Member"; break;
            case 2: roll = "Lobby Hostess"; break;
            case 3: roll = "Delivery"; break;
            case 4: roll = "Crew Trainer"; break;
            case 5: roll = "Floor Manager"; break;
            case 6: roll = "Shift Runner"; break;
            case 7: roll = "Salaried Manager"; break;
            case 8: roll = "Business Manager"; break;
        }
    try {    
        rs = stmt.executeQuery("select id,store,surname,firstname,bonus,last_updated,updated_by from `names` WHERE role in ("+(role)+") ORDER BY surname ASC;");
                rs.first();
                getP().add(new Person(rs.getInt(1),rs.getInt(2),rs.getNString(3),rs.getNString(4),role,rs.getInt(5),rs.getTimestamp(6).toString(),rs.getInt(7),"mkoran"));
                System.out.println("[INFO] - ADMINBEAN: Listing: "+roll+" from "+rs.getInt(2));
                while (rs.next())
                {
                    getP().add(new Person(rs.getInt(1),rs.getInt(2),rs.getNString(3),rs.getNString(4),role,rs.getInt(5),rs.getTimestamp(6).toString(),rs.getInt(7),"mkoran"));
                }
    }
        catch(SQLException e) {
            System.out.println("[EXCEPTION] - ADMINBEAN: "+e.getMessage());
        }
    }
    
    /**
     * @return the p
     */
    public ArrayList<Person> getP() {
        return p;
    }

    /**
     * @param p the p to set
     */
    public void setP(ArrayList<Person> p) {
        this.p = p;
    }
    
}
