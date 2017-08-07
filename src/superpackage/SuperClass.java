package superpackage;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
    
    public EntityManager getEntityManager(){
          this.emf=Persistence.createEntityManagerFactory("BoutiquePU");
          return emf.createEntityManager();
    }
    //formatage des dates
    public String getDateFormatAffichage(Date dt){
         SimpleDateFormat df= new SimpleDateFormat("dd/MM/yyyy");
         return dt!=null?df.format(dt):null;
    }
    
    
    public Date convertStringToDate(LocalDate localDate) throws ParseException{
            return  new SimpleDateFormat("yyyy-MM-dd").parse(localDate.toString());    
    }
    //connection a la bdd
     public Connection getConnection() throws SQLException{  
                 String url = "jdbc:mysql://localhost:3306/boutique";   
                 com.mysql.jdbc.Driver driver = new com.mysql.jdbc.Driver();
                 DriverManager.registerDriver(driver);
                  Connection cnt = DriverManager.getConnection(url,"root","mario");
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
     
     
     
   /* public  void alert(String title, String message ){
        Label label= new Label();
        Button closeButton= new Button("Femer");
        Stage window= new Stage();
        window.initModality(Modality.APPLICATION_MODAL);// fenetre modale
        window.setTitle(title);
        
        label.setText(message);
        closeButton.setOnAction(e ->window.close());
        
        HBox layoutButton   = new HBox(5); 
        HBox layoutText     = new HBox(5);
        VBox content        =new VBox(10);
        
        layoutButton.getChildren().addAll(closeButton);
        layoutText.getChildren().addAll(label);

        layoutButton.setAlignment(Pos.BOTTOM_RIGHT);
        layoutText.setAlignment(Pos.CENTER);
        
        content.getChildren().addAll(layoutText,layoutButton);
        
        Scene scene =new Scene(content, 300, 250);
         window.setScene(scene);
         window.showAndWait();
    }*/
    //mettre alertType en paramètre de la méthode
    public void alert(String title,String message){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(message);
       // alert.setContentText(message);
        alert.showAndWait();
    }
    
     public  void alert(String title, String message, String type ){
         
         if(type.contentEquals("danger"))
             title="DANGER: "+title;
         if(type.contentEquals("warning"))
             title="ATTENTION: "+title;
         if(type.contentEquals("success"))
             title="Validation: "+title;
          if(type.contentEquals("info")){
             alert(title,message);
             return;
          }
         
          Label label= new Label();
        Button closeButton= new Button("Femer");  
        Button openButton= new Button("Ouvrir");

        Stage window= new Stage();
        window.initModality(Modality.APPLICATION_MODAL);// fenetre modale
        window.setTitle(title);
        
        label.setText(message);
        closeButton.setOnAction(e ->window.close());
        
        HBox layoutButton   = new HBox(5); 
        HBox layoutText     = new HBox(10);
        VBox content        =new VBox(10);
        
        layoutButton.getChildren().addAll(closeButton);
        layoutText.getChildren().addAll(label);

        layoutButton.setAlignment(Pos.BOTTOM_RIGHT);
        layoutText.setAlignment(Pos.CENTER);
        openButton.setAlignment(Pos.BOTTOM_LEFT);
        content.getChildren().addAll(layoutText,layoutButton,openButton);
        
        Scene scene =new Scene(content, 300, 250);
         window.setScene(scene);
         window.showAndWait();         
                          
         
        
     }
}