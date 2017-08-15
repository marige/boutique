/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configuration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import superpackage.SuperClass;

/**
 * FXML Controller class
 *
 * @author OBAM
 */
public class ConnectDbbController extends SuperClass implements Initializable {
    @FXML
    private TextField txtIp;
    @FXML
    private TextField txtBdd;
    @FXML
    private TextField txtPort;
    @FXML
    private TextField txtUser;
    @FXML
    private PasswordField txtPass;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clickAppliquer(MouseEvent event) {
         if(testConnect()){
        File f= new File(System.getenv("APPDATA")+"\\tkt12");
        if(!f.exists())
        f.mkdir();
       // JOptionPane.showMessageDialog(this,System.getenv("APPDATA"));
        ObjectOutputStream oos = null;
        BddInfo obInfo= new BddInfo(txtUser.getText(),txtPass.getText(),txtPort.getText(),txtBdd.getText(),txtIp.getText());
        try {
            oos = new ObjectOutputStream(new FileOutputStream(f+"\\cf.btq"));
            oos.writeObject(obInfo);
            oos.flush();
            new SuperClass();
          alert("bon","Paramètres appliqués\n Relancez l\'application");
        } catch (IOException ex) {
            alert("erreur","erreur d\'accès aux données de configuration");
        }
        finally{
            try {
                oos.close();

            } catch (IOException ex) {
               alert("erreur","erreur d\'accès aux données de configuration");
            }
            }
        }
    }

    @FXML
    private void clickTester(MouseEvent event) {
        if(testConnect())
             alert("error","connexion réussie\n veuillez sauvegader les informations");
        else
             alert("error","Paramètres de connexion incorrecte");
    }
    
     private boolean testConnect(){
        try{
        com.mysql.jdbc.Driver driver;
        Connection cnt=null;

        try {
            driver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(driver);
            cnt =DriverManager.getConnection("jdbc:mysql://"+txtIp.getText()+":"+txtPort.getText()+"/"+txtBdd.getText(),txtUser.getText(),txtPass.getText());

            if(cnt!=null){
               return true;
            }
        } catch (SQLException ex) {
                return false;
        }
        finally{
            try {
                cnt.close();
            } catch (SQLException ex) {              
            }
        }
        }catch(Exception ex){}
        return false;
    }
}
