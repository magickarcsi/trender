/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opst;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author karci
 */
@ManagedBean(name="uploadwizzard")
@ViewScoped
public class UploadWizzard {

    public String onFlowProcess(FlowEvent event) {
        
            return event.getNewStep();
    }
    
    /**
     * Creates a new instance of UploadWizzard
     */
    public UploadWizzard() {
    }
    
}
