/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opst;

import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
/**
 *
 * @author karci
 */
public class ManagerValueListener implements ValueChangeListener {
    
    @Override
    public void processValueChange(ValueChangeEvent event)
			throws AbortProcessingException, NullPointerException, ClassCastException {
		
		//access adminbean bean directly
		adminBean adminbean = (adminBean) FacesContext.getCurrentInstance().
			getExternalContext().getSessionMap().get("adminbean");

		adminbean.getFandL((Integer) event.getNewValue());
		
	}
    
}
