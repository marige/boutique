
package projetboutique;

import entities.Article;
import entities.Categorie;
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
import javafx.scene.layout.AnchorPane;
import jpaControler.ArticleJpaController;
import jpaControler.CategorieJpaController;

/**
 * FXML Controller class
 *
 * @author OBAM
 */

public class VueArticleController implements Initializable {
    private ObservableList<Article> article=FXCollections.observableArrayList();
    private ObservableList<Categorie> categorie=null;
    SuperClass sc= new SuperClass();
    CategorieJpaController cat=new CategorieJpaController();
    @FXML
    private TableView<Article> table;
    @FXML
    private TableColumn<Article,String> NOM;
    @FXML
    private TableColumn<Article, String> PRENOMS;
    @FXML
    private TableColumn<Article, String> EMAIL;
    @FXML
    private Button btn;
    @FXML
    private TextField txtLibarticle;
    @FXML
    private TextField txtStock;
    @FXML
    private ComboBox<Categorie> cmbCategorie;
    @FXML
    private AnchorPane frmArticle;
    /*
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      categorie=FXCollections.observableArrayList(cat.findCategorieEntities());
      cmbCategorie.getItems().addAll(categorie);
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
    
    @FXML
    private void action(ActionEvent event) {
        ArticleJpaController d=new  ArticleJpaController();
        Article a= new Article(1,txtLibarticle.getText(),Integer.parseInt(txtStock.getText()));
        Categorie c=cmbCategorie.getValue();
        a.setCategorie(c); 
        d.create(a);      
        sc.display("notification","Article créé avec succès");        
    }
        
    
}
