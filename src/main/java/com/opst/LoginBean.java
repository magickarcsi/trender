/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opst;

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
public class LoginBean {
  private String username;
  private String password;
  private Boolean isLoggedInRev = true;
  private String curruser = null;


  public String login () {
    FacesContext context = FacesContext.getCurrentInstance();
    HttpServletRequest request = (HttpServletRequest)
        context.getExternalContext().getRequest();
    try {
        System.out.println("[INFO] - LOGINBEAN: Logging "+getUsername()+" in.");
      request.login(getUsername(), getPassword());
    } catch (ServletException e) {
      //...
      context.addMessage(null, new FacesMessage("Login failed. "+e.getCause()));
      return "error";
    }
    HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRe‌​quest();
    String original = origRequest.getPathInfo();
        setCurruser(request.getRemoteUser());
    if (request.getRemoteUser() != null){
        setIsLoggedInRev(false);
    }
    setUsername(null);
    setPassword(null);
    return "index?faces-redirect=true";
  }

  public String logout() {
    FacesContext context = FacesContext.getCurrentInstance();
    HttpServletRequest request = (HttpServletRequest) 
        context.getExternalContext().getRequest();
    try {
        System.out.println("[INFO] - LOGINBEAN: Logging "+request.getRemoteUser()+" out.");
      request.logout();
    } catch (ServletException e) {
      //...
      context.addMessage(null, new FacesMessage("Logout failed."));
    }
    setCurruser(null);
    if (request.getRemoteUser() == null){
        setIsLoggedInRev(true);
    }
    return "index?faces-redirect=true";
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