/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autorisation;


import controller.Controler;
import controller.ControlerJpaController;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import superpackage.SuperClass;
import utilisateur.Utilisateur;
import utilisateur.UtilisateurJpaController;
import utilisateur.exceptions.NonexistentEntityException;

/**
 * FXML Controller class
 *
 * @author OBAM
 */
public class AutorisationController extends SuperClass implements Initializable {
    @FXML
    private ComboBox<Utilisateur> cmbUtilisateur;
    @FXML
    private TableView<Controler> tbv_fonction_dispo;
    @FXML
    private TableView<Autorisation> tbv_fonction_user;    
    @FXML
    private TableColumn<Autorisation, Controler> cln_fonctional_user;
    @FXML
    private TableColumn<Controler, String> cln_fonctionalite_exist;   
    @FXML
    private TitledPane panneauUser;
    
    AutorisationJpaController autocon= new AutorisationJpaController();
    ControlerJpaController concon= new ControlerJpaController();
    UtilisateurJpaController userCon= new UtilisateurJpaController();
  
    Autorisation autorisation = new Autorisation();
    Controler con=new Controler();
 
    ObservableList<Controler> listAutorisa=FXCollections.observableArrayList();
    ObservableList<Autorisation> listAutorisaUser=FXCollections.observableArrayList();
    List<Autorisation> ancienneAutorisation=null;
    
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {    
        cln_fonctional_user.setCellValueFactory(new PropertyValueFactory<>("controler"));
        cln_fonctionalite_exist.setCellValueFactory(new PropertyValueFactory<>("description"));
        listAutorisa.addAll(concon.findControlerEntities());
        //pour les autorisations disponibles
        tbv_fonction_dispo.setItems(listAutorisa);
        //pour les  autorisations de l'utilisateur
        tbv_fonction_user.setItems(listAutorisaUser);
        cmbUtilisateur.getItems().addAll(userCon.findUtilisateurEntities());
    }    
     Utilisateur userSelect=null;
    @FXML
    private void clickComboUser(ActionEvent event) {
        listAutorisaUser.clear();
        userSelect=cmbUtilisateur.getValue();
        if(userSelect!=null){
        ancienneAutorisation=autocon.getUserAutorisation(userSelect);
        listAutorisaUser.addAll(ancienneAutorisation);
        panneauUser.setText("Fonctions autorisées à "+userSelect.getNom());
        }
    }
     
    @FXML
    private void clickAjouterFonc(ActionEvent event) {
       Controler c= tbv_fonction_dispo.getSelectionModel().getSelectedItem();
       if(userSelect==null)
           alert("error","Selectionnez l\'utilisateur concerné");
       else if(c==null){
           alert("error","choisissez le droit à ajouter");
       }
       else if(testExisteAutorisation(c))
           alert("error",c.getDescription()+" existe déjà pour "+userSelect.getNom());
       else{
       autorisation.setControler(c);
       autorisation.setUtilisateur(userSelect);
       tbv_fonction_user.getItems().add(autorisation);
      }
    }

    @FXML
    private void clickRetirer(ActionEvent event) {
        if(!tbv_fonction_user.getSelectionModel().isEmpty())
            listAutorisaUser.remove(tbv_fonction_user.getSelectionModel().getSelectedItem());
        else 
            alert("error","choisissez le droit à retirer");
    }

    @FXML
    private void clickValidConfig(ActionEvent event) {
        try {
            autocon.updateAttribut(ancienneAutorisation, listAutorisaUser);
            listAutorisaUser.clear();
            cmbUtilisateur.getSelectionModel().clearSelection();
            alert("notification","Paramètres mis à jour");
            
        } catch (NonexistentEntityException ex) {
          alert("error","problème: "+ex.getMessage());
        }
    }
    //voir si l'attribut est déjà ajouté entre temps
    private boolean testExisteAutorisation(Controler c){
        for(Autorisation a: listAutorisaUser){
            if(a.getControler().equals(c))
                return true;
        }
        return false;
    }
    
}
