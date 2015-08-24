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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
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
    public static Map<String,Object> getWeek2Value() {
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
    public static void setWeek2Value(Map<String,Object> aWeek2Value) {
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
    private int week = currentweek-1;
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
    
    private Boolean serviceOn = true;
    private Boolean productionOn = true;
    private Boolean trainingOn = true;
    private Boolean messageBoardOn = false;

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
    
    private static Map<String,Object> week2Value;
	static{
		setWeek2Value(new LinkedHashMap<String,Object>());
                for (int i=1; i<=getCurrentweek(); i++) {
                getWeek2Value().put("Week "+i, i); //label, value    
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
    
    public void getFandL(int week) {
        setWeek(this.week);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        setFirst(sdf.format(firstDayOfWeekByWN(week).getTime()));
        setLast(sdf.format(lastDayOfWeekByWN(week).getTime()));
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
