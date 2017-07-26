/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetboutique;

import superpackage.ClassSuper;
import entities.Article;
import entities.Facture;
import entities.Vente;
import entite.tableView.DetailFacture;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import jpaControler.ArticleJpaController;
import jpaControler.FactureJpaController;
import jpaControler.VenteJpaController;

/**
 * FXML Controller class
 *
 * @author OBAM
 */
public class VenteController extends ClassSuper implements Initializable {
  
    private final ObservableList<Article> artList= FXCollections.observableArrayList();
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
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       /* Article ar= new Article();
        ar.setLibarticle("bonbon");
        ar.setStock(10);
        ar.setIdarticle(12);*/
        artList.addAll(ac.findArticleEntities());
        //mapage des champs tableview et object
        idarticle.setCellValueFactory(new PropertyValueFactory<>("idarticle"));
        libarticle.setCellValueFactory(new PropertyValueFactory<>("libarticle"));
        stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        tvListProduit.setItems(artList);
        //mapage des champ de detail 
        cln_code.setCellValueFactory(new PropertyValueFactory<>("id"));
        cln_libarticle.setCellValueFactory(new PropertyValueFactory<>("libArticle"));
        cln_qte.setCellValueFactory(new PropertyValueFactory<>("qte"));
        cln_pu.setCellValueFactory(new PropertyValueFactory<>("pu"));
        cln_montant.setCellValueFactory(new PropertyValueFactory<>("montant"));     
    }    

    @FXML
    private void action(ActionEvent event) {
    }

    @FXML
    private void tvClicked(MouseEvent event) {
      art=tvListProduit.getSelectionModel().getSelectedItem();
      if(art!=null){
          
          txtLibarticle.setText(art.getLibarticle());
          txtCode.setText(String.valueOf(art.getIdarticle()));
          txtPu.setText(String.valueOf(art.getPrixVente()));
          dtDateFacture.setValue(LocalDate.now());               
      }
       
    }
     DetailFacture detail=null;
     @FXML
    private void clickPanier(MouseEvent event){
      int montant=Integer.parseInt(txtQte.getText())*Integer.parseInt(txtPu.getText());
      detail =new DetailFacture(Integer.parseInt(txtCode.getText()),txtLibarticle.getText(),Integer.parseInt(txtPu.getText()),Integer.parseInt(txtQte.getText()),montant);
      factList.add(detail);
      txtMontantTtc.setText(String.valueOf(Integer.parseInt("0"+txtMontantTtc.getText())+detail.getMontant()));
      
      tvDetailFacture.setItems(factList);
    }

    @FXML
    private void clickRetirer(MouseEvent event) {
         detF=tvDetailFacture.getSelectionModel().getSelectedItem();
        if(detF!=null){
            factList.remove(detF);   
            txtMontantTtc.setText(String.valueOf(Integer.parseInt("0"+txtMontantTtc.getText())-detF.getMontant()));
        }
    }

    @FXML
    private void btnFinCommande(MouseEvent event) {
        Vente v= new Vente();
        Article ad=new Article();
        Facture f = new Facture();
        
        try {
            //enregistrement de la facture
            f.setDateFacture(this.convertStringToDate(dtDateFacture.getValue()));
            factC.create(f);
        } catch (ParseException ex) {
            Logger.getLogger(VenteController.class.getName()).log(Level.SEVERE, null, ex);
        }
     
        System.out.println("nombre element est "+factList.size());
        for(DetailFacture d:factList){
            ad=ac.findArticle(d.getId());
            v.setPu(d.getPu());
            v.setQte(d.getQte());
            v.setArticle(ad);
            v.setFacture(f);       
            venteC.create(v);
        }
       
        
       
    }
    
}
