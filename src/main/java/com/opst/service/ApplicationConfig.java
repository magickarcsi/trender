/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opst.service;

import java.util.Set;

/**
 *
 * @author karci
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends javax.ws.rs.core.Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<Class<?>>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.opst.service.CtpDailyFacadeREST.class);
        resources.add(com.opst.service.CtpPodFacadeREST.class);
        resources.add(com.opst.service.CtpWeeklyByManagerFacadeREST.class);
        resources.add(com.opst.service.CtpWeeklyByPodFacadeREST.class);
        resources.add(com.opst.service.NamesFacadeREST.class);
    }
    
}
