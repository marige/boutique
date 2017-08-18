/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boutique;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import superpackage.SuperClass;

/**
 * FXML Controller class
 *
 * @author OBAM
 */
public class PrincipaleController extends Stage implements Initializable {
    
    @FXML
    private Button btnVendre;
    @FXML
    private HBox hbox;
    @FXML
    private AnchorPane anchor;
    @FXML
    private Button btnArticleAjouter;
    @FXML
    private Button btnBonCommande;
    @FXML
    private Button btnFournisseur;
    @FXML
    private Button btnAjouterCategorie;
    @FXML
    private Button btnRptVente;
    @FXML
    private Button btnReceptionBonCommande;
    @FXML
    private JFXButton Utilisateurs;
    @FXML
    private Accordion accordion;
    
    superpackage.SuperClass sc= new SuperClass();
    @FXML
    private JFXButton btnVendre1;
    @FXML
    private JFXButton btnAjouterCategorie1;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      //  accordion.setVisible(false);
    } 
    
    
    private void newForm(String packag,String frmName) {
         if(sc.isAuthorized(SuperClass.user, frmName)||SuperClass.user.getTypeUser().equalsIgnoreCase("administrateur")||SuperClass.user.getTypeUser().equalsIgnoreCase("superutilisateur"))
         {try {
            anchor.getChildren().clear();
            AnchorPane anchorVente = FXMLLoader.load(getClass().getResource("/"+packag+"/"+frmName+".fxml"));
            //VenteController.stage=stage;
            anchor.getChildren().add(anchorVente);
        } catch (IOException ex) {
            Logger.getLogger(PrincipaleController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }else
             sc.alert("notification","vous n\'avez pas droit à ce formulaire");
    }
    @FXML
    private void clickVendre(ActionEvent event) {
       newForm("vente","vueVente");
    }
    
    @FXML
    private void clickArticle(ActionEvent event) {
       newForm("article","vueArticle");
    }
    @FXML
    private void clickFournisseur(ActionEvent event) {
       newForm("fournisseur","vueFournisseurs");
    }
    @FXML
    private void clickBonCommande(ActionEvent event) {
       newForm("boncommande","vueBonCommande");
    }   

    @FXML
    private void clickBtnAjouterCategorie(ActionEvent event) {
        newForm("categorie","vueCategorie");
    }

    @FXML
    private void btnRptVente(MouseEvent event) {
          newForm("vente","rapportVente");
    }

    @FXML
    private void clickedReceptionBon(ActionEvent event) {
        newForm("boncommande","vueReceptionCommande");
    }
    @FXML
    private void clickInfoSociete(MouseEvent event) {
        newForm("societe","Societe");
    }
    @FXML
    private void clickListeVente(ActionEvent event) {
           newForm("vente","listeVente");
    }
 

    @FXML
    private void sortieSouri(MouseEvent event) {
      //  System.out.println("sortie de la zone");
       accordion.setVisible(false);
    }

    @FXML
    private void entreSouriPrincipale(MouseEvent event) {
         accordion.setVisible(true);
    }
    @FXML
    private void clickCreerUtilsateur(ActionEvent event) {
            newForm("utilisateur","nouvelutilisateur");
    }

    @FXML
    private void clickUtilisateur(MouseEvent event) {
         
    }

    @FXML
    private void clickAjouterControler(ActionEvent event) {
         newForm("controller","ajoutcontroler");
    }
    @FXML
    private void clickDroitAcces(ActionEvent event){
         newForm("autorisation","autorisation");
    }
    @FXML
    private void clickListeBon(ActionEvent event){
        newForm("boncommande","listeBoncommande");
    }
  
    
    
       
}
