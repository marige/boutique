/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boutique;
import utilisateur.UtilisateurJpaController;
import utilisateur.Utilisateur;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import superpackage.SuperClass;

/**
 * FXML Controller class
 *
 * @author OBAM
 */
public class AuthentificationController extends SuperClass implements Initializable {
    Stage s=null;
    UtilisateurJpaController uticon = new UtilisateurJpaController();
    ObservableList listUsers=FXCollections.observableArrayList();
    @FXML
    private ComboBox<Utilisateur> cmbCompteUser;
    @FXML
    private PasswordField txtPasse;
    @FXML
    private Button btnEntrer;
    /**
     * Initializes the controller class.
     */
    public static Stage stage;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listUsers.addAll(uticon.findUtilisateurEntities());
        cmbCompteUser.getItems().addAll(listUsers);
       // GlyphsDude.setIcon(btnEntrer, FontAwesomeIcon.LOCK, "4em");
       
    }    

    @FXML
    private void clickEntrer(MouseEvent event) {
        if(txtPasse.getText().isEmpty())
            alert("","taper le mot de passe");
        else if(cmbCompteUser.getValue().getMotpasse().equals(txtPasse.getText())){
            //porter en memoir l'utilisateur pour toutes l'application
            SuperClass.user=cmbCompteUser.getValue();
            try {
              // Stage s = new Stage();
               Parent root = FXMLLoader.load(getClass().getResource("Principale.fxml"));
               Scene scene = new Scene(root);
               stage.setX(0);
               stage.setY(0);
               stage.setScene(scene);
               stage.show();
           } catch (IOException ex) {
               Logger.getLogger(AuthentificationController.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
       else
           alert("erreur","mot de passe incorrect");
    }
    
    @FXML
    private void enterClavier(KeyEvent event) {
        if(event.getCode()==KeyCode.ENTER)
            clickEntrer(null);
    }
    }
