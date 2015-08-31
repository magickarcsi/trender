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
        while ((line = bufferedReader.readLine()) != null) {
            //System.out.println(line);
            i++;
            String showme = null;
            if (i == 6) {
              String ymd=line.substring(61, 70);
              System.out.println("[INFO] - UploadBean: "+ymd);
                if (!"".equals(ymd))
                    {
                        String[] parts = ymd.split("/");
                        String d = parts[0]; // day
                        String m = parts[1]; //month
                        String y = "20"+parts[2]; //year
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
                    }
              //ctpdate = showme(2015,8,20).getTime();
              //Date date = new Date(line.substring(61,70));
              
          }
          if (i>10 && i<35) {
             
             //System.out.println(i+" "+line);
              Double ctp = Double.parseDouble(line.substring(87, 92));
             ctparray[i-11] = ctp;
             
          }
          
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
                        break;
                    case 3: getUdata().getTuesday().setDate(ctpdate);
                    getUdata().setTuesdaypod(pod);
                    getUdata().setTuesdayavg(avg);
                        break;
                    case 4: getUdata().getWednesday().setDate(ctpdate);
                    getUdata().setWednesdaypod(pod);
                    getUdata().setWednesdayavg(avg);
                        break;
                    case 5: getUdata().getThursday().setDate(ctpdate);
                    getUdata().setThursdaypod(pod);
                    getUdata().setThursdayavg(avg);
                        break;
                    case 6: getUdata().getFriday().setDate(ctpdate);
                    getUdata().setFridaypod(pod);
                    getUdata().setFridayavg(avg);
                        break;
                    case 7: getUdata().getSaturday().setDate(ctpdate);
                    getUdata().setSaturdaypod(pod);
                    getUdata().setSaturdayavg(avg);
                        break;
                    case 1: getUdata().getSunday().setDate(ctpdate);
                    getUdata().setSundaypod(pod);
                    getUdata().setSundayavg(avg);
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
        while ((line = bufferedReader.readLine()) != null) {
            //System.out.println(line);
            i++;
            String showme = null;
            if (i == 6) {
              String ymd=line.substring(61, 70);
              System.out.println("[INFO] - UploadBean: "+ymd);
                if (!"".equals(ymd))
                    {
                        String[] parts = ymd.split("/");
                        String d = parts[0]; // day
                        String m = parts[1]; //month
                        String y = "20"+parts[2]; //year
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
                    }
              //ctpdate = showme(2015,8,20).getTime();
              //Date date = new Date(line.substring(61,70));
              
          }
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
                  preparedStmt.setDouble (i+2, ctparray[i]);
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
              String query1 = " insert into `ctp_pod` (`date`, `1`, `2`, `3`, `4`, `updated_by`)"
                + " values (?, ?, ?, ?, ?, ?)";

              // create the mysql insert preparedstatement1
              PreparedStatement preparedStmt1 = conn1.prepareStatement(query1);
              preparedStmt1.setDate (1, sqlDate);
              preparedStmt1.setDouble(2, getUdata().getMondaypod()[0]);
              preparedStmt1.setDouble(3, getUdata().getMondaypod()[1]);
              preparedStmt1.setDouble(4, getUdata().getMondaypod()[2]);
              preparedStmt1.setDouble(5, getUdata().getMondaypod()[3]);
              preparedStmt1.setInt(6, 48);
              preparedStmt1.execute();
              conn1.close();
              
//              //PoD Manager CTP
//              Connection conn2 = com.opst.MySqlDAOFactory.createConnection();
//                Statement stmt2 = null;
//                ResultSet rs2 = null;
//
//                stmt2 = conn2.createStatement();
//                // the mysql insert statement
//              String query2 = " insert into `manager_pod` (`date`, `1`, `2`, `3`, `4`, `updated_by`)"
//                + " values (?, ?, ?, ?, ?, ?)";
//
//              // create the mysql insert preparedstatement1
//              PreparedStatement preparedStmt2 = conn2.prepareStatement(query2);
//              preparedStmt2.setDate (1, sqlDate);
//              preparedStmt2.setInt(2, getUdata().getMonday().getOvernight());
//              preparedStmt2.setInt(3, getUdata().getMonday().getOpen());
//              preparedStmt2.setInt(4, getUdata().getMonday().getDay());
//              preparedStmt2.setInt(5, getUdata().getMonday().getEvening());
//              preparedStmt2.setInt(6, 48);
//              preparedStmt2.execute();
//              conn2.close();
//              
              System.out.println("[INFO] - UPLOADER: Upload successful for "+sqlDate+".");
        }
        else {
            System.out.println("[INFO] - Entry already exists for "+sqlDate+".");
        }
    //tuesday
        count = 0;
        sqlDate = new java.sql.Date(getUdata().getTuesday().getDate().getTime());
        
        //check if there is an entry to that date already
        connCheck = com.opst.MySqlDAOFactory.createConnection();
        queryCheck = "SELECT count(*) from `ctp_daily` WHERE date = ?";
        psCheck = connCheck.prepareStatement(queryCheck);
        psCheck.setDate(1, sqlDate);
        resultSet = psCheck.executeQuery();
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
                  preparedStmt.setDouble (i+2, ctparray[i]);
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
              String query1 = " insert into `ctp_pod` (`date`, `1`, `2`, `3`, `4`, `updated_by`)"
                + " values (?, ?, ?, ?, ?, ?)";

              // create the mysql insert preparedstatement1
              PreparedStatement preparedStmt1 = conn1.prepareStatement(query1);
              preparedStmt1.setDate (1, sqlDate);
              preparedStmt1.setDouble(2, getUdata().getTuesdaypod()[0]);
              preparedStmt1.setDouble(3, getUdata().getTuesdaypod()[1]);
              preparedStmt1.setDouble(4, getUdata().getTuesdaypod()[2]);
              preparedStmt1.setDouble(5, getUdata().getTuesdaypod()[3]);
              preparedStmt1.setInt(6, 48);
              preparedStmt1.execute();
              conn1.close();
              System.out.println("[INFO] - UPLOADER: Upload successful for "+sqlDate+".");
        }
        else {
            System.out.println("[INFO] - Entry already exists for "+sqlDate+".");
        }
        
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
