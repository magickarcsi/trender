/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opst;

import java.io.Serializable;
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
  private Boolean isLoggedInRev = true;
  private String curruser = null;


  public String login () {
    FacesContext context = FacesContext.getCurrentInstance();
    context.getExternalContext().getRequest();
    System.out.println("[INFO] - LOGINBEAN: Logging "+getUsername()+" in.");
        doLogin(getUsername(), getPassword());
        setCurruser(getRemoteUser());
    if (getRemoteUser() != null){
        setIsLoggedInRev(false);
    }
    setUsername(null);
    setPassword(null);
    return "index?faces-redirect=true";
  }

  public String logout() {
    FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getRequest();
    HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
    System.out.println("[INFO] - LOGINBEAN: Logging "+getRemoteUser()+" out.");
    doLogout();
    setCurruser(null);
    if (getRemoteUser() == null){
        setIsLoggedInRev(true);
    }
    return "index?faces-redirect=true";
  }
    public String getRemoteUser(){
       String ruser = null;
        
       return ruser; 
    }

    public void doLogin(String uname, String pword) {
        
    }
    public void doLogout(){
        
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
}