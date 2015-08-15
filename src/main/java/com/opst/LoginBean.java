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
import org.owasp.esapi.ESAPI;

/**
 *
 * @author karci
 */
@ManagedBean(name="loginbean")
@SessionScoped
public class LoginBean implements Serializable{
  private static final long serialVersionUID = 1L;
 
  public String login() {
      return "/mcdst/index?faces-redirect=true";
  }

  
  
  public String logout() {
    
    System.out.println("[INFO] - LOGINBEAN: Logging "+getRemoteUser()+" out.");
    return "/mcdst/index?faces-redirect=true";
  }
    public String getRemoteUser(){
//       if (currentUser.isAuthenticated()){
//           String ruser = currentUser.getPrincipal().toString();
//           return ruser;
//       }
       return null; 
    }
}