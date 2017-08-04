/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boutique;

import entitie.Article;
import entitie.BonCommande;
import entitie.DetailBonCommande;
import entitie.Fournisseur;
import java.net.URL;
import java.time.Instant;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpaController.ArticleJpaController;
import jpaController.BonCommandeJpaController;
import jpaController.DetailBonCommandeJpaController;
import jpaController.FournisseurJpaController;
import jpaController.exceptions.IllegalOrphanException;
import jpaController.exceptions.NonexistentEntityException;
import superpackage.SuperClass;

/**
 * FXML Controller class
 *
 * @author geres
 */
public class VueBonCommandeController implements Initializable {
    
    private ObservableList<Fournisseur> les_fournisseurs=null;  
    private ObservableList<Article> les_produits=null;
    private ObservableList<DetailBonCommande> les_details=null;
    SuperClass superClass =new SuperClass();
    
    @FXML
    private TextField txt_prix;
    @FXML
    private TextField txt_quantite;
    @FXML
    private ComboBox<Fournisseur> com_fournisseur;
    @FXML
    private TableView<DetailBonCommande> tbl_produits_liste;
    @FXML
    private TableColumn<DetailBonCommande, Integer> cln_index;
    @FXML
    private TableColumn<DetailBonCommande, Article> cln_produit_lib;
    @FXML
    private TableColumn<DetailBonCommande, Integer> cln_prix;
    @FXML
    private TableColumn<DetailBonCommande, Integer> cln_quantite;
    @FXML
    private Button btn_sauvegarder;
    @FXML
    private Button btn_supprimer;
    @FXML
    private TextField txt_idBonCommande;
    @FXML
    private TextField txt_lib_bonCommande;


    /**
     * Initializes the controller class.
     */
    
    private final FournisseurJpaController fournisseurController= new FournisseurJpaController();
    private final ArticleJpaController articleController= new ArticleJpaController();
    private final DetailBonCommandeJpaController detailController= new DetailBonCommandeJpaController();
    private final  BonCommandeJpaController boncommandeController=new BonCommandeJpaController();
    @FXML
    private Button btn_ajouter;
    @FXML
    private Button btn_modifier;
    @FXML
    private TableColumn<DetailBonCommande, Date> cln_exp;
    @FXML
    private DatePicker txt_date;

    @FXML
    private DatePicker txt_date_now;
    @FXML
    private TableView<Article> tbl_produits;
    @FXML
    private TableColumn<Article, String> cln_lib_produit;
    @FXML
    private TextField txt_recherche;
    @FXML
    private TableColumn<Article, String> cln_produit_stock;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       les_fournisseurs=FXCollections.observableArrayList(fournisseurController.findFournisseurEntities());
      com_fournisseur.getItems().addAll(les_fournisseurs);
      
      les_produits=FXCollections.observableArrayList(articleController.findArticleEntities());
      tbl_produits.setItems(les_produits);
      cln_lib_produit.setCellValueFactory(new PropertyValueFactory<>("libarticle"));
      cln_produit_stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
      
      
       les_details=FXCollections.observableArrayList(detailController.findDetailBonCommandeEntities());
       tbl_produits_liste.setItems(les_details);
            
       //les colonnes de la table
            cln_index.setCellValueFactory(new PropertyValueFactory<>("idDetailBonCommande"));
            cln_prix.setCellValueFactory(new PropertyValueFactory<>("puachat"));
            cln_produit_lib.setCellValueFactory(new PropertyValueFactory<>("idArticle"));
            cln_quantite.setCellValueFactory(new PropertyValueFactory<>("quantiteDetailBonCommande"));
            cln_exp.setCellValueFactory(new PropertyValueFactory<>("dateperemption"));
            //un id automatiquement génére pour le bon de commande
            txt_idBonCommande.setText(""+(boncommandeController.getBonCommandeCount()+1));
            txt_idBonCommande.setEditable(false);
            
            //pour donner un nom par defaut au bon de commande
            txt_lib_bonCommande.setText("Commande N°"+txt_idBonCommande.getText());
            
            //on cree un bon de commande en instance
          /*  BonCommande bonCommandTemp= new BonCommande();
            bonCommandTemp.setDateBonCommande(Date.from(Instant.EPOCH));
            bonCommandTemp.setIdBonCommande(Integer.parseInt(txt_idBonCommande.getText()));
            bonCommandTemp.setIdFournisseur(new Fournisseur(1));
            
            boncommandeController.create(bonCommandTemp);*/
    }    
    
    
//pour l'insertion
    @FXML
    private void btnAjouterModifierClicked(MouseEvent event) {
          
        if(!controleEntrer()){
            superClass.alert("Valeurs", "Les champs ne sont pas remplit", "warning");
            
        }
        else if(!com_fournisseur.getId().isEmpty()){
            
            
           //Mise a jour de la commande créé temporairement
            BonCommande bonCommand= new BonCommande(Integer.parseInt(txt_idBonCommande.getText()));
            bonCommand.setLibBonCommande(txt_lib_bonCommande.getText());
            bonCommand.setIdFournisseur(com_fournisseur.getValue());
            //bonCommand.setDateBonCommande();
            try {
                boncommandeController.edit(bonCommand);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(VueBonCommandeController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(VueBonCommandeController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
           // Article articleSelectionne =new Article(com_article.getValue().getIdarticle());
            DetailBonCommande nouveau =new DetailBonCommande();
        //    nouveau.setIdArticle(articleSelectionne);
            nouveau.setIdBonCommande(bonCommand);
            nouveau.setLibDetailBonCommande(txt_lib_bonCommande.getText());
            nouveau.setPuachat(Integer.parseInt(txt_prix.getText()));
            nouveau.setQuantiteDetailBonCommande(Double.parseDouble(txt_quantite.getText()));
           // nouveau.setDateperemption();
            
            detailController.create(nouveau);
            actualiser();
            superClass.alert("Valeurs", "Données Bien enregistrées", "success");
      
        }
        else{
            superClass.alert("Valeurs", "Veuillez choisir un fournisseur", "warning");
        }
    }

    @FXML
    private void tableClicked(MouseEvent event) {
         DetailBonCommande selectItems;
         selectItems=tbl_produits_liste.getSelectionModel().getSelectedItem();
        
        if(selectItems!=null){
                       
                        txt_prix.setText(""+selectItems.getPuachat());
                        txt_quantite.setText(""+selectItems.getQuantiteDetailBonCommande());
                       // txt_date.setValue(superClass.LOCAL_DATE(selectItems.getDateperemption().toString()));
                     //   com_article.getSelectionModel().select(selectItems.getIdArticle());
        }
    }

    @FXML
    private void btnAnnulerClicked(MouseEvent event) throws IllegalOrphanException, NonexistentEntityException {
        boncommandeController.destroy(Integer.parseInt(txt_idBonCommande.getText()));
    }

    @FXML
    private void btnSauvegarderClicked(MouseEvent event) {
    }

    @FXML
    private void btnSupprimerClicked(MouseEvent event) {
    }
    
    
     private void actualiser(){
        //les_details.clear();
        //les_details.addAll(detailController.findDetailBonCommandeEntities());
       txt_quantite.clear();
       txt_prix.clear();
      
    }
    
     private boolean controleEntrer(){
        
        return !txt_prix.getText().isEmpty() && !txt_quantite.getText().isEmpty();
    }

    @FXML
    private void rechercheTyped(KeyEvent event) {
    }
}
