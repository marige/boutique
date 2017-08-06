/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boutique;

import entitie.Fournisseur;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import jpaController.FournisseurJpaController;

/**
 * FXML Controller class
 *
 * @author geres
 */
public class VueFournisseursController implements Initializable {
    
    private ObservableList<Fournisseur> les_fournisseurs=null;  
    @FXML
    private TextField txt_recherche;
    @FXML
    private TableView<Fournisseur> tbl_fournisseurs;
    @FXML
    private TableColumn<Fournisseur, String> cln_lib_fournisseurs;
    @FXML
    private TableColumn<Fournisseur, String> cln_ifu;
    @FXML
    private TableColumn<Fournisseur, String> cln_rcm;
    @FXML
    private TableColumn<Fournisseur, Integer> cln_n_commande;
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

    
    
     private final FournisseurJpaController fournisseurController= new FournisseurJpaController();
    @FXML
    private TextField txt_telephone;
    @FXML
    private TableColumn<Fournisseur, Integer> cln_telphone;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        les_fournisseurs=FXCollections.observableArrayList(fournisseurController.findFournisseurEntities());
        
        cln_ifu.setCellValueFactory(new PropertyValueFactory<>("ifuFournisseur"));
            cln_lib_fournisseurs.setCellValueFactory(new PropertyValueFactory<>("libFournisseur"));
            cln_rcm.setCellValueFactory(new PropertyValueFactory<>("rcmFournisseur"));
            cln_telphone.setCellValueFactory(new PropertyValueFactory<>("telFournisseur"));
           // cln_n_commande.setCellValueFactory(new PropertyValueFactory<>("dateperemption"));
        // TODO cln_n_commande, le nombres de commande deja passé, 
            tbl_fournisseurs.setItems(les_fournisseurs);
    }    

    @FXML
    private void rechercheFournisseur(KeyEvent event) {
    }

    @FXML
    private void tableFournisseurClicked(MouseEvent event) {
         Fournisseur selectItems;
         selectItems=tbl_fournisseurs.getSelectionModel().getSelectedItem();
         
          if(selectItems!=null){
         txt_details.setText(selectItems.getDetailsFournisseur());
        txt_ifu.setText(selectItems.getIfuFournisseur());
        txt_lib_fournisseur.setText(selectItems.getLibFournisseur());
        txt_rcm.setText(selectItems.getRcmFournisseur());
        txt_telephone.setText(selectItems.getTelFournisseur());
          }
    }

    @FXML
    private void modifierFournisseurClicked(MouseEvent event) {
    }

    @FXML
    private void nouveauClicked(MouseEvent event) {
        Fournisseur nouveau =new Fournisseur();
        nouveau.setLibFournisseur(txt_lib_fournisseur.getText());
        nouveau.setIfuFournisseur(txt_ifu.getText());
        nouveau.setRcmFournisseur(txt_rcm.getText());
        nouveau.setDetailsFournisseur(txt_details.getText());
        nouveau.setTelFournisseur(txt_telephone.getText());
        
        fournisseurController.create(nouveau);
        les_fournisseurs.add(nouveau);
        EffacerText();
        
    }
    
    private void EffacerText(){
        txt_details.clear();
        txt_ifu.clear();
        txt_lib_fournisseur.clear();
        txt_rcm.clear();
        txt_telephone.clear();
}
    
}
