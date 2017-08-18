/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package societe;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import superpackage.SuperClass;

/**
 * FXML Controller class
 *
 * @author OBAM
 */
public class SocieteController extends SuperClass implements Initializable {
    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtAdresse;
    @FXML
    private TextField txtRegis;
    @FXML
    private TextArea txtEntete;
    @FXML
    private TextArea txtPieds;
    @FXML
    private TextField txtIfu;
    
    Societe sc= new Societe();
    SocieteJpaController sccon= new SocieteJpaController();
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       try{
        sc=sccon.findSociete(1);
        txtAdresse.setText(sc.getAdresse());
        txtEntete.setText(sc.getEntetedocument());
        txtIfu.setText(sc.getIfu());
        txtNom.setText(sc.getLibelle());
        txtPieds.setText(sc.getPiedsdocument());
        txtRegis.setText(sc.getRcm());
       }catch(Exception ex){
           
       }
    }    
    
    @FXML
    private void clickAppliquer(MouseEvent event) {
        sc.setIdsociete(1);
        sc.setEntetedocument(txtEntete.getText());
        sc.setIfu(txtIfu.getText());
        sc.setLibelle(txtNom.getText());
        sc.setPiedsdocument(txtPieds.getText());
        sc.setRcm(txtRegis.getText());
        sc.setAdresse(txtAdresse.getText());
        if(sccon.getSocieteCount()==0)
            sccon.create(sc);
        else 
            try {
                sccon.edit(sc);
        } catch (Exception ex) {
           alert("","erreur de mise à jour :"+ex.getMessage());
        }
        
        alert("notification","mis à jour réussie");
    }
    
}
