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
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.animation.TranslateTransitionBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
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
    private Accordion accordion;
    
    superpackage.SuperClass sc= new SuperClass();
    @FXML
    private JFXButton btnVendre1;
    @FXML
    private JFXButton btnAjouterCategorie1;
    
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
        
    double sceneWidth = parentPane.getWidth();
    double msgWidth = node.getLayoutBounds().getWidth();

        KeyValue initKeyValue = new KeyValue(node.translateXProperty(), sceneWidth);
        KeyFrame initFrame = new KeyFrame(Duration.ZERO, initKeyValue);

    KeyValue endKeyValue = new KeyValue(node.translateXProperty(), -1.0
        * msgWidth);
    KeyFrame endFrame = new KeyFrame(Duration.seconds(3), endKeyValue);

        Timeline timeline = new Timeline(initFrame, endFrame);

    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
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
    
       
}
