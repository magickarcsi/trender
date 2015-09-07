/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opst;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.Part;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import com.opst.adminBean.uploadData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author karci
 */
@ManagedBean(name="uploadbean")
@RequestScoped
public class UploadBean {

    /**
     * Creates a new instance of UploadBean
     */
    private Part[] file = new Part[2];
    private Part monday;
    private Part tuesday;
    int BUFFER_LENGTH = 4096;
    private Calendar sDateCalendar = new GregorianCalendar();
    public Calendar showme(int y, int m, int d){
          Calendar calorie = (Calendar) sDateCalendar.clone();
          calorie.set(Calendar.YEAR, y);
          calorie.set(Calendar.MONTH, m); // 11 = december
          calorie.set(Calendar.DAY_OF_MONTH, d);
          int Woy = calorie.get(Calendar.WEEK_OF_YEAR);
          return calorie;
      }
    public Double[] ctparray = new Double[24];
    public Double[] ctp_pod = new Double[4];
    public Date ctpdate = new Date();
    

    
    
    
    public void anyad(FileUploadEvent event) throws Exception, IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat mysqldate = new SimpleDateFormat("yyyy-MM-dd");
        
        
        
        UploadedFile file = event.getFile();
        String filename = file.getFileName();
        try {
        byte[] results = new byte[(int) file.getSize()];
        
        InputStream in = file.getInputstream();
        in.read(results);
        FileOutputStream os = new FileOutputStream(System.getenv("OPENSHIFT_DATA_DIR") + filename);
        os.write(results);
        os.flush();
        in.close();
        os.close();
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
        
        //readfile
        File text = new File(System.getenv("OPENSHIFT_DATA_DIR") + filename);
        FileReader fileReader = new FileReader(text);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
                        int i =0;
                        String showme = null;                   
                        
                        String ymd= filename;
                
                if (!"".equals(ymd))
                    {
                        //Speed_of_Service_for_26_08_15_Wednesday.prn
                        String[] parts = ymd.split("_");
                        String d = parts[4]; // day
                        String m = parts[5]; //month
                        String y = "20"+parts[6]; //year
                        if (!"dd".equals(d))
                            {
                            Float D = Float.parseFloat(d);
                            int Dd = Math.round(D);
                            Float M =Float.parseFloat(m);
                            int Mm = Math.round(M)-1;
                            Float Y =Float.parseFloat(y);
                            int Yy = Math.round(Y);
                            showme = sdf.format(showme(Yy, Mm, Dd).getTime());
                            ctpdate = showme(Yy, Mm, Dd).getTime();
                            }
                System.out.println("[INFO] - UploadBean: "+showme);
                    }
        while ((line = bufferedReader.readLine()) != null) {
            //System.out.println(line);
            i++;
//            String showme = null;
//            if (i == 6) {
//              String ymd=line.substring(61, 70);
//              System.out.println("[INFO] - UploadBean: "+ymd);
//                if (!"".equals(ymd))
//                    {
//                        String[] parts = ymd.split("/");
//                        String d = parts[0]; // day
//                        String m = parts[1]; //month
//                        String y = "20"+parts[2]; //year
//                        if (!"dd".equals(d))
//                            {
//                            Float D = Float.parseFloat(d);
//                            int Dd = Math.round(D);
//                            Float M =Float.parseFloat(m);
//                            int Mm = Math.round(M)-1;
//                            Float Y =Float.parseFloat(y);
//                            int Yy = Math.round(Y);
//                            showme = sdf.format(showme(Yy, Mm, Dd).getTime());
//                            ctpdate = showme(Yy, Mm, Dd).getTime();
//                            }
//                    }
//              //ctpdate = showme(2015,8,20).getTime();
//              //Date date = new Date(line.substring(61,70));
//              
//          }
          if (i>10 && i<35) {
             
             //System.out.println(i+" "+line);
              Double ctp = Double.parseDouble(line.substring(87, 92));
             ctparray[i-11] = ctp;
             
          }
          
      }
        //
        java.sql.Date sqldate = new java.sql.Date(ctpdate.getTime());
        int count = 0;
        Connection connCheck = com.opst.MySqlDAOFactory.createConnection();
        String queryCheck = "SELECT count(*) from `ctp_daily` WHERE date = ?";
        PreparedStatement psCheck = connCheck.prepareStatement(queryCheck);
        psCheck.setDate(1, sqldate);
        ResultSet resultSet = psCheck.executeQuery();
        if(resultSet.next()) {
        count = resultSet.getInt(1);
        }
        if (count != 1)
        {
            
        }
            // TODO add data to uploadData()
        
        
        Double sum = 0.0;
        Double[] pod = new Double[4];
              pod[0] = 0.0;
              pod[1] = 0.0;
              pod[2] = 0.0;
              pod[3] = 0.0;
              for (int j=0;j<24;j++)
                {
                  
                  sum = sum+ctparray[j];
                  if (j<5)
                  {
                      pod[0] = pod[0]+ctparray[j];
                  }
                  else if (j>4 && j<8)
                  {
                      pod[1] = pod[1]+ctparray[j];
                  }
                  else if (j>7 && j<16)
                  {
                      pod[2] = pod[2]+ctparray[j];
                  }
                  else if (j>15)
                  {
                      pod[3] = pod[3]+ctparray[j];
                  }
                }
              pod[0] = pod[0]/5;
              pod[1] = pod[1]/3;
              pod[2] = pod[2]/8;
              pod[3] = pod[3]/8;

              Double avg = sum/24;
              Calendar c = Calendar.getInstance();
                c.setTime(ctpdate);
                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                switch (dayOfWeek){
                    case 2: getUdata().getMonday().setDate(ctpdate);
                    getUdata().setMondaypod(pod);
                    getUdata().setMondayavg(avg);
                    getUdata().setMondayctp(ctparray);
                        break;
                    case 3: getUdata().getTuesday().setDate(ctpdate);
                    getUdata().setTuesdaypod(pod);
                    getUdata().setTuesdayavg(avg);
                    getUdata().setTuesdayctp(ctparray);
                        break;
                    case 4: getUdata().getWednesday().setDate(ctpdate);
                    getUdata().setWednesdaypod(pod);
                    getUdata().setWednesdayavg(avg);
                    getUdata().setWednesdayctp(ctparray);
                        break;
                    case 5: getUdata().getThursday().setDate(ctpdate);
                    getUdata().setThursdaypod(pod);
                    getUdata().setThursdayavg(avg);
                    getUdata().setThursdayctp(ctparray);
                        break;
                    case 6: getUdata().getFriday().setDate(ctpdate);
                    getUdata().setFridaypod(pod);
                    getUdata().setFridayavg(avg);
                    getUdata().setFridayctp(ctparray);
                        break;
                    case 7: getUdata().getSaturday().setDate(ctpdate);
                    getUdata().setSaturdaypod(pod);
                    getUdata().setSaturdayavg(avg);
                    getUdata().setSaturdayctp(ctparray);
                        break;
                    case 1: getUdata().getSunday().setDate(ctpdate);
                    getUdata().setSundaypod(pod);
                    getUdata().setSundayavg(avg);
                    getUdata().setSundayctp(ctparray);
                        break;
                }
      // dispose all the resources after using them.
        fileReader.close();
        }
        
    
    public void uploadFile() throws IOException, Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat mysqldate = new SimpleDateFormat("yyyy-MM-dd");
        int j = 0;
        if (getMonday() !=null)
        {
            file[0] = getMonday();
        }
        if (getTuesday() !=null)
        {
            file[1] = getTuesday();
        }
        for (j=0;j<file.length;j++) {
        String filename = getFile()[j].getName();
        try {
            
            byte[] results=new byte[(int)getFile()[j].getSize()];
            InputStream in=getFile()[j].getInputStream();
            in.read(results);
            FileOutputStream os = new FileOutputStream(System.getenv("OPENSHIFT_DATA_DIR") + filename);
            os.write(results);
        os.flush();
        in.close();
        os.close();
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
        
        
        
        //read file
        File text = new File(System.getenv("OPENSHIFT_DATA_DIR") + filename);
        FileReader fileReader = new FileReader(text);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
                        int i =0;
                        String showme = null;                   
                        
                        String ymd= filename;
                
                if (!"".equals(ymd))
                    {
                        //Speed_of_Service_for_26_08_15_Wednesday.prn
                        String[] parts = ymd.split("_");
                        String d = parts[4]; // day
                        String m = parts[5]; //month
                        String y = "20"+parts[6]; //year
                        if (!"dd".equals(d))
                            {
                            Float D = Float.parseFloat(d);
                            int Dd = Math.round(D);
                            Float M =Float.parseFloat(m);
                            int Mm = Math.round(M)-1;
                            Float Y =Float.parseFloat(y);
                            int Yy = Math.round(Y);
                            showme = sdf.format(showme(Yy, Mm, Dd).getTime());
                            ctpdate = showme(Yy, Mm, Dd).getTime();
                            }
                System.out.println("[INFO] - UploadBean: "+showme);
                    }
        while ((line = bufferedReader.readLine()) != null) {
            //System.out.println(line);
            i++;
//            String showme = null;
//            if (i == 6) {
//              String ymd=line.substring(61, 70);
//              System.out.println("[INFO] - UploadBean: "+ymd);
//                if (!"".equals(ymd))
//                    {
//                        String[] parts = ymd.split("/");
//                        String d = parts[0]; // day
//                        String m = parts[1]; //month
//                        String y = "20"+parts[2]; //year
//                        if (!"dd".equals(d))
//                            {
//                            Float D = Float.parseFloat(d);
//                            int Dd = Math.round(D);
//                            Float M =Float.parseFloat(m);
//                            int Mm = Math.round(M)-1;
//                            Float Y =Float.parseFloat(y);
//                            int Yy = Math.round(Y);
//                            showme = sdf.format(showme(Yy, Mm, Dd).getTime());
//                            ctpdate = showme(Yy, Mm, Dd).getTime();
//                            }
//                    }
//              //ctpdate = showme(2015,8,20).getTime();
//              //Date date = new Date(line.substring(61,70));
//              
//          }
          if (i>10 && i<35) {
             
             //System.out.println(i+" "+line);
              Double ctp = Double.parseDouble(line.substring(87, 92));
             ctparray[i-11] = ctp;
             
          }
          
      }
            
            // TODO add data to uploadData()
        
      // dispose all the resources after using them.
        fileReader.close();
        }
			
    
    }
    
    
    public void updateCtp() throws Exception{
        int count;
    //monday
        if (getUdata().getMonday().getDate() != null)
        {
        count = 0;
        java.sql.Date sqlDate = new java.sql.Date(getUdata().getMonday().getDate().getTime());
        
        //check if there is an entry to that date already
        Connection connCheck = com.opst.MySqlDAOFactory.createConnection();
        String queryCheck = "SELECT count(*) from `ctp_daily` WHERE date = ?";
        PreparedStatement psCheck = connCheck.prepareStatement(queryCheck);
        psCheck.setDate(1, sqlDate);
        ResultSet resultSet = psCheck.executeQuery();
        if(resultSet.next()) {
        count = resultSet.getInt(1);
        }
        if (count != 1)
        {
                Connection conn = com.opst.MySqlDAOFactory.createConnection();
                Statement stmt = null;
                ResultSet rs = null;

                stmt = conn.createStatement();
                // the mysql insert statement
              String query = " insert into `ctp_daily` (`date`, `1`, `2`, `3`, `4`, `5`, `6`, `7`, `8`, `9`, `10`, `11`, `12`, `13`, `14`, `15`, `16`, `17`, `18`, `19`, `20`, `21`, `22`, `23`, `24`, `avg`, `updated_by`)"
                + " values (?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

              // create the mysql insert preparedstatement
              PreparedStatement preparedStmt = conn.prepareStatement(query);
              preparedStmt.setDate (1, sqlDate);
              for (int i=0;i<24;i++)
                {
                  preparedStmt.setDouble (i+2, getUdata().getMondayctp()[i]);
                }
              preparedStmt.setDouble(26, getUdata().getMondayavg());
              preparedStmt.setInt(27, 48);
              // execute the preparedstatement
              preparedStmt.execute();
              conn.close();

              //PoD CTP
              Connection conn1 = com.opst.MySqlDAOFactory.createConnection();
                Statement stmt1 = null;
                ResultSet rs1 = null;

                stmt1 = conn1.createStatement();
                // the mysql insert statement
              String query1 = " insert into `ctp_pod` (`date`, `name`, `daypart`, `ctp`, `updated_by`)"
                + " values (?, ?, ?, ?, ?)";

              // create the mysql insert preparedstatement1
              PreparedStatement preparedStmt1 = conn1.prepareStatement(query1);
              preparedStmt1.setDate (1, sqlDate);
              preparedStmt1.setInt(2, getUdata().getMonday().getOvernight());
              preparedStmt1.setInt(3, 1);
              preparedStmt1.setDouble(4, getUdata().getMondaypod()[0]);
              preparedStmt1.setInt(5, 48);
              preparedStmt1.execute();
              
              preparedStmt1 = conn1.prepareStatement(query1);
              preparedStmt1.setDate (1, sqlDate);
              preparedStmt1.setInt(2, getUdata().getMonday().getOpen());
              preparedStmt1.setInt(3, 2);
              preparedStmt1.setDouble(4, getUdata().getMondaypod()[1]);
              preparedStmt1.setInt(5, 48);
              preparedStmt1.execute();
              
              preparedStmt1 = conn1.prepareStatement(query1);
              preparedStmt1.setDate (1, sqlDate);
              preparedStmt1.setInt(2, getUdata().getMonday().getDay());
              preparedStmt1.setInt(3, 3);
              preparedStmt1.setDouble(4, getUdata().getMondaypod()[2]);
              preparedStmt1.setInt(5, 48);
              preparedStmt1.execute();
              
              preparedStmt1 = conn1.prepareStatement(query1);
              preparedStmt1.setDate (1, sqlDate);
              preparedStmt1.setInt(2, getUdata().getMonday().getEvening());
              preparedStmt1.setInt(3, 4);
              preparedStmt1.setDouble(4, getUdata().getMondaypod()[3]);
              preparedStmt1.setInt(5, 48);
              preparedStmt1.execute();
              
              conn1.close();
              
              
              
              System.out.println("[INFO] - UPLOADER: Upload successful for "+sqlDate+".");
        }
        else {
            System.out.println("[INFO] - Entry already exists for "+sqlDate+".");
        }
        }
    //tuesday
        if (getUdata().getTuesday().getDate() != null)
        {
        count = 0;
        java.sql.Date sqlDate = new java.sql.Date(getUdata().getTuesday().getDate().getTime());
        
        //check if there is an entry to that date already
        Connection connCheck = com.opst.MySqlDAOFactory.createConnection();
        String queryCheck = "SELECT count(*) from `ctp_daily` WHERE date = ?";
        PreparedStatement psCheck = connCheck.prepareStatement(queryCheck);
        psCheck.setDate(1, sqlDate);
        ResultSet resultSet = psCheck.executeQuery();
        if(resultSet.next()) {
        count = resultSet.getInt(1);
        }
        if (count != 1)
        {
                Connection conn = com.opst.MySqlDAOFactory.createConnection();
                Statement stmt = null;
                ResultSet rs = null;

                stmt = conn.createStatement();
                // the mysql insert statement
              String query = " insert into `ctp_daily` (`date`, `1`, `2`, `3`, `4`, `5`, `6`, `7`, `8`, `9`, `10`, `11`, `12`, `13`, `14`, `15`, `16`, `17`, `18`, `19`, `20`, `21`, `22`, `23`, `24`, `avg`, `updated_by`)"
                + " values (?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

              // create the mysql insert preparedstatement
              PreparedStatement preparedStmt = conn.prepareStatement(query);
              preparedStmt.setDate (1, sqlDate);
              for (int i=0;i<24;i++)
                {
                  preparedStmt.setDouble (i+2, getUdata().getTuesdayctp()[i]);
                }
              preparedStmt.setDouble(26, getUdata().getTuesdayavg());
              preparedStmt.setInt(27, 48);
              // execute the preparedstatement
              preparedStmt.execute();
              conn.close();

              //PoD CTP
              Connection conn1 = com.opst.MySqlDAOFactory.createConnection();
                Statement stmt1 = null;
                ResultSet rs1 = null;

                stmt1 = conn1.createStatement();
                // the mysql insert statement
              String query1 = " insert into `ctp_pod` (`date`, `name`, `daypart`, `ctp`, `updated_by`)"
                + " values (?, ?, ?, ?, ?)";

              PreparedStatement preparedStmt1 = conn1.prepareStatement(query1);
              preparedStmt1.setDate (1, sqlDate);
              preparedStmt1.setInt(2, getUdata().getTuesday().getOvernight());
              preparedStmt1.setInt(3, 1);
              preparedStmt1.setDouble(4, getUdata().getTuesdaypod()[0]);
              preparedStmt1.setInt(5, 48);
              preparedStmt1.execute();
              
              preparedStmt1 = conn1.prepareStatement(query1);
              preparedStmt1.setDate (1, sqlDate);
              preparedStmt1.setInt(2, getUdata().getTuesday().getOpen());
              preparedStmt1.setInt(3, 2);
              preparedStmt1.setDouble(4, getUdata().getTuesdaypod()[1]);
              preparedStmt1.setInt(5, 48);
              preparedStmt1.execute();
              
              preparedStmt1 = conn1.prepareStatement(query1);
              preparedStmt1.setDate (1, sqlDate);
              preparedStmt1.setInt(2, getUdata().getTuesday().getDay());
              preparedStmt1.setInt(3, 3);
              preparedStmt1.setDouble(4, getUdata().getTuesdaypod()[2]);
              preparedStmt1.setInt(5, 48);
              preparedStmt1.execute();
              
              preparedStmt1 = conn1.prepareStatement(query1);
              preparedStmt1.setDate (1, sqlDate);
              preparedStmt1.setInt(2, getUdata().getTuesday().getEvening());
              preparedStmt1.setInt(3, 4);
              preparedStmt1.setDouble(4, getUdata().getTuesdaypod()[3]);
              preparedStmt1.setInt(5, 48);
              preparedStmt1.execute();
              
              conn1.close();
              
              System.out.println("[INFO] - UPLOADER: Upload successful for "+sqlDate+".");
        }
        else {
            System.out.println("[INFO] - Entry already exists for "+sqlDate+".");
        }
        }
        
        //wednesday
        if (getUdata().getWednesday().getDate() != null)
        {
        count = 0;
        java.sql.Date sqlDate = new java.sql.Date(getUdata().getWednesday().getDate().getTime());
        
        //check if there is an entry to that date already
        Connection connCheck = com.opst.MySqlDAOFactory.createConnection();
        String queryCheck = "SELECT count(*) from `ctp_daily` WHERE date = ?";
        PreparedStatement psCheck = connCheck.prepareStatement(queryCheck);
        psCheck.setDate(1, sqlDate);
        ResultSet resultSet = psCheck.executeQuery();
        if(resultSet.next()) {
        count = resultSet.getInt(1);
        }
        if (count != 1)
        {
                Connection conn = com.opst.MySqlDAOFactory.createConnection();
                Statement stmt = null;
                ResultSet rs = null;

                stmt = conn.createStatement();
                // the mysql insert statement
              String query = " insert into `ctp_daily` (`date`, `1`, `2`, `3`, `4`, `5`, `6`, `7`, `8`, `9`, `10`, `11`, `12`, `13`, `14`, `15`, `16`, `17`, `18`, `19`, `20`, `21`, `22`, `23`, `24`, `avg`, `updated_by`)"
                + " values (?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

              // create the mysql insert preparedstatement
              PreparedStatement preparedStmt = conn.prepareStatement(query);
              preparedStmt.setDate (1, sqlDate);
              for (int i=0;i<24;i++)
                {
                  preparedStmt.setDouble (i+2, getUdata().getWednesdayctp()[i]);
                }
              preparedStmt.setDouble(26, getUdata().getWednesdayavg());
              preparedStmt.setInt(27, 48);
              // execute the preparedstatement
              preparedStmt.execute();
              conn.close();

              //PoD CTP
              Connection conn1 = com.opst.MySqlDAOFactory.createConnection();
                Statement stmt1 = null;
                ResultSet rs1 = null;

                stmt1 = conn1.createStatement();
                // the mysql insert statement
              String query1 = " insert into `ctp_pod` (`date`, `name`, `daypart`, `ctp`, `updated_by`)"
                + " values (?, ?, ?, ?, ?)";

              PreparedStatement preparedStmt1 = conn1.prepareStatement(query1);
              preparedStmt1.setDate (1, sqlDate);
              preparedStmt1.setInt(2, getUdata().getWednesday().getOvernight());
              preparedStmt1.setInt(3, 1);
              preparedStmt1.setDouble(4, getUdata().getWednesdaypod()[0]);
              preparedStmt1.setInt(5, 48);
              preparedStmt1.execute();
              
              preparedStmt1 = conn1.prepareStatement(query1);
              preparedStmt1.setDate (1, sqlDate);
              preparedStmt1.setInt(2, getUdata().getWednesday().getOpen());
              preparedStmt1.setInt(3, 2);
              preparedStmt1.setDouble(4, getUdata().getWednesdaypod()[1]);
              preparedStmt1.setInt(5, 48);
              preparedStmt1.execute();
              
              preparedStmt1 = conn1.prepareStatement(query1);
              preparedStmt1.setDate (1, sqlDate);
              preparedStmt1.setInt(2, getUdata().getWednesday().getDay());
              preparedStmt1.setInt(3, 3);
              preparedStmt1.setDouble(4, getUdata().getWednesdaypod()[2]);
              preparedStmt1.setInt(5, 48);
              preparedStmt1.execute();
              
              preparedStmt1 = conn1.prepareStatement(query1);
              preparedStmt1.setDate (1, sqlDate);
              preparedStmt1.setInt(2, getUdata().getWednesday().getEvening());
              preparedStmt1.setInt(3, 4);
              preparedStmt1.setDouble(4, getUdata().getWednesdaypod()[3]);
              preparedStmt1.setInt(5, 48);
              preparedStmt1.execute();
              
              conn1.close();
              
              System.out.println("[INFO] - UPLOADER: Upload successful for "+sqlDate+".");
        }
        else {
            System.out.println("[INFO] - Entry already exists for "+sqlDate+".");
        }
        }
        
        //thursday
        if (getUdata().getThursday().getDate() != null)
        {
        count = 0;
        java.sql.Date sqlDate = new java.sql.Date(getUdata().getThursday().getDate().getTime());
        
        //check if there is an entry to that date already
        Connection connCheck = com.opst.MySqlDAOFactory.createConnection();
        String queryCheck = "SELECT count(*) from `ctp_daily` WHERE date = ?";
        PreparedStatement psCheck = connCheck.prepareStatement(queryCheck);
        psCheck.setDate(1, sqlDate);
        ResultSet resultSet = psCheck.executeQuery();
        if(resultSet.next()) {
        count = resultSet.getInt(1);
        }
        if (count != 1)
        {
                Connection conn = com.opst.MySqlDAOFactory.createConnection();
                Statement stmt = null;
                ResultSet rs = null;

                stmt = conn.createStatement();
                // the mysql insert statement
              String query = " insert into `ctp_daily` (`date`, `1`, `2`, `3`, `4`, `5`, `6`, `7`, `8`, `9`, `10`, `11`, `12`, `13`, `14`, `15`, `16`, `17`, `18`, `19`, `20`, `21`, `22`, `23`, `24`, `avg`, `updated_by`)"
                + " values (?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

              // create the mysql insert preparedstatement
              PreparedStatement preparedStmt = conn.prepareStatement(query);
              preparedStmt.setDate (1, sqlDate);
              for (int i=0;i<24;i++)
                {
                  preparedStmt.setDouble (i+2, getUdata().getThursdayctp()[i]);
                }
              preparedStmt.setDouble(26, getUdata().getThursdayavg());
              preparedStmt.setInt(27, 48);
              // execute the preparedstatement
              preparedStmt.execute();
              conn.close();

              //PoD CTP
              Connection conn1 = com.opst.MySqlDAOFactory.createConnection();
                Statement stmt1 = null;
                ResultSet rs1 = null;

                stmt1 = conn1.createStatement();
                // the mysql insert statement
              String query1 = " insert into `ctp_pod` (`date`, `name`, `daypart`, `ctp`, `updated_by`)"
                + " values (?, ?, ?, ?, ?)";

              PreparedStatement preparedStmt1 = conn1.prepareStatement(query1);
              preparedStmt1.setDate (1, sqlDate);
              preparedStmt1.setInt(2, getUdata().getThursday().getOvernight());
              preparedStmt1.setInt(3, 1);
              preparedStmt1.setDouble(4, getUdata().getThursdaypod()[0]);
              preparedStmt1.setInt(5, 48);
              preparedStmt1.execute();
              
              preparedStmt1 = conn1.prepareStatement(query1);
              preparedStmt1.setDate (1, sqlDate);
              preparedStmt1.setInt(2, getUdata().getThursday().getOpen());
              preparedStmt1.setInt(3, 2);
              preparedStmt1.setDouble(4, getUdata().getThursdaypod()[1]);
              preparedStmt1.setInt(5, 48);
              preparedStmt1.execute();
              
              preparedStmt1 = conn1.prepareStatement(query1);
              preparedStmt1.setDate (1, sqlDate);
              preparedStmt1.setInt(2, getUdata().getThursday().getDay());
              preparedStmt1.setInt(3, 3);
              preparedStmt1.setDouble(4, getUdata().getThursdaypod()[2]);
              preparedStmt1.setInt(5, 48);
              preparedStmt1.execute();
              
              preparedStmt1 = conn1.prepareStatement(query1);
              preparedStmt1.setDate (1, sqlDate);
              preparedStmt1.setInt(2, getUdata().getThursday().getEvening());
              preparedStmt1.setInt(3, 4);
              preparedStmt1.setDouble(4, getUdata().getThursdaypod()[3]);
              preparedStmt1.setInt(5, 48);
              preparedStmt1.execute();
              
              conn1.close();
              
              System.out.println("[INFO] - UPLOADER: Upload successful for "+sqlDate+".");
        }
        else {
            System.out.println("[INFO] - Entry already exists for "+sqlDate+".");
        }
        }
        
        //friday
        if (getUdata().getFriday().getDate() != null)
        {
        count = 0;
        java.sql.Date sqlDate = new java.sql.Date(getUdata().getFriday().getDate().getTime());
        
        //check if there is an entry to that date already
        Connection connCheck = com.opst.MySqlDAOFactory.createConnection();
        String queryCheck = "SELECT count(*) from `ctp_daily` WHERE date = ?";
        PreparedStatement psCheck = connCheck.prepareStatement(queryCheck);
        psCheck.setDate(1, sqlDate);
        ResultSet resultSet = psCheck.executeQuery();
        if(resultSet.next()) {
        count = resultSet.getInt(1);
        }
        if (count != 1)
        {
                Connection conn = com.opst.MySqlDAOFactory.createConnection();
                Statement stmt = null;
                ResultSet rs = null;

                stmt = conn.createStatement();
                // the mysql insert statement
              String query = " insert into `ctp_daily` (`date`, `1`, `2`, `3`, `4`, `5`, `6`, `7`, `8`, `9`, `10`, `11`, `12`, `13`, `14`, `15`, `16`, `17`, `18`, `19`, `20`, `21`, `22`, `23`, `24`, `avg`, `updated_by`)"
                + " values (?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

              // create the mysql insert preparedstatement
              PreparedStatement preparedStmt = conn.prepareStatement(query);
              preparedStmt.setDate (1, sqlDate);
              for (int i=0;i<24;i++)
                {
                  preparedStmt.setDouble (i+2, getUdata().getFridayctp()[i]);
                }
              preparedStmt.setDouble(26, getUdata().getFridayavg());
              preparedStmt.setInt(27, 48);
              // execute the preparedstatement
              preparedStmt.execute();
              conn.close();

              //PoD CTP
              Connection conn1 = com.opst.MySqlDAOFactory.createConnection();
                Statement stmt1 = null;
                ResultSet rs1 = null;

                stmt1 = conn1.createStatement();
                // the mysql insert statement
              String query1 = " insert into `ctp_pod` (`date`, `name`, `daypart`, `ctp`, `updated_by`)"
                + " values (?, ?, ?, ?, ?)";

              PreparedStatement preparedStmt1 = conn1.prepareStatement(query1);
              preparedStmt1.setDate (1, sqlDate);
              preparedStmt1.setInt(2, getUdata().getFriday().getOvernight());
              preparedStmt1.setInt(3, 1);
              preparedStmt1.setDouble(4, getUdata().getFridaypod()[0]);
              preparedStmt1.setInt(5, 48);
              preparedStmt1.execute();
              
              preparedStmt1 = conn1.prepareStatement(query1);
              preparedStmt1.setDate (1, sqlDate);
              preparedStmt1.setInt(2, getUdata().getFriday().getOpen());
              preparedStmt1.setInt(3, 2);
              preparedStmt1.setDouble(4, getUdata().getFridaypod()[1]);
              preparedStmt1.setInt(5, 48);
              preparedStmt1.execute();
              
              preparedStmt1 = conn1.prepareStatement(query1);
              preparedStmt1.setDate (1, sqlDate);
              preparedStmt1.setInt(2, getUdata().getFriday().getDay());
              preparedStmt1.setInt(3, 3);
              preparedStmt1.setDouble(4, getUdata().getFridaypod()[2]);
              preparedStmt1.setInt(5, 48);
              preparedStmt1.execute();
              
              preparedStmt1 = conn1.prepareStatement(query1);
              preparedStmt1.setDate (1, sqlDate);
              preparedStmt1.setInt(2, getUdata().getFriday().getEvening());
              preparedStmt1.setInt(3, 4);
              preparedStmt1.setDouble(4, getUdata().getFridaypod()[3]);
              preparedStmt1.setInt(5, 48);
              preparedStmt1.execute();
              
              conn1.close();
              
              System.out.println("[INFO] - UPLOADER: Upload successful for "+sqlDate+".");
        }
        else {
            System.out.println("[INFO] - Entry already exists for "+sqlDate+".");
        }
        }
        
        //saturday
        if (getUdata().getSaturday().getDate() != null)
        {
        count = 0;
        java.sql.Date sqlDate = new java.sql.Date(getUdata().getSaturday().getDate().getTime());
        
        //check if there is an entry to that date already
        Connection connCheck = com.opst.MySqlDAOFactory.createConnection();
        String queryCheck = "SELECT count(*) from `ctp_daily` WHERE date = ?";
        PreparedStatement psCheck = connCheck.prepareStatement(queryCheck);
        psCheck.setDate(1, sqlDate);
        ResultSet resultSet = psCheck.executeQuery();
        if(resultSet.next()) {
        count = resultSet.getInt(1);
        }
        if (count != 1)
        {
                Connection conn = com.opst.MySqlDAOFactory.createConnection();
                Statement stmt = null;
                ResultSet rs = null;

                stmt = conn.createStatement();
                // the mysql insert statement
              String query = " insert into `ctp_daily` (`date`, `1`, `2`, `3`, `4`, `5`, `6`, `7`, `8`, `9`, `10`, `11`, `12`, `13`, `14`, `15`, `16`, `17`, `18`, `19`, `20`, `21`, `22`, `23`, `24`, `avg`, `updated_by`)"
                + " values (?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

              // create the mysql insert preparedstatement
              PreparedStatement preparedStmt = conn.prepareStatement(query);
              preparedStmt.setDate (1, sqlDate);
              for (int i=0;i<24;i++)
                {
                  preparedStmt.setDouble (i+2, getUdata().getSaturdayctp()[i]);
                }
              preparedStmt.setDouble(26, getUdata().getSaturdayavg());
              preparedStmt.setInt(27, 48);
              // execute the preparedstatement
              preparedStmt.execute();
              conn.close();

              //PoD CTP
              Connection conn1 = com.opst.MySqlDAOFactory.createConnection();
                Statement stmt1 = null;
                ResultSet rs1 = null;

                stmt1 = conn1.createStatement();
                // the mysql insert statement
              String query1 = " insert into `ctp_pod` (`date`, `name`, `daypart`, `ctp`, `updated_by`)"
                + " values (?, ?, ?, ?, ?)";

              PreparedStatement preparedStmt1 = conn1.prepareStatement(query1);
              preparedStmt1.setDate (1, sqlDate);
              preparedStmt1.setInt(2, getUdata().getSaturday().getOvernight());
              preparedStmt1.setInt(3, 1);
              preparedStmt1.setDouble(4, getUdata().getSaturdaypod()[0]);
              preparedStmt1.setInt(5, 48);
              preparedStmt1.execute();
              
              preparedStmt1 = conn1.prepareStatement(query1);
              preparedStmt1.setDate (1, sqlDate);
              preparedStmt1.setInt(2, getUdata().getSaturday().getOpen());
              preparedStmt1.setInt(3, 2);
              preparedStmt1.setDouble(4, getUdata().getSaturdaypod()[1]);
              preparedStmt1.setInt(5, 48);
              preparedStmt1.execute();
              
              preparedStmt1 = conn1.prepareStatement(query1);
              preparedStmt1.setDate (1, sqlDate);
              preparedStmt1.setInt(2, getUdata().getSaturday().getDay());
              preparedStmt1.setInt(3, 3);
              preparedStmt1.setDouble(4, getUdata().getSaturdaypod()[2]);
              preparedStmt1.setInt(5, 48);
              preparedStmt1.execute();
              
              preparedStmt1 = conn1.prepareStatement(query1);
              preparedStmt1.setDate (1, sqlDate);
              preparedStmt1.setInt(2, getUdata().getSaturday().getEvening());
              preparedStmt1.setInt(3, 4);
              preparedStmt1.setDouble(4, getUdata().getSaturdaypod()[3]);
              preparedStmt1.setInt(5, 48);
              preparedStmt1.execute();
              
              conn1.close();
              
              System.out.println("[INFO] - UPLOADER: Upload successful for "+sqlDate+".");
        }
        else {
            System.out.println("[INFO] - Entry already exists for "+sqlDate+".");
        }
        }
        
        
        //sunday
        if (getUdata().getSunday().getDate() != null)
        {
        count = 0;
        java.sql.Date sqlDate = new java.sql.Date(getUdata().getSunday().getDate().getTime());
        
        //check if there is an entry to that date already
        Connection connCheck = com.opst.MySqlDAOFactory.createConnection();
        String queryCheck = "SELECT count(*) from `ctp_daily` WHERE date = ?";
        PreparedStatement psCheck = connCheck.prepareStatement(queryCheck);
        psCheck.setDate(1, sqlDate);
        ResultSet resultSet = psCheck.executeQuery();
        if(resultSet.next()) {
        count = resultSet.getInt(1);
        }
        if (count != 1)
        {
                Connection conn = com.opst.MySqlDAOFactory.createConnection();
                Statement stmt = null;
                ResultSet rs = null;

                stmt = conn.createStatement();
                // the mysql insert statement
              String query = " insert into `ctp_daily` (`date`, `1`, `2`, `3`, `4`, `5`, `6`, `7`, `8`, `9`, `10`, `11`, `12`, `13`, `14`, `15`, `16`, `17`, `18`, `19`, `20`, `21`, `22`, `23`, `24`, `avg`, `updated_by`)"
                + " values (?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

              // create the mysql insert preparedstatement
              PreparedStatement preparedStmt = conn.prepareStatement(query);
              preparedStmt.setDate (1, sqlDate);
              for (int i=0;i<24;i++)
                {
                  preparedStmt.setDouble (i+2, getUdata().getSundayctp()[i]);
                }
              preparedStmt.setDouble(26, getUdata().getSundayavg());
              preparedStmt.setInt(27, 48);
              // execute the preparedstatement
              preparedStmt.execute();
              conn.close();

              //PoD CTP
              Connection conn1 = com.opst.MySqlDAOFactory.createConnection();
                Statement stmt1 = null;
                ResultSet rs1 = null;

                stmt1 = conn1.createStatement();
                // the mysql insert statement
              String query1 = " insert into `ctp_pod` (`date`, `name`, `daypart`, `ctp`, `updated_by`)"
                + " values (?, ?, ?, ?, ?)";

              PreparedStatement preparedStmt1 = conn1.prepareStatement(query1);
              preparedStmt1.setDate (1, sqlDate);
              preparedStmt1.setInt(2, getUdata().getSunday().getOvernight());
              preparedStmt1.setInt(3, 1);
              preparedStmt1.setDouble(4, getUdata().getSundaypod()[0]);
              preparedStmt1.setInt(5, 48);
              preparedStmt1.execute();
              
              preparedStmt1 = conn1.prepareStatement(query1);
              preparedStmt1.setDate (1, sqlDate);
              preparedStmt1.setInt(2, getUdata().getSunday().getOpen());
              preparedStmt1.setInt(3, 2);
              preparedStmt1.setDouble(4, getUdata().getSundaypod()[1]);
              preparedStmt1.setInt(5, 48);
              preparedStmt1.execute();
              
              preparedStmt1 = conn1.prepareStatement(query1);
              preparedStmt1.setDate (1, sqlDate);
              preparedStmt1.setInt(2, getUdata().getSunday().getDay());
              preparedStmt1.setInt(3, 3);
              preparedStmt1.setDouble(4, getUdata().getSundaypod()[2]);
              preparedStmt1.setInt(5, 48);
              preparedStmt1.execute();
              
              preparedStmt1 = conn1.prepareStatement(query1);
              preparedStmt1.setDate (1, sqlDate);
              preparedStmt1.setInt(2, getUdata().getSunday().getEvening());
              preparedStmt1.setInt(3, 4);
              preparedStmt1.setDouble(4, getUdata().getSundaypod()[3]);
              preparedStmt1.setInt(5, 48);
              preparedStmt1.execute();
              
              conn1.close();
              
              System.out.println("[INFO] - UPLOADER: Upload successful for "+sqlDate+".");
        }
        else {
            System.out.println("[INFO] - Entry already exists for "+sqlDate+".");
        }
        }
        leaderBoardCreatorCTP(getUdata().getMonday().getDate(), getUdata().getSunday().getDate());
    }
    
    private HashMap<Integer,Double> leaderboard = new HashMap<Integer, Double>();
    {
            
    }
    
    public void leaderBoardMe() throws Exception {
        Calendar elso = new GregorianCalendar();
        elso.set(2015, 7, 24);
        Calendar utolso = new GregorianCalendar();
        utolso.set(2015, 7, 30);
        leaderBoardCreatorCTP(elso.getTime(), utolso.getTime());
    }
    
    private HashMap<Integer,Double> PoDLeaderboard = new HashMap<Integer, Double>();
    {
            
    }
    
    private HashMap<Integer,Double[]> managerAndTheirCtp = new HashMap<Integer, Double[]>();
    {
            
    }
    
    public void leaderBoardCreatorCTP(Date elso, Date utolso) throws Exception {
        
        SimpleDateFormat mysqldate = new SimpleDateFormat("yyyy-MM-dd");
        Date first = elso;
        Date oneBefore = adminBean.addDays(first, -1);
        Date last = utolso;
        Date oneAfter = adminBean.addDays(last, 1);
        Connection conn1 = com.opst.MySqlDAOFactory.createConnection();
        Statement stmt1 = conn1.createStatement();
                // get each name
              String query1 = "SELECT DISTINCT name FROM `ctp_pod` WHERE date > '"+mysqldate.format(oneBefore)+"' AND date < '"+mysqldate.format(oneAfter)+"'";
              ResultSet rs = stmt1.executeQuery(query1);
              System.out.println("[INFO] - UploadBean: "+mysqldate.format(first)+" - "+mysqldate.format(last)+"");
              while(rs.next()) {
                  //get all shift run by those names
                  String name = adminBean.managernames.get(rs.getInt(1));
                  System.out.println("[INFO] - UploadBean: "+name+"("+rs.getInt(1)+")");
                  
                  Connection conn2 = com.opst.MySqlDAOFactory.createConnection();
                  Statement stmt2 = conn2.createStatement();
                  String query2 = "SELECT ctp FROM `ctp_pod` WHERE name = "+rs.getInt(1)+" AND date > '"+mysqldate.format(oneBefore)+"' AND date < '"+mysqldate.format(oneAfter)+"'";
                  ResultSet rs2 = stmt2.executeQuery(query2);
                  List<Double> rowValues = new ArrayList<Double>();
                  while(rs2.next())
                  {
                      rowValues.add(rs2.getDouble(1));
                      System.out.println("[INFO] - UploadBean: "+rs2.getDouble(1)+" ");
                  }
                  Double[] ctp = rowValues.toArray(new Double[rowValues.size()]);
                  managerAndTheirCtp.put(rs.getInt(1), ctp);
                  Double sum = 0.0;
                  for (Double c : ctp){
                      if (!Double.isNaN(c)){
                        sum += c;
                      }
                  }
                  Double avg = sum/ctp.length;
                  System.out.println("[INFO] - UploadBean: "+name+"("+rs.getInt(1)+") sum:"+sum+" avg:"+avg);
                  leaderboard.put(rs.getInt(1), avg);
                  conn2.close();
              }
              //creating the weekly average for the leaderboard
              for (Map.Entry<Integer,Double> entry : leaderboard.entrySet()){
                  int lbName = entry.getKey();
                  Double lbCtp = entry.getValue();
                  Calendar cal = Calendar.getInstance();
                    cal.setTime(first);
                    int week = cal.get(Calendar.WEEK_OF_YEAR);
                    int year = cal.get(Calendar.YEAR);
                  
                  query1 = " insert into `ctp_weekly_by_manager` (`name`, `ctp`, `week`, `year`, `updated_by`)"
                + " values (?, ?, ?, ?, ?)";

              PreparedStatement preparedStmt1 = conn1.prepareStatement(query1);
              preparedStmt1.setInt (1, lbName);
              preparedStmt1.setDouble(2, lbCtp);
              preparedStmt1.setInt(3, week);
              preparedStmt1.setInt(4, year);
              preparedStmt1.setInt(5, 48);
              preparedStmt1.execute();
              System.out.println("[INFO] - UploadBean: Week "+week+" - "+adminBean.managernames.get(lbName)+"("+lbName+") - "+lbCtp);
              }
              
        //PoD weekly averages
              for (int dayPart = 1;dayPart<5;dayPart++)
              {
                Connection conn2 = com.opst.MySqlDAOFactory.createConnection();
                    Statement stmt2 = null;
                    
                    String query2 = "SELECT ctp FROM `ctp_pod` WHERE daypart = "+dayPart+" AND date > '"+mysqldate.format(oneBefore)+"' AND date < '"+mysqldate.format(oneAfter)+"'";
                    stmt2 = conn2.createStatement();
                    ResultSet rs2 = stmt2.executeQuery(query2);
                    List<Double> rowValues = new ArrayList<Double>();
                    while(rs2.next())
                    {
                        rowValues.add(rs2.getDouble(1));
                    }
                    Double[] ctp = rowValues.toArray(new Double[rowValues.size()]);
                    Double sum = 0.0;
                    for (Double c : ctp){
                        if (!Double.isNaN(c)){
                        sum += c;                            
                        }
                    }
                    Double avg = sum/ctp.length;
                    PoDLeaderboard.put(dayPart, avg);
                    conn2.close();
                //creating the weekly average for the leaderboard
                    
              }
              for (Map.Entry<Integer,Double> entry : PoDLeaderboard.entrySet()){
                    int lbDayPart = entry.getKey();
                    Double lbCtp = 0.0;
                    if (!Double.isNaN(entry.getValue()))
                    {
                        lbCtp = entry.getValue();
                    }
                    Calendar cal = Calendar.getInstance();
                      cal.setTime(adminBean.addDays(first, 2));
                      int week = cal.get(Calendar.WEEK_OF_YEAR);
                      int year = cal.get(Calendar.YEAR);

                    query1 = " insert into `ctp_weekly_by_pod` (`daypart`, `ctp`, `week`, `year`, `updated_by`)"
                  + " values (?, ?, ?, ?, ?)";

                PreparedStatement preparedStmt1 = conn1.prepareStatement(query1);
                preparedStmt1.setInt (1, lbDayPart);
                preparedStmt1.setDouble(2, lbCtp);
                preparedStmt1.setInt(3, week);
                preparedStmt1.setInt(4, year);
                preparedStmt1.setInt(5, 48);
                preparedStmt1.execute();
                System.out.println("[INFO] - UploadBean: Week "+week+" - "+lbDayPart+" - "+lbCtp);
                }
              
              conn1.close();
    }
    
    
    public UploadBean() {
    }

    /**
     * @return the file
     */
    public Part[] getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(Part[] file) {
        this.file = file;
    }

    /**
     * @return the monday
     */
    public Part getMonday() {
        return monday;
    }

    /**
     * @param monday the monday to set
     */
    public void setMonday(Part monday) {
        this.monday = monday;
    }

    /**
     * @return the tuesday
     */
    public Part getTuesday() {
        return tuesday;
    }

    /**
     * @param tuesday the tuesday to set
     */
    public void setTuesday(Part tuesday) {
        this.tuesday = tuesday;
    }

    /**
     * @return the udata
     */
    public uploadData getUdata() {
        return adminBean.udata;
    }
    
}
