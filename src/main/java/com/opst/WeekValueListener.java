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
 
public class WeekValueListener implements ValueChangeListener{

	@Override
	public void processValueChange(ValueChangeEvent event)
			throws AbortProcessingException, NullPointerException, ClassCastException {
		
		//access country bean directly
		adminBean adminbean = (adminBean) FacesContext.getCurrentInstance().
			getExternalContext().getSessionMap().get("adminbean");

		adminbean.getFandL((Integer) event.getNewValue());
                //adminbean.setWeek((Integer) event.getNewValue());
		
	}
	
}
