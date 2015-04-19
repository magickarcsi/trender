package com.opst;

import java.io.File;
import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import com.opst.CtpBean;
import com.opst.KvsBean;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.net.HttpURLConnection;
import java.net.URL;

@ManagedBean(name="xmlbean")
@SessionScoped

public class XMLHandler {
    
    /**
     * @return the kvsList
     */
    public ArrayList<KvsBean.Kvs> getKvsList() {
        return kvsList;
    }

    /**
     * @return the ctpList
     */
    public ArrayList<CtpBean.Ctp> getCtpList() {
        return ctpList;
    }
        private String week;
        private String date1;
        private String date2;
        private String muti;
        private KvsBean.Kvs initialkvs = new KvsBean.Kvs(0,"0",new BigDecimal("0"),new BigDecimal("0"),0);
        private CtpBean.Ctp initialctp = new CtpBean.Ctp(0,"0",new BigDecimal("0"),new BigDecimal("0"),0);
        private ArrayList<KvsBean.Kvs> kvsList = new ArrayList<KvsBean.Kvs>(Arrays.asList(initialkvs
        ));
        private ArrayList<CtpBean.Ctp> ctpList= new ArrayList<CtpBean.Ctp>(Arrays.asList(initialctp
        ));
        
        public void kvsnodes(String w) throws ParserConfigurationException, SAXException, IOException{
            
            //File fXmlFile = new File("http://breakme-mcdst.rhcloud.com/storekvs.xml");
             DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
             DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
             Document doc = dBuilder.parse(new URL("http://trender-mcdst.rhcloud.com/storekvs.xml").openStream());
             doc.getDocumentElement().normalize();
            NodeList leaderboarder = doc.getElementsByTagName("leaderboard");
            Element el = (Element)leaderboarder.item(0);
            //NodeList kvs = el.getChildNodes();
            //setMuti(kvs.item(1).getTextContent());
            if (el.getAttribute("week").equals(w)) {
                NodeList kvs = el.getElementsByTagName("kvs");
            int count_a = kvs.getLength();
            for (int i=0;i<count_a;i++){
                String ntidta = kvs.item(i).getTextContent();
                String[] parts = ntidta.split("\\s+");
                if (parts.length>1){
                String n = parts[1]; 
                BigDecimal ti = new BigDecimal(parts[2]); 
                BigDecimal d = new BigDecimal(parts[3]);
                int ta;
                ta = Integer.parseInt(parts[4]);
                KvsBean.Kvs kvselem = new KvsBean.Kvs((i+1),n,ti,d,ta);
                if (i==0) {
                  getKvsList().clear();  
                }
                getKvsList().add(kvselem);
            }
            }
            }
        }

        public void ctpnodes(String w) throws ParserConfigurationException, SAXException, IOException{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
             DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
             Document doc = dBuilder.parse(new URL("http://trender-mcdst.rhcloud.com/storectp.xml").openStream());
             doc.getDocumentElement().normalize();
            NodeList leaderboarder = doc.getElementsByTagName("leaderboard");
            Element el = (Element)leaderboarder.item(0);
            //NodeList kvs = el.getChildNodes();
            //setMuti(kvs.item(1).getTextContent());
            if (el.getAttribute("week").equals(w)) {
                NodeList ctp = el.getElementsByTagName("ctp");
            int count_b = ctp.getLength();
            for (int i=0;i<count_b;i++){
                String ntidta = ctp.item(i).getTextContent();
                String[] parts = ntidta.split("\\s+");
                if (parts.length>1){
                String n = parts[1]; 
                BigDecimal ti = new BigDecimal(parts[2]); 
                BigDecimal d = new BigDecimal(parts[3]);
                int ta;
                ta = Integer.parseInt(parts[4]);
                CtpBean.Ctp ctpelem = new CtpBean.Ctp((i+1),n,ti,d,ta);
                if (i==0) {
                  getCtpList().clear();  
                }
                getCtpList().add(ctpelem);
            }
            }
            }
        }

        /**
     * @return the week
     */
    public String getWeek() {
        return week;
    }

    /**
     * @return the date1
     */
    public String getDate1() {
        return date1;
    }

    /**
     * @return the date2
     */
    public String getDate2() {
        return date2;
    }

    /**
     * @param week the week to set
     */
    public void setWeek(String week) {
        this.week = week;
    }

    /**
     * @param date1 the date1 to set
     */
    public void setDate1(String date1) {
        this.date1 = date1;
    }

    /**
     * @param date2 the date2 to set
     */
    public void setDate2(String date2) {
        this.date2 = date2;
    }

    /**
     * @return the muti
     */
    public String getMuti() {
        return muti;
    }

    /**
     * @param muti the muti to set
     */
    public void setMuti(String muti) {
        this.muti = muti;
    }
}
