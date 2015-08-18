package com.opst;
 
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
 
import javax.activation.MimetypesFileTypeMap;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import com.opst.adminBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
 
@WebServlet(name = "Upload")
@MultipartConfig
public class Upload extends HttpServlet {
 
 
  private static final long serialVersionUID = 2857847752169838915L;
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
  
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 
    PrintWriter out = response.getWriter();
    for (Part part : request.getParts()) {
        InputStream is = request.getPart(part.getName()).getInputStream();
        String fileName = getFileName(part);
        FileOutputStream os = new FileOutputStream(System.getenv("OPENSHIFT_DATA_DIR") + fileName);
        byte[] bytes = new byte[BUFFER_LENGTH];
        int read = 0;
        while ((read = is.read(bytes, 0, BUFFER_LENGTH)) != -1) {
            os.write(bytes, 0, read);
        }
        os.flush();
        is.close();
        os.close();
        out.println();
        out.println(fileName + " was uploaded to " + System.getenv("OPENSHIFT_DATA_DIR"));
        out.println();
        out.print("Time | CTP");
        File text = new File(System.getenv("OPENSHIFT_DATA_DIR") + fileName);
        FileInputStream fis = null;
        
    BufferedInputStream bis = null;
    DataInputStream dis = null;
 
    try {
      fis = new FileInputStream(text);
 
      // Here BufferedInputStream is added for fast reading.
      bis = new BufferedInputStream(fis);
      dis = new DataInputStream(bis);
 
      // dis.available() returns 0 if the file does not have more lines.
      int i = 0;
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      SimpleDateFormat mysqldate = new SimpleDateFormat("yyyy-MM-dd");
      while (dis.available() != 0) {
 
      // this statement reads the line from the file and print it to
        // the console.
          i++;
          String line = dis.readLine();
          String showme = null;
          if (i == 6) {
              String ymd=line.substring(61, 70);
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
                            }}
              //Date date = new Date(line.substring(61,70));
              out.println("    "+showme);
              
          }
          if (i>10 && i<35) {
             
             //out.println(i+" "+line);
              Double ctp = Double.parseDouble(line.substring(87, 92));
             out.println(line.substring(1, 6)+"|"+ctp);
             ctparray[i-11] = ctp;
          }
          
      }
      updateCtp( ctparray, ctpdate);
        System.out.println("[INFO] - UPLOADER: Upload successful.");
      // dispose all the resources after using them.
      fis.close();
      bis.close();
      dis.close();
 
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }   catch (Exception ex) {
            Logger.getLogger(Upload.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  }
  public void updateCtp(Double[] ctparray, Date date) throws Exception{
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
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
      preparedStmt1.setDouble(2, overnight);
      preparedStmt1.setDouble(3, open);
      preparedStmt1.setDouble(4, day);
      preparedStmt1.setDouble(5, evening);
      preparedStmt1.setInt(6, 31);
      preparedStmt1.execute();
      conn1.close();
        
    }
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 
    String filePath = request.getRequestURI();
 
    File file = new File(System.getenv("OPENSHIFT_DATA_DIR") + filePath.replace("/upload/",""));
    InputStream input = new FileInputStream(file);
 
    response.setContentLength((int) file.length());
    response.setContentType(new MimetypesFileTypeMap().getContentType(file));
 
    OutputStream output = response.getOutputStream();
    byte[] bytes = new byte[BUFFER_LENGTH];
    int read = 0;
    while ((read = input.read(bytes, 0, BUFFER_LENGTH)) != -1) {
        output.write(bytes, 0, read);
        output.flush();
    }
 
    input.close();
    output.close();
  }
 
  private String getFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
          if (cd.trim().startsWith("filename")) {
            return cd.substring(cd.indexOf('=') + 1).trim()
                    .replace("\"", "");
          }
        }
        return null;
      }

    /**
     * @return the ctparray
     */
    public Double[] getCtparray() {
        return ctparray;
    }
}