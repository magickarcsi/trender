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
  
    public void anyad() throws Exception, IOException {
        InputStream is = getMonday().getInputStream();
        byte[] b = new byte[1024];
        is.read(b);
        String filename = getMonday().getName();
        FileOutputStream os = new FileOutputStream(System.getenv("OPENSHIFT_DATA_DIR") + filename);
        byte[] bytes = new byte[BUFFER_LENGTH];
        int read = 0;
        while ((read = is.read(bytes, 0, BUFFER_LENGTH)) != -1) {
            os.write(bytes, 0, read);
        }
        os.flush();
        is.close();
        os.close();
        //read file
        File text = new File(System.getenv("OPENSHIFT_DATA_DIR") + filename);
        FileReader fileReader = new FileReader(text);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
				stringBuffer.append("\n");
			}
			fileReader.close();
			System.out.println("Contents of file:");
			System.out.println(stringBuffer.toString());
    }
    
    public void uploadFile() throws IOException, Exception {
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
        InputStream is = getFile()[j].getInputStream();
        byte[] b = new byte[1024];
        is.read(b);
        String filename = getFile()[j].getName();
        FileOutputStream os = new FileOutputStream(System.getenv("OPENSHIFT_DATA_DIR") + filename);
        byte[] bytes = new byte[BUFFER_LENGTH];
        int read = 0;
        while ((read = is.read(bytes, 0, BUFFER_LENGTH)) != -1) {
            os.write(bytes, 0, read);
        }
        os.flush();
        is.close();
        os.close();
        //read file
        File text = new File(System.getenv("OPENSHIFT_DATA_DIR") + filename);
        FileReader fileReader = new FileReader(text);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        int i =0;
        while ((line = bufferedReader.readLine()) != null) {   
            //do stuff with lines
          System.out.println(line);
          String showme = null;
          if (i == 6) {
              String ymd=line.substring(61, 70);
              System.out.println("[INFO] - UploadBean: "+ymd);
                /*if (!"".equals(ymd))
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
                    }*/
              ctpdate = showme(2015,8,20).getTime();
              //Date date = new Date(line.substring(61,70));
              
          }
          if (i>10 && i<35) {
             
             //out.println(i+" "+line);
              Double ctp = Double.parseDouble(line.substring(87, 92));
             ctparray[i-11] = ctp;
          }
          
      }
      updateCtp( ctparray, ctpdate);
        
      // dispose all the resources after using them.
        fileReader.close();

          
    }
    }
    
    public void updateCtp(Double[] ctparray, Date date) throws Exception{
        int count = 0;
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        
        //check if there is an entry to that date already
        Connection connCheck = com.opst.MySqlDAOFactory.createConnection();
        final String queryCheck = "SELECT count(*) from `ctp_daily` WHERE date = ?";
        final PreparedStatement psCheck = connCheck.prepareStatement(queryCheck);
        psCheck.setDate(1, sqlDate);
        final ResultSet resultSet = psCheck.executeQuery();
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
              Double sum = 0.0;
              Double overnight = 0.0;
              Double open = 0.0;
              Double day = 0.0;
              Double evening = 0.0;
              for (int i=0;i<24;i++)
                {
                  preparedStmt.setDouble (i+2, ctparray[i]);
                  sum = sum+ctparray[i];
                  if (i<5)
                  {
                      overnight = overnight+ctparray[i];
                  }
                  else if (i>4 && i<8)
                  {
                      open = open+ctparray[i];
                  }
                  else if (i>7 && i<16)
                  {
                      day = day+ctparray[i];
                  }
                  else if (i>15)
                  {
                      evening = evening+ctparray[i];
                  }
                }

              Double avg = sum/24;
              preparedStmt.setDouble(26, avg);
              preparedStmt.setInt(27, 31);
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
              preparedStmt1.setDouble(2, overnight/5);
              preparedStmt1.setDouble(3, open/3);
              preparedStmt1.setDouble(4, day/8);
              preparedStmt1.setDouble(5, evening/8);
              preparedStmt1.setInt(6, 31);
              preparedStmt1.execute();
              conn1.close();
              System.out.println("[INFO] - UPLOADER: Upload successful for "+ctpdate+".");
        }
        else {
            System.out.println("[INFO] - Entry already exists for "+ctpdate+".");
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
    
}
