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
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
public class VueBonCommandeController  implements Initializable {
    
    private ObservableList<Fournisseur> les_fournisseurs=null;  
    private ObservableList<Article> les_produits=null;
    private ObservableList<Article> les_produits_Filtre=FXCollections.observableArrayList();
    private ObservableList<DetailBonCommande> les_details=FXCollections.observableArrayList();
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
    //  tbl_produits.setItems(les_produits);
      les_produits_Filtre.addAll(les_produits);
      tbl_produits.setItems(les_produits_Filtre);
      
      cln_lib_produit.setCellValueFactory(new PropertyValueFactory<>("libarticle"));
      cln_produit_stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
      
      
       //les_details=FXCollections.observableArrayList(detailController.findDetailBonCommandeEntities());
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
            
           
            
       txt_recherche.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                updateFilteredData();
             
            }
        });
    }    
    
    
//pour l'insertion
    @FXML
    private void btnAjouterModifierClicked(MouseEvent event) {
          
        if(!controleEntrer()){
            superClass.alert("Valeurs", "Les champs ne sont pas remplit", "warning");
            
        }
        
        else if(tbl_produits.getSelectionModel().isEmpty()){
            superClass.alert("Produit", "Veuillez choisir un Produit", "warning");
        }
        else{
    
            DetailBonCommande nouveau =new DetailBonCommande();
            
            nouveau.setIdArticle(tbl_produits.getSelectionModel().getSelectedItem());
            nouveau.setPuachat(Integer.parseInt(txt_prix.getText()));
            nouveau.setQuantiteDetailBonCommande(Double.parseDouble(txt_quantite.getText()));
            // nouveau.setDateperemption();
            nouveau.setIdDetailBonCommande(les_details.size()+1);
            les_details.add(nouveau);
            actualiser();
            superClass.alert("Valeurs", "Données Bien enregistrées", "success");

        }
    }

    @FXML
    private void tableClicked(MouseEvent event) {
         DetailBonCommande selectItems;
         selectItems=tbl_produits_liste.getSelectionModel().getSelectedItem();
        
        if(selectItems!=null){
                       
                        txt_prix.setText(""+selectItems.getPuachat());
                        txt_quantite.setText(""+selectItems.getQuantiteDetailBonCommande());
                        txt_recherche.setText(selectItems.getIdArticle().toString());
                       // txt_date.setValue(superClass.LOCAL_DATE(selectItems.getDateperemption().toString()));
                     
        }
    }

    @FXML
    private void btnAnnulerClicked(MouseEvent event) throws IllegalOrphanException, NonexistentEntityException {
        boncommandeController.destroy(Integer.parseInt(txt_idBonCommande.getText()));
    }

    @FXML
    private void btnSauvegarderClicked(MouseEvent event) {
        if(com_fournisseur.selectionModelProperty().getValue().isEmpty()){
            superClass.alert(" Valeurs ", "Veuillez choisir un fournisseur", "warning");
        }else{
        BonCommande bonDeCommande = new BonCommande();
        bonDeCommande.setIdBonCommande(Integer.parseInt(txt_idBonCommande.getText()));
        bonDeCommande.setIdFournisseur(com_fournisseur.getSelectionModel().getSelectedItem());
        bonDeCommande.setLibBonCommande(txt_lib_bonCommande.getText());
       //bonDeCommande.setDateBonCommande(null);
        bonDeCommande.setDetailBonCommandeList(les_details);
        
        
        boncommandeController.create(bonDeCommande);
        
        superClass.alert("Fait", "BON DE COMMANDE BIEN CREE");
        }
    }

    @FXML
    private void btnSupprimerClicked(MouseEvent event) {
    }
    
    
     private void actualiser(){
        
       txt_quantite.clear();
       txt_prix.clear();
      
    }
    
     private boolean controleEntrer(){
        
        return !txt_prix.getText().isEmpty() && !txt_quantite.getText().isEmpty();
    }

     
    private void updateFilteredData() {
         les_produits_Filtre.clear();

        for (Article art : les_produits) {
            if (matchesFilter(art)) {
                les_produits_Filtre.add(art);
                
            }
           
        }
          trier();
    }
    
     private boolean matchesFilter(Article article) {
        String filterString = txt_recherche.getText();
        if (filterString == null || filterString.isEmpty()) {
            // No filter --> Add all.
            return true;
        }
        String lowerCaseFilterString = filterString.toLowerCase();
        if (article.getLibarticle().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        } else if (String.valueOf(article.getIdarticle()).indexOf(lowerCaseFilterString) != -1) {
            return true;
        }
        return false; // Does not match
    }
     
      private void trier() {
        ArrayList<TableColumn<Article, ?>> sortOrder = new ArrayList<>(tbl_produits.getSortOrder());
        tbl_produits.getSortOrder().clear();
        tbl_produits.getSortOrder().addAll(sortOrder);
    }

    @FXML
    private void rechercheTyped(KeyEvent event) {
    }
}
