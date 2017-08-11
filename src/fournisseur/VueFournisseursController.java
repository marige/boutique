/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fournisseur;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import superpackage.SuperClass;


/**
 * FXML Controller class
 *
 * @author geres
 */
public class VueFournisseursController extends SuperClass implements Initializable {
    
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
    @FXML
    private Button btn_ajouter;
    @FXML
    private TextField txt_telephone;
    @FXML
    private TableColumn<Fournisseur, Integer> cln_telphone;

        
    private final FournisseurJpaController fournisseurController= new FournisseurJpaController();
   
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btn_modifier.setDisable(true);
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
    
    Fournisseur amodifier=null;
    @FXML
    private void tableFournisseurClicked(MouseEvent event) {
        amodifier=tbl_fournisseurs.getSelectionModel().getSelectedItem();                 
        if(amodifier!=null){
            txt_details.setText(amodifier.getDetailsFournisseur());
            txt_ifu.setText(amodifier.getIfuFournisseur());
            txt_lib_fournisseur.setText(amodifier.getLibFournisseur());
            txt_rcm.setText(amodifier.getRcmFournisseur());
            txt_telephone.setText(amodifier.getTelFournisseur());
         }
        btn_ajouter.setDisable(true);
        btn_modifier.setDisable(false);
       
    }

    @FXML
    private void modifierFournisseurClicked(MouseEvent event) {
        try {
            if(controle()){
            les_fournisseurs.remove(amodifier);
            amodifier.setLibFournisseur(txt_lib_fournisseur.getText());
            amodifier.setIfuFournisseur(txt_ifu.getText());
            amodifier.setRcmFournisseur(txt_rcm.getText());
            amodifier.setDetailsFournisseur(txt_details.getText());
            amodifier.setTelFournisseur(txt_telephone.getText());
            fournisseurController.edit(amodifier);
            les_fournisseurs.add(amodifier);
            tbl_fournisseurs.refresh();
            this.alert("notification","modification réussie");
            EffacerText();
            }
        } catch (Exception ex) {
            Logger.getLogger(VueFournisseursController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void clickBtnAjouter(MouseEvent event) {
        if(controle()){
            Fournisseur nouveau =new Fournisseur();
            nouveau.setLibFournisseur(txt_lib_fournisseur.getText());
            nouveau.setIfuFournisseur(txt_ifu.getText());
            nouveau.setRcmFournisseur(txt_rcm.getText());
            nouveau.setDetailsFournisseur(txt_details.getText());
            nouveau.setTelFournisseur(txt_telephone.getText());       
            fournisseurController.create(nouveau);
            les_fournisseurs.add(nouveau);
            this.alert("notification","Fournisseur ajouté");
            EffacerText();   
        }    
    }
    
    private void EffacerText(){
        txt_details.clear();
        txt_ifu.clear();
        txt_lib_fournisseur.clear();
        txt_rcm.clear();
        txt_telephone.clear();
    }
    @FXML
    private void clickBtnNouveau(MouseEvent event){
        btn_modifier.setDisable(true);
        btn_ajouter.setDisable(false); 
        EffacerText();
    }
    
    private boolean controle(){
        boolean ok=false;
        if(txt_lib_fournisseur.getText().isEmpty())
            alert("erreur","taper le nom du fournisseur");
        else
            ok=true;
        return ok;
    }
    
}
