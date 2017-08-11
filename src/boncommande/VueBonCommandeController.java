/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boncommande;

import article.Article;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import article.ArticleJpaController;
import detailboncommande.DetailBonCommande;
import detailboncommande.DetailBonCommandeJpaController;
import fournisseur.Fournisseur;
import fournisseur.FournisseurJpaController;
import report.REPORT;
import superpackage.SuperClass;
import tableView.DetailFacture;

/**
 * FXML Controller class
 *
 * @author geres
 */
public class VueBonCommandeController extends SuperClass implements Initializable {      
   
    @FXML
    private TextField txt_prix;
    @FXML
    private TextField txt_quantite;
    @FXML
    private ComboBox<Fournisseur> com_fournisseur;
    @FXML
    private TableView<DetailFacture> tbl_produits_liste;
    @FXML
    private TableColumn<DetailFacture, Integer> cln_index;
    @FXML
    private TableColumn<DetailFacture, String> cln_produit_lib;
    @FXML
    private TableColumn<DetailFacture, Integer> cln_prix;
    @FXML
    private TableColumn<DetailFacture, Integer> cln_quantite;
    @FXML
    private TableColumn<DetailFacture, Integer> cln_montant;   
    @FXML
    private Button btn_sauvegarder;
    @FXML
    private Button btn_ajouter;
    private TableColumn<DetailBonCommande, Date> cln_exp;
    private DatePicker txt_date_now;
    @FXML
    private TableView<Article> tbl_produits;
    @FXML
    private TableColumn<Article, String> cln_lib_produit;
    @FXML
    private TextField txt_recherche;
    @FXML
    private TableColumn<Article, String> cln_produit_stock;
    private DatePicker txt_date_reception;
    @FXML
    private Button btn_retirer;
    @FXML
    private TableColumn<Article,Integer> cln_code;
    @FXML
    private TextField txt_libarticle;
    @FXML
    private TextField txtMontantBon;
    
    private ObservableList<Fournisseur> les_fournisseurs=null;  
    private ObservableList<Article> les_produits=null;
    private final ObservableList<Article> les_produits_Filtre=FXCollections.observableArrayList();
    private final ObservableList<DetailFacture> les_details=FXCollections.observableArrayList();
    private final FournisseurJpaController fournisseurController= new FournisseurJpaController();
    private final ArticleJpaController articleController= new ArticleJpaController();
    private final DetailBonCommandeJpaController detailController= new DetailBonCommandeJpaController();
    private final  BonCommandeJpaController boncommandeController=new BonCommandeJpaController();
    @FXML
    private TextField txt_lib_bonCommande;
    @FXML
    private DatePicker txt_date_bon;
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
      les_fournisseurs=FXCollections.observableArrayList(fournisseurController.findFournisseurEntities());
      com_fournisseur.getItems().addAll(les_fournisseurs);
      les_produits=FXCollections.observableArrayList(articleController.findArticleEntities());
      les_produits_Filtre.addAll(les_produits);
      tbl_produits.setItems(les_produits_Filtre);
      
      //mappage de la tableview article
      cln_lib_produit.setCellValueFactory(new PropertyValueFactory<>("libarticle"));
      cln_produit_stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
      cln_code.setCellValueFactory(new PropertyValueFactory<>("idarticle"));
      
      tbl_produits_liste.setItems(les_details);            
      //les colonnes de la tableview detail          
      cln_prix.setCellValueFactory(new PropertyValueFactory<>("pu"));
      cln_produit_lib.setCellValueFactory(new PropertyValueFactory<>("libArticle"));
      cln_quantite.setCellValueFactory(new PropertyValueFactory<>("qte"));
      cln_montant.setCellValueFactory(new PropertyValueFactory<>("montant"));
        //pour donner un nom par defaut au bon de commande                    
        txt_recherche.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                updateFilteredData();
            }
        });
    }            
    @FXML
    private void btnSauvegarderClicked(MouseEvent event) {
        if(tbl_produits_liste.getItems().isEmpty()){
            this.alert("erreur","Ajouter des articles au bon");
        }
        else{
        try {
            BonCommande bonDeCommande = new BonCommande();
            if(com_fournisseur.selectionModelProperty().getValue().isEmpty()){
                alert(" Valeurs ", "Veuillez choisir un fournisseur", "warning");                
            }else {                  
                bonDeCommande.setFournisseur(com_fournisseur.getSelectionModel().getSelectedItem());
                bonDeCommande.setLibBonCommande(txt_lib_bonCommande.getText());
                bonDeCommande.setDateBonCommande(this.convertStringToDate(txt_date_bon.getValue()));
                bonDeCommande.setReception(false);
                bonDeCommande.setMontant(Integer.parseInt(txtMontantBon.getText()));
                boncommandeController.create(bonDeCommande);
                DetailBonCommande detailbon=new DetailBonCommande();
                for(DetailFacture detail:les_details){                  
                    detailbon.setBonCommande(bonDeCommande);
                    detailbon.setArticle(detail.getArticle());
                    detailbon.setPuachat(detail.getPu());
                    detailbon.setQuantiteDetailBonCommande(detail.getQte());
                    detailController.create(detailbon);
                };                
                if(this.confirmation("affichage état", "BON DE COMMANDE BIEN CREE\nAFFICHAGE DE L'ETAT?")){
                    new REPORT().etatBonCommande(bonDeCommande);
                };
            }
            //impression de la facture
        
            } catch (Exception ex) {
                Logger.getLogger(VueBonCommandeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }  
    
    private void viderChamps(){      
       txt_quantite.clear();
       txt_prix.clear();
       txt_libarticle.clear();
    }
    
     private boolean controleEntrer(){
         boolean ok=false;
            if(com_fournisseur.getSelectionModel().isEmpty())
            alert("erreur","choisissez le fournisseur");
            else if(txt_libarticle.getText().isEmpty())
            alert("erreur","choisissez l\'article");
            else if(txt_prix.getText().isEmpty())
            alert("erreur","taper le prix unitaire du produit");        
            else if(txt_quantite.getText().isEmpty())
            alert("erreur","taper la quatité à commander"); 
            else if(txt_date_bon.getValue()==null)
            alert("erreur","choisissez la date du bon de commander"); 
            else
            ok=true;
         return ok;      
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
    
    public static Date asDate(LocalDate localDate){
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    @FXML
    private void btnRetirer(MouseEvent event) {
        DetailFacture d=null;
        if(!tbl_produits_liste.getSelectionModel().isEmpty()){
            d=tbl_produits_liste.getSelectionModel().getSelectedItem();
            txtMontantBon.setText(String.valueOf(Integer.parseInt(txtMontantBon.getText())-d.getMontant()));
            les_details.remove(d);
        }
    }

    @FXML
    private void btnAjouter(MouseEvent event) {         
        Article a=null;
        if(controleEntrer()){   
            a=tbl_produits.getSelectionModel().getSelectedItem();
            DetailFacture d =new DetailFacture();        
            d.setLibArticle(a.getLibarticle());
            d.setPu(Integer.parseInt(txt_prix.getText()));
            d.setQte(Integer.parseInt(txt_quantite.getText()));
            d.setArticle(a);
            les_details.add(d);
            txtMontantBon.setText(String.valueOf(Integer.parseInt(txtMontantBon.getText())+d.getMontant()));
            viderChamps();
      }
    }

    @FXML
    private void clickTableArticle(MouseEvent event) {
        viderChamps();
        Article a=null;
        if(!tbl_produits.getSelectionModel().isEmpty()){
            a=tbl_produits.getSelectionModel().getSelectedItem();
            txt_libarticle.setText(a.getLibarticle());
        }
    }
}
