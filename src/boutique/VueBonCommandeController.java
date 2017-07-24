/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boutique;

import entities.Article;
import entities.BonCommande;
import entities.DetailBonCommande;
import entities.Fournisseur;
import entities.Utilisateur;
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
import jpaController.ArticleJpaController;
import jpaController.BonCommandeJpaController;
import jpaController.DetailBonCommandeJpaController;
import jpaController.FournisseurJpaController;

/**
 * FXML Controller class
 *
 * @author geres
 */
public class VueBonCommandeController implements Initializable {
    
    private ObservableList<Fournisseur> les_fournisseurs=null;  
    private ObservableList<Article> les_produits=null;
    private ObservableList<DetailBonCommande> les_details=null;
    private ObservableList<BonCommande> commandes=null;

    SuperClass superClass =new SuperClass();
    @FXML
    private ComboBox<Article> com_produits;
    @FXML
    private TextField txt_prix;
    @FXML
    private TextField txt_quantite;
    @FXML
    private Button btn_ajouter_modifier;
    @FXML
    private ComboBox<Fournisseur> com_fournisseur;
    @FXML
    private TableView<DetailBonCommande> tbl_produits_liste;
    @FXML
    private TableColumn<DetailBonCommande, Integer> cln_index;
    @FXML
    private TableColumn<DetailBonCommande, String> cln_produit_lib;
    @FXML
    private TableColumn<DetailBonCommande, Integer> cln_prix;
    @FXML
    private TableColumn<DetailBonCommande, Integer> cln_quantite;
    @FXML
    private Button btn_annuler;
    @FXML
    private Button btn_sauvegarder;
    @FXML
    private Button btn_supprimer;
    @FXML
    private TextField txt_idBonCommande;

    
    private final EntityManagerFactory emf= new Persistence().createEntityManagerFactory("BoutiquePU");
    
    
    private final FournisseurJpaController fournisseurController= new FournisseurJpaController(emf);
    private final ArticleJpaController articleController= new ArticleJpaController(emf);
    private final DetailBonCommandeJpaController detailController= new DetailBonCommandeJpaController(emf);
    private final  BonCommandeJpaController boncommandeController=new BonCommandeJpaController(emf);
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        commandes=FXCollections.observableArrayList(boncommandeController.findBonCommandeEntities());
       
       les_fournisseurs=FXCollections.observableArrayList(fournisseurController.findFournisseurEntities());
      com_fournisseur.getItems().addAll(les_fournisseurs);
      
      les_produits=FXCollections.observableArrayList(articleController.findArticleEntities());
      com_produits.getItems().addAll(les_produits);
      
      les_details=FXCollections.observableArrayList(detailController.findDetailBonCommandeEntities());
       tbl_produits_liste.getItems().addAll(les_details);
            
            cln_index.setCellValueFactory(new PropertyValueFactory<>("idDetailBonCommande"));
            cln_prix.setCellValueFactory(new PropertyValueFactory<>("coutDetailBonCommande"));
            cln_produit_lib.setCellValueFactory(new PropertyValueFactory<>("libDetailBonCommande"));
       txt_idBonCommande.setText(""+(boncommandeController.getBonCommandeCount()+1));
       txt_idBonCommande.setEditable(false);
     
    }    

    @FXML
    private void btnAjouterModifierClicked(MouseEvent event) {
         
          BonCommande bonCommand= new BonCommande() ;
          Utilisateur utilisateur= new Utilisateur(1);
          
           bonCommand.setFournisseuridFournisseur(com_fournisseur.getSelectionModel().getSelectedItem());
           bonCommand.setIdBonCommande(Integer.parseInt(txt_idBonCommande.getText()));
           bonCommand.setIdUtilisateur(utilisateur);
        if(!controleEntrer()){
            superClass.alert("Valeurs", "Les chanps ne sont pas remplit", "warning");
            
        }else{
            
            
           DetailBonCommande nouveau =new DetailBonCommande();
                        nouveau.setCoutDetailBonCommande(Integer.parseInt(txt_prix.getText()));
                        nouveau.setLibDetailBonCommande(com_produits.getSelectionModel().getSelectedItem().getLibArticle());
                        nouveau.setBonCommandeidBonCommande(bonCommand);
                        nouveau.setIdDetailBonCommande(detailController.getDetailBonCommandeCount()+1);
                        
            les_details.add(nouveau);
            
            superClass.alert("Valeurs", "Données Bien enregistrées", "success");
      
        }
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
    
     private boolean controleEntrer(){
        
        return !txt_prix.getText().isEmpty() && !txt_quantite.getText().isEmpty();
    }
    
}
