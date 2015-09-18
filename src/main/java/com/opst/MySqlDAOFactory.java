/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opst;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author karci
 */

class MySqlDAOFactory {
    public static final String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
    public static final String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
    public static final String dbuser = System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
    public static final String dbpw = System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");
    public static final String dbaddress = host+":"+port;
    

  public static Connection createConnection(String db) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
      try {
            // The newInstance() call is a work around for some
            // broken Java implementations

            Class.forName("com.mysql.jdbc.Driver").newInstance();
        }
            catch (ClassNotFoundException ex) {System.out.println("[EXCEPTION] "+ex.toString()); }
            catch (InstantiationException ex) {System.out.println("[EXCEPTION] "+ex.toString()); }
            catch (IllegalAccessException ex) {System.out.println("[EXCEPTION] "+ex.toString()); }
        Connection conn = null;
        String database = db;
        try {
            conn =
        DriverManager.getConnection("jdbc:mysql://"+dbaddress+"/"+database+"?" +
                                   "user="+dbuser+"&password="+dbpw);
        }
            catch (SQLException ex) {}
        return conn;
  }

  /*public BankDAO getBankDAO() throws SQLException {
      Connection conn = DriverManager.getConnection(url, username, password);
    return new BankDAO(conn);
  }*/
}
