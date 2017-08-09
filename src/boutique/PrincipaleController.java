/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boutique;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    private void newForm(String frmName) {
         
        try {
            anchor.getChildren().clear();
            AnchorPane anchorVente = FXMLLoader.load(getClass().getResource(frmName+".fxml"));
            //VenteController.stage=stage;
            anchor.getChildren().add(anchorVente);
        } catch (IOException ex) {
            Logger.getLogger(PrincipaleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void clickVendre(ActionEvent event) {
       newForm("vueVente");
    }
    
    @FXML
    private void clickArticle(ActionEvent event) {
       newForm("/article/vueArticle");
    }
    @FXML
    private void clickFournisseur(ActionEvent event) {
       newForm("/boutique/vueFournisseurs");
    }
    @FXML
    private void clickBonCommande(ActionEvent event) {
       newForm("/boncommande/vueBonCommande");
    }   

    @FXML
    private void clickBtnAjouterCategorie(ActionEvent event) {
        newForm("/categorie/vueCategorie");
    }

    @FXML
    private void btnRptVente(MouseEvent event) {
          newForm("/vente/rapportVente");
    }

    @FXML
    private void clickedReceptionBon(ActionEvent event) {
        newForm("/boncommande/vueReceptionCommande");
    }
      

}
