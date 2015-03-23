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


  public String login () {
    FacesContext context = FacesContext.getCurrentInstance();
    HttpServletRequest request = (HttpServletRequest)
        context.getExternalContext().getRequest();
    try {
        System.out.println("[INFO]"+getUsername()+" "+getPassword());
      request.login(getUsername(), getPassword());
    } catch (ServletException e) {
      //...
      context.addMessage(null, new FacesMessage("Login failed. "+e.getCause()));
      return "error";
    }
    HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRe‌​quest();
    String original = origRequest.getPathInfo();
    if (request.getRemoteUser() != null){
        setIsLoggedInRev(false);
    }
    setUsername(null);
    setPassword(null);
    return "index";
  }

  public String logout() {
    FacesContext context = FacesContext.getCurrentInstance();
    HttpServletRequest request = (HttpServletRequest) 
        context.getExternalContext().getRequest();
    try {
        System.out.println("Logging "+request.getRemoteUser());
      request.logout();
    } catch (ServletException e) {
      //...
      context.addMessage(null, new FacesMessage("Logout failed."));
    }
    if (request.getRemoteUser() != null){
        setIsLoggedInRev(true);
    }
    HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRe‌​quest();
    String original = origRequest.getPathInfo();
    return original;
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
}