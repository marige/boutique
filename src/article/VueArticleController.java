
package article;

import categorie.Categorie;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import categorie.CategorieJpaController;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.MouseEvent;
import superpackage.SuperClass;

/**
 * FXML Controller class
 *
 * @author OBAM
 */

public class VueArticleController extends SuperClass implements Initializable {
    private final ObservableList<Article> article=FXCollections.observableArrayList();
    private ObservableList<Categorie> categorie=null;
    CategorieJpaController cat=new CategorieJpaController();
    ArticleJpaController artcon=new ArticleJpaController();
    Article a= new Article();
    
    @FXML
    private TableView<Article> table;
    @FXML
    private TableColumn<Article, String> cln_libarticle;
    @FXML
    private TableColumn<Article,Integer> cln_idarticle;
    @FXML
    private TableColumn<Article, Integer> cln_stock;
    @FXML
    private TableColumn<Article, Integer> cln_prixvente;
    @FXML
    private TableColumn<Article, Integer> cln_stocksecurite;   
    @FXML
    private TextField txtLibarticle;
    @FXML
    private TextField txtStock; 
    @FXML
    private TextField txtPrixvente;
    @FXML
    private ComboBox<Categorie> cmbCategorie;
    @FXML
    private AnchorPane frmArticle;
    @FXML
    private TextField txtStockSecurite;
    @FXML
    private Button btnModifier;
    @FXML
    private Button btnAjouter;
    @FXML
    private Button btnNouveau;
 
  

    /*
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      categorie=FXCollections.observableArrayList(cat.findCategorieEntities());
      cmbCategorie.getItems().addAll(categorie);
      
       //mapage des champs tableview et object
        cln_idarticle.setCellValueFactory(new PropertyValueFactory<>("idarticle"));
        cln_libarticle.setCellValueFactory(new PropertyValueFactory<>("libarticle"));
        cln_stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        cln_prixvente.setCellValueFactory(new PropertyValueFactory<>("prixvente"));
        cln_stocksecurite.setCellValueFactory(new PropertyValueFactory<>("stocksecurite"));
        
        //tvListProduit.setItems(artList);
        article.addAll(artcon.findArticleEntities());
        table.setItems(article);    
    }    
    @FXML
    private void chargement(){
          //articleliste.add(a);
          //cmbCategorie.getItems().clear();
         //cmbCategorie.getItems().addAll("valeur 1","valeur 2","valeur 3");    
    }
    @FXML
    private void indexChange(){
    //    System.out.println("valeur choisie "+cmbCategorie.getValue());
    }
    
    ArticleJpaController d=new  ArticleJpaController();
    private boolean verification(){
       boolean ok=false;
     if(cmbCategorie.getValue()==null){
            alert("notification","Choisissez la catégorie de l\'article");
        }
        else if(txtLibarticle.getText().isEmpty()){
            alert("notification","saisissez le libellé de l\'article");
        }
        else if(!this.isInteger(txtStock.getText()))
            alert("notification","valeur de stock saisie incorrecte");
         else if(!this.isInteger(txtPrixvente.getText()))
            alert("notification","prix de vente incorrect");
          else if(!this.isInteger(txtStockSecurite.getText()))
            alert("notification","stock de sécurité incorrect");
        else
              ok=true;
         return ok;
    }
    
    
    @FXML
    private void action(ActionEvent event) {     
        if(verification()){
            Article a= new Article();
            a.setLibarticle(txtLibarticle.getText());
            a.setStock(Integer.parseInt(txtStock.getText()));
            a.setPrixVente(Integer.parseInt(txtPrixvente.getText())); 
            a.setStockSecurite(Integer.parseInt(txtStockSecurite.getText()));
         
            Categorie c=cmbCategorie.getValue();
            a.setCategorie(c); 
            d.create(a);   
            article.add(a);
            alert("notification","Article créé avec succès"); 
            viderChamps();
        }
    }
     
    @FXML
    private void clickTable(MouseEvent event) {
        a = table.getSelectionModel().getSelectedItem();
        txtLibarticle.setText(a.getLibarticle());
        txtStock.setText(String.valueOf(a.getStock()));
        txtStockSecurite.setText(String.valueOf(a.getStockSecurite()));
        cmbCategorie.setValue(a.getCategorie());
        txtPrixvente.setText(String.valueOf(a.getPrixVente()));
        btnAjouter.setDisable(true);
    }

    @FXML
    private void btnModifierClick(MouseEvent event) {
        if(verification()){
            try {
                //enlever l'objet à modifier pour le rajouter après
                article.remove(a);
                a.setLibarticle(txtLibarticle.getText());
                a.setStock(Integer.parseInt(txtStock.getText()));
                a.setPrixVente(Integer.parseInt(txtPrixvente.getText()));
                a.setStockSecurite(Integer.parseInt(txtStockSecurite.getText()));
                Categorie c=cmbCategorie.getValue();
                a.setCategorie(c);
                d.edit(a);
                article.add(a);        
                alert("notification","Article modifié");
                viderChamps();
            } catch (Exception ex) {
                Logger.getLogger(VueArticleController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void viderChamps(){
       // cmbCategorie.setSelectionModel(null);
        txtLibarticle.setText("");
        txtStock.setText("");
        txtPrixvente.setText("");
        txtStockSecurite.setText("");      
    }

    @FXML
    private void btnNouveauClick(MouseEvent event) {
        btnAjouter.setDisable(false);
        viderChamps();
    }
        
    
}
