/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opst;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author karci
 */
@ManagedBean(name="loginbean")
@SessionScoped
public class LoginBean implements Serializable{
  private static final long serialVersionUID = 1L;
  private String username;
  private String password;
  private Boolean isLoggedIn;
  private Boolean isLoggedInRev = true;
  private String curruser = null;
  
  private static final String[] users = {"mkoran:mkoran","admin:admin"};

    @EJB
    private UserService userService;
    private User current;


  public String login () {
    FacesContext context = FacesContext.getCurrentInstance();
    context.getExternalContext().getRequest();
    
    System.out.println("[INFO] - LOGINBEAN: Logging "+getUsername()+" in.");
        for (String user: users) {
            String dbUsername = user.split(":")[0];
            String dbPassword = user.split(":")[1];
             
            // Successful login
            if (dbUsername.equals(username) && dbPassword.equals(password)) {
                setIsLoggedIn(true);
                System.out.println("[INFO] - LOGINBEAN: Login successful.");
                setCurruser(username);
                return "/mcdst/index?faces-redirect=true";
            }
        }
         
        // Set login ERROR
        FacesMessage msg = new FacesMessage("Login error!", "ERROR MSG");
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext.getCurrentInstance().addMessage(null, msg);
        if (getCurruser() != null){
        setIsLoggedInRev(false);
    }
    setUsername(null);
    setPassword(null); 
        // To to login page
        return "/login?faces-redirect=true";
  }

  public String logout() {
    FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getRequest();
    HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
    System.out.println("[INFO] - LOGINBEAN: Logging "+getRemoteUser()+" out.");
    
    // Set the paremeter indicating that user is logged in to false
        setIsLoggedIn(false);
         
        // Set logout message
        FacesMessage msg = new FacesMessage("Logout success!", "INFO MSG");
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext.getCurrentInstance().addMessage(null, msg);
         
    setCurruser(null);
    if (getCurruser() == null){
        setIsLoggedInRev(true);
    }
    return "/login?faces-redirect=true";
  }
    public String getRemoteUser(){
       String ruser = null;
        
       return ruser; 
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the isLoggedIn
     */
    public Boolean getIsLoggedInRev() {
        return isLoggedInRev;
    }

    /**
     * @param isLoggedInRev the isLoggedInRev to set
     */
    public void setIsLoggedInRev(Boolean isLoggedInRev) {
        this.isLoggedInRev = isLoggedInRev;
    }

    /**
     * @return the curruser
     */
    public String getCurruser() {
        return curruser;
    }

    /**
     * @param curruser the curruser to set
     */
    public void setCurruser(String curruser) {
        this.curruser = curruser;
    }

    /**
     * @return the isLoggedIn
     */
    public Boolean getIsLoggedIn() {
        return isLoggedIn;
    }

    /**
     * @param isLoggedIn the isLoggedIn to set
     */
    public void setIsLoggedIn(Boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }
}