/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boutique;

import entities.Fournisseur;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpaController.FournisseurJpaController;

/**
 * FXML Controller class
 *
 * @author geres
 */
public class VueBonCommandeController implements Initializable {
    @FXML
    private ComboBox<?> com_produits;
    @FXML
    private TextField txt_prix;
    @FXML
    private TextField txt_quantite;
    @FXML
    private Button btn_ajouter_modifier;
    @FXML
    private ComboBox<?> com_fournisseur;
    @FXML
    private TableView<?> tbl_produits_liste;
    @FXML
    private TableColumn<?, ?> cln_index;
    @FXML
    private TableColumn<?, ?> cln_produit_lib;
    @FXML
    private TableColumn<?, ?> cln_prix;
    @FXML
    private TableColumn<?, ?> cln_quantite;
    @FXML
    private Button btn_annuler;
    @FXML
    private Button btn_sauvegarder;
    @FXML
    private Button btn_supprimer;

    
    
    private final ObservableList<Fournisseur> fournisseursListe =FXCollections.observableArrayList();
    
    private final EntityManagerFactory emf= new Persistence().createEntityManagerFactory("BoutiquePU");
    private final FournisseurJpaController fournisseurController= new FournisseurJpaController(emf);
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        fournisseursListe.addAll(fournisseurController.findFournisseurEntities());
       com_produits= new ComboBox<>(fournisseursListe);
       com_produits.getSelectionModel().selectFirst();
    }    

    @FXML
    private void btnAjouterModifierClicked(MouseEvent event) {
    }

    @FXML
    private void btnAnnulerClicked(MouseEvent event) {
    }

    @FXML
    private void btnSauvegarderClicked(MouseEvent event) {
    }

    @FXML
    private void btnSupprimerClicked(MouseEvent event) {
    }
    
}
