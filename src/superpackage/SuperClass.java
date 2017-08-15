package superpackage;


import configuration.BddInfo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author OBAM
 */
public class SuperClass {
    
    private EntityManagerFactory emf=null;
    EntityManagerFactory managerFactory = null;
    public static  BddInfo bi;
    
    public SuperClass(){
          bi=new BddInfo().getObjet();   
    }
    public EntityManager getEntityManager(){
          Map<String, String> persistenceMap = new HashMap<String, String>();
          persistenceMap.put("javax.persistence.jdbc.url","jdbc:mysql://"+bi.getIp()+":"+bi.getPort()+"/"+bi.getBddName());
          persistenceMap.put("javax.persistence.jdbc.user", bi.getUser());
          persistenceMap.put("javax.persistence.jdbc.password", bi.getPass());
          persistenceMap.put("javax.persistence.jdbc.driver","com.mysql.jdbc.Driver");
          this.emf= Persistence.createEntityManagerFactory("BoutiquePU", persistenceMap);
          return emf.createEntityManager();
    }
    //formatage des dates
    public String getDateFormatAffichage(Date dt){
         SimpleDateFormat df= new SimpleDateFormat("dd/MM/yyyy");
         return dt!=null?df.format(dt):null;
    }
    
    public String formatageMontant(int montant){
         NumberFormat nf=NumberFormat.getInstance(Locale.FRENCH);
         return nf.format(montant);
    }
    public int parseMontantFomatToInt(String montantString) throws ParseException{
         NumberFormat nf=NumberFormat.getInstance(Locale.FRENCH);       
         return nf.parse(montantString).intValue();
    }
      
    public Date convertStringToDate(LocalDate localDate) throws ParseException{
            return  new SimpleDateFormat("yyyy-MM-dd").parse(localDate.toString());    
    }
    //connection a la bdd
     public Connection getConnection() throws SQLException{  
                 String url = "jdbc:mysql://"+bi.getIp()+":"+bi.getPort()+"/"+bi.getBddName();   
                 com.mysql.jdbc.Driver driver = new com.mysql.jdbc.Driver();
                 DriverManager.registerDriver(driver);
                  Connection cnt = DriverManager.getConnection(url,bi.getUser(),bi.getPass());
             return cnt;
    }
    
     //fonctoin de test si un entier est saisi
    public boolean isInteger(String str){     
     try  
     {  
       double d = Double.parseDouble(str);  
     }  
     catch(NumberFormatException nfe)  
     {  
       return false;  
     }  
     return true;  
    }
     
    //mettre alertType en paramètre de la méthode
    public void alert(String title,String message){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(message);
       // alert.setContentText(message);
        alert.showAndWait();
    }
    
    public boolean confirmation(String titre,String message){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(titre);
        alert.setHeaderText(message);
        ButtonType btnOui = new ButtonType("Oui");
        ButtonType btnNon = new ButtonType("Non",ButtonData.CANCEL_CLOSE);
        //alert.setContentText("question");
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(btnNon,btnOui);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() ==btnOui){
            return true;
        } 
        return false;
    }
 
}