/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilisateur;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import superpackage.SuperClass;

/**
 * FXML Controller class
 *
 * @author OBAM
 */
public class NouvelutilisateurController extends SuperClass implements Initializable {
    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtFontion;
    @FXML
    private ComboBox<Object> cmbTypeUser;
    @FXML
    private PasswordField txtPass;
    @FXML
    private TextField txtLogin;
    @FXML
    private PasswordField txtPassConfirmation;
    @FXML
    private TableView<Utilisateur> tableView;
    @FXML
    private TableColumn<Utilisateur, String> cln_login;
    @FXML
    private TableColumn<Utilisateur, String> cln_nom_prenoms;
    @FXML
    private TableColumn<Utilisateur, String> cln_fonction;
    
    UtilisateurJpaController userCon= new UtilisateurJpaController();
    ObservableList<Utilisateur> listUser=FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      cmbTypeUser.getItems().add("Administrateur");
      cmbTypeUser.getItems().add("Super utilisateur");
      cmbTypeUser.getItems().add("Utilisateur");
     //mapping
      cln_login.setCellValueFactory(new PropertyValueFactory<>("login"));
      cln_nom_prenoms.setCellValueFactory(new PropertyValueFactory<>("nom"));
      cln_fonction.setCellValueFactory(new PropertyValueFactory<>("fonction"));
      
      listUser.addAll(userCon.findUtilisateurEntities());
      tableView.setItems(listUser);
      
      
    }    
    private boolean controle(){
     boolean ok=false;
    
      if(txtNom.getText().isEmpty())
         alert("erreur","taper le nom de l\'utilisateur");
      else if(txtLogin.getText().isEmpty())
         alert("erreur","taper le login");
      else if(cmbTypeUser.getValue()==null)
         alert("erreur","choisissez le type utilisateur");
      else if(txtPass.getText().isEmpty()|| txtPassConfirmation.getText().isEmpty())
         alert("erreur","mot de passe obligatoire");
     else if(!txtPass.getText().equals(txtPassConfirmation.getText()))
         alert("erreur","les mots de passe ne correspondent pas");
     
     else 
         ok=true;
     return ok;
    }
    
    @FXML
    private void clickAjouter(MouseEvent event) {
        if(controle()){
           Utilisateur utilisateur= new Utilisateur();
           utilisateur.setFonction(txtFontion.getText());
           utilisateur.setLogin(txtLogin.getText());
           utilisateur.setMotpasse(txtPass.getText());
           utilisateur.setNom(txtNom.getText());
           utilisateur.setTypeUser(cmbTypeUser.getValue().toString());
           userCon.create(utilisateur);
           listUser.add(utilisateur);
           alert("notification","Utilisateur ajouté");
        }
    }
    
}
