/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boutique;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author geres
 */
public class VueFournisseursController implements Initializable {
    @FXML
    private Button btn_fermer;
    @FXML
    private TextField txt_recherche;
    @FXML
    private TableView<?> tbl_fournisseurs;
    @FXML
    private TableColumn<?, ?> cln_ordre;
    @FXML
    private TableColumn<?, ?> cln_lib_fournisseurs;
    @FXML
    private TableColumn<?, ?> cln_ifu;
    @FXML
    private TableColumn<?, ?> cln_rcm;
    @FXML
    private TableColumn<?, ?> cln_n_commande;
    @FXML
    private Button btn_modifier;
    @FXML
    private TextField txt_lib_fournisseur;
    @FXML
    private TextField txt_ifu;
    @FXML
    private TextField txt_rcm;
    @FXML
    private TextArea txt_details;
    @FXML
    private Button btn_nouveau;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void fermerClicked(MouseEvent event) {
    }

    @FXML
    private void rechercheFournisseur(KeyEvent event) {
    }

    @FXML
    private void tableFournisseurClicked(MouseEvent event) {
    }

    @FXML
    private void modifierFournisseurClicked(MouseEvent event) {
    }
    
}
