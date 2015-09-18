/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opst;

import com.opst.adminBean.ctpData;
import com.opst.adminBean.Ctp;
import com.opst.adminBean.Elements;
import com.opst.adminBean.Day;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
/**
 *
 * @author karci
 */
public class CtpDAO {
    
    public ArrayList<Elements> getElements(String store, int week, int year) throws Exception {
        ArrayList<Elements> elements = new ArrayList<Elements>();
        Connection conn = com.opst.MySqlDAOFactory.createConnection(store);
        
        return elements;
    }
    public ArrayList<Ctp> getLeaderboard(String store, int week, int year) {
        ArrayList<Ctp> leaderboard= new ArrayList<Ctp>();
        
        return leaderboard;
    }
    
    public void add(ctpData data){
        String store = data.getStore();
    }
}
