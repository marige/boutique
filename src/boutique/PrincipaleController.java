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
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Accordion;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import superpackage.SuperClass;

/**
 * FXML Controller class
 *
 * @author OBAM
 */
public class PrincipaleController extends Stage implements Initializable {
    
    @FXML
    private HBox hbox;
    @FXML
    private AnchorPane anchor;
    @FXML
    private Accordion accordion;
    
    superpackage.SuperClass sc= new SuperClass();
    
    @FXML
private Text node; // text to marquee

@FXML
private Pane parentPane; // pane on which text is placed
    
 TranslateTransition transition;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
   /*     
    double sceneWidth = parentPane.getWidth();
    double msgWidth = node.getLayoutBounds().getWidth();

        KeyValue initKeyValue = new KeyValue(node.translateXProperty(), sceneWidth);
        KeyFrame initFrame = new KeyFrame(Duration.ZERO, initKeyValue);

    KeyValue endKeyValue = new KeyValue(node.translateXProperty(), -1.0
        * msgWidth);
    KeyFrame endFrame = new KeyFrame(Duration.seconds(5), endKeyValue);

        Timeline timeline = new Timeline(initFrame, endFrame);

    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();*/
    } 
    
    
    private void newForm(String packag,String frmName) {
         if(sc.isAuthorized(SuperClass.user, frmName)||SuperClass.user.getTypeUser().equalsIgnoreCase("administrateur")||SuperClass.user.getTypeUser().equalsIgnoreCase("superutilisateur"))
         {
             try 
         {
            anchor.getChildren().clear();
            anchor.setPadding(new Insets(3, 3, 3, 3));
            AnchorPane anchorVente = FXMLLoader.load(getClass().getResource("/"+packag+"/"+frmName+".fxml"));
          /*  AnchorPane.setTopAnchor(anchorVente,5.0);
            AnchorPane.setLeftAnchor(anchorVente,5.0);*/
            anchor.getChildren().add(anchorVente);
        } catch (IOException ex) {
            Logger.getLogger(PrincipaleController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }else
             sc.alert("notification","vous n\'avez pas droit à ce formulaire");
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
  
  
    private void rerunAnimation() {
    //transition.stop();
    // if needed set different text on "node"
    //recalculateTransition();
    transition.playFromStart();
}
    
    private void recalculateTransition() {
    transition.setToX(node.getBoundsInLocal().getMaxX() * -1 - 100);
    transition.setFromX(parentPane.widthProperty().get() + 100);

    double distance = parentPane.widthProperty().get() + 2 * node.getBoundsInLocal().getMaxX();
    transition.setDuration(new Duration(distance / 5));
}

    @FXML
    private void clickListeBon(MouseEvent event) {
         newForm("boncommande","listeBoncommande");
    }

    @FXML
    private void clickListeVente(MouseEvent event) {
           newForm("vente","listeVente");
    }

    @FXML
    private void clickBtnAjouterCategorie(MouseEvent event) {
          newForm("categorie","vueCategorie");
    }

    @FXML
    private void clickArticle(MouseEvent event) {
          newForm("article","vueArticle");
    }

    @FXML
    private void clickFournisseur(MouseEvent event) {
         newForm("fournisseur","vueFournisseurs");
    }

    @FXML
    private void clickCreerUtilsateur(MouseEvent event) {
        newForm("utilisateur","nouvelutilisateur");
    }

    @FXML
    private void clickAjouterControler(MouseEvent event) {
         newForm("controller","ajoutcontroler");
    }

    @FXML
    private void clickDroitAcces(MouseEvent event) {
         newForm("autorisation","autorisation");
    }

    @FXML
    private void btnRptVente(MouseEvent event) {
          newForm("vente","rapportVente");
    }
    @FXML
     private void clickBtnSociete(MouseEvent event) {
         newForm("societe","Societe");
      }

    @FXML
    private void clickBonCommande(MouseEvent event) {
         newForm("boncommande","vueBonCommande");
    }

    @FXML
    private void clickVendre(MouseEvent event) {
         newForm("vente","vueVente");
    }

    @FXML
    private void clickVendreRaccourci(ActionEvent event) {
          newForm("vente","vueVente");
    }

    @FXML
    private void clickedReceptionBon(MouseEvent event) {
         newForm("boncommande","vueReceptionCommande");
    }

    @FXML
    private void clickedReceptionBonRaccourci(ActionEvent event) {
         newForm("boncommande","vueReceptionCommande");
    }

    @FXML
    private void clickBonCommandeRaccourci(ActionEvent event) {
           newForm("boncommande","vueBonCommande");
    }

}
