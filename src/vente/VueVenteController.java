
package vente;

import superpackage.SuperClass;
import article.Article;
import facture.Facture;
import tableView.DetailFacture;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import article.ArticleJpaController;
import facture.FactureJpaController;
import report.REPORT;

/**
 * FXML Controller class
 *
 * @author OBAM
 */
public class VueVenteController extends SuperClass implements Initializable {
  
    private final ObservableList<Article> artList= FXCollections.observableArrayList();
    private final ObservableList<Article> artListFiltre=FXCollections.observableArrayList();
    
    
    
    private final ObservableList<DetailFacture> factList= FXCollections.observableArrayList();
    ArticleJpaController ac= new ArticleJpaController();
    FactureJpaController factC=new FactureJpaController();
    VenteJpaController venteC= new VenteJpaController();
    Article art=null;
    DetailFacture detF=null;
    
    
    @FXML
    private TableView<DetailFacture> tvDetailFacture;
    @FXML
    private TableColumn<DetailFacture,Integer>cln_code;
    @FXML
    private TableColumn<DetailFacture, String> cln_libarticle;
    @FXML
    private TableColumn<DetailFacture,Integer> cln_qte;
    @FXML
    private TableColumn<DetailFacture,Integer> cln_pu;
    @FXML
    private TableColumn<DetailFacture,Integer> cln_montant;
    
    
    @FXML
    private TableView<Article> tvListProduit;
    @FXML
    private TableColumn<Article, Integer> idarticle;
    @FXML
    private TableColumn<Article, String> libarticle;
    @FXML
    private TableColumn<Article, Integer> stock;
        
    @FXML
    private TextField txtRecherche;
    @FXML
    private DatePicker dtDateFacture;
    @FXML
    private TextField txtLibarticle;
    @FXML
    private TextField txtQte;
    @FXML
    private TextField txtMontantTtc;
    private TextField txtQteCode;
    @FXML
    private TextField txtPu;
    @FXML
    private Button btnAjouterP;
    @FXML
    private TextField txtCode;
    @FXML
    private Button btnRetirer;
    @FXML
    private Button btnFinCommande;
    @FXML
    private TextField txtClient;
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //chargement 
        artList.addAll(ac.findArticleEntities());
        artListFiltre.addAll(artList);
        
                 
        //mapage des champs tableview et object
        idarticle.setCellValueFactory(new PropertyValueFactory<>("idarticle"));
        libarticle.setCellValueFactory(new PropertyValueFactory<>("libarticle"));
        stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        //tvListProduit.setItems(artList);
        tvListProduit.setItems(artListFiltre);
        //mapage des champ de detail 
        cln_code.setCellValueFactory(new PropertyValueFactory<>("id"));
        cln_libarticle.setCellValueFactory(new PropertyValueFactory<>("libArticle"));
        cln_qte.setCellValueFactory(new PropertyValueFactory<>("qte"));
        cln_pu.setCellValueFactory(new PropertyValueFactory<>("pu"));
        cln_montant.setCellValueFactory(new PropertyValueFactory<>("montant")); 
         
           //event d'ecoute de champ de recherche
        txtRecherche.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                updateFilteredData();
             
            }
        });
    }    
     
    private void viderChamps(){
        txtLibarticle.clear();txtPu.clear();txtQte.clear();
    }
    
    @FXML
    private void action(ActionEvent event) {
        
    }

    @FXML
    private void tvClicked(MouseEvent event) {
      art=tvListProduit.getSelectionModel().getSelectedItem();
      if(art!=null){
          txtQte.setText("");
          txtLibarticle.setText(art.getLibarticle());
          txtCode.setText(String.valueOf(art.getIdarticle()));
          txtPu.setText(this.formatageMontant(art.getPrixvente()));
          dtDateFacture.setValue(LocalDate.now());     
          dtDateFacture.setDisable(true);
          
      }     
    }
     DetailFacture detail=null;
     
     @FXML
    private void clickPanier(MouseEvent event){
       if(txtLibarticle.getText().isEmpty())
           alert("notification","choisissez l\'article à vendre");
        else if(!this.isInteger(txtQte.getText()))
           alert("notification","valeur de quantité incorrecte");
        else if(txtPu.getText().trim().equals("0"))
           alert("notification","corrigez dans la base prix vente article: "+txtLibarticle.getText());       
        else{
           try {
               int montant=Integer.parseInt(txtQte.getText())*this.parseMontantFomatToInt(txtPu.getText().trim());
               detail =new DetailFacture(Integer.parseInt(txtCode.getText()),txtLibarticle.getText(),this.parseMontantFomatToInt(txtPu.getText()),Integer.parseInt(txtQte.getText()),montant);
               factList.add(detail);
               txtMontantTtc.setText(this.formatageMontant(this.parseMontantFomatToInt("0"+txtMontantTtc.getText())+detail.getMontant()));
               tvDetailFacture.setItems(factList);
               viderChamps();
           } catch (ParseException ex) {
               Logger.getLogger(VueVenteController.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
    }

    @FXML
    private void clickRetirer(MouseEvent event) {
        if(tvDetailFacture.getSelectionModel().isEmpty())
            alert("notification","choisissez une ligne à supprimer");
        else{
         detF=tvDetailFacture.getSelectionModel().getSelectedItem();
        if(detF!=null){
            factList.remove(detF);   
            txtMontantTtc.setText(String.valueOf(Integer.parseInt("0"+txtMontantTtc.getText())-detF.getMontant()));
        }
        }
    }
  
           
    @FXML
    private void btnFinCommande(MouseEvent event) {
       if(tvDetailFacture.getItems().isEmpty())
           alert("notification","Ajouter les articles  vendre");
       else{
        try {
            Vente v= new Vente();
            Article ad=new Article();
            Facture f = new Facture();
            
            try {
                //enregistrement de la facture
                f.setDateFacture(this.convertStringToDate(dtDateFacture.getValue()));
                f.setClient(txtClient.getText());
                f.setMontant(this.parseMontantFomatToInt(txtMontantTtc.getText()));
                factC.create(f);
            } catch (ParseException ex) {
                Logger.getLogger(VueVenteController.class.getName()).log(Level.SEVERE, null, ex);
            }
            for(DetailFacture d:factList){
                ad=ac.findArticle(d.getId());
                v.setPu(d.getPu());
                v.setQte(d.getQte());
                v.setArticle(ad);
                v.setFacture(f);
                venteC.create(v);
            }
            txtMontantTtc.clear();
            factList.clear();
            txtClient.clear();
            if(this.confirmation("Information", "Vente effectuée, \tAfficher la facture?")){
            //impression de la facture
                new REPORT().etatFacture(f);
             
         }
        } catch (Exception ex) {
            Logger.getLogger(VueVenteController.class.getName()).log(Level.SEVERE, null, ex);
        }
       }
        
       
    }       
     private void updateFilteredData() {
        artListFiltre.clear();

        for (Article art : artList) {
            if (matchesFilter(art)) {
                artListFiltre.add(art);
            }
           
        }

        // Must re-sort table after items changed
        trier();
    }

    /**
     * Returns true if the person matches the current filter. Lower/Upper case
     * is ignored.
     * 
     * @param person
     * @return
     */
     
     //verification de la presence de l'objet dans la le critere de recherche
    private boolean matchesFilter(Article article) {
        String filterString = txtRecherche.getText();
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
    
  //trier dans la tableview
    private void trier() {
        ArrayList<TableColumn<Article, ?>> sortOrder = new ArrayList<>(tvListProduit.getSortOrder());
        tvListProduit.getSortOrder().clear();
        tvListProduit.getSortOrder().addAll(sortOrder);
    }

}

