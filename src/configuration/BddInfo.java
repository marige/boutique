/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 *
 * @author OBAM
 */
public class BddInfo implements Serializable {
    private String user,pass,port,bddname,ip;
    public BddInfo(){}
    public BddInfo(String user,String pass,String port,String bdd,String ip){
        this.bddname=bdd;
        this.pass=pass;
        this.port=port;
        this.user=user;
        this.ip=ip;
    }
    public String getUser(){
    return this.user;
    }
    public String getPass(){
    return this.pass;
    }
    public String getPort(){
    return this.port;
    }
    public String getBddName(){
    return this.bddname;
    }
    public String getIp(){
    return this.ip;
    }
    
    public BddInfo  getObjet(){
      ObjectInputStream ois = null;
        BddInfo bd=null;
        try {
          ois = new ObjectInputStream( new FileInputStream(System.getenv("APPDATA")+"\\tkt12\\cf.btq"));
          bd=(BddInfo)ois.readObject();  
          ois.close();
        } catch (ClassNotFoundException | IOException ex) {
        //    Logger.getLogger(BddInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
        //   Logger.getLogger(BddInfo.class.getName()).log(Level.SEVERE, null, ex);
        
       
      return bd;    
    }
}
