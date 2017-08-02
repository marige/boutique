/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boutique;

import entitie.Categorie;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import jpaController.CategorieJpaController;
import superpackage.SuperClass;

/**
 * FXML Controller class
 *
 * @author OBAM
 */
public class VueCategorieController extends SuperClass implements Initializable {
    private final ObservableList<Categorie> cate= FXCollections.observableArrayList();
    CategorieJpaController catcon= new CategorieJpaController(); 
    Categorie c= new Categorie();
    
    @FXML
    private TableView<Categorie> tableVcategorie;
    @FXML
    private Button btnAjouter;
    @FXML
    private TableColumn<Categorie,Integer> cln_idcategorie;
    @FXML
    private TableColumn<Categorie,String> cln_libcategorie;
    @FXML
    private TextField txtLibCategorie;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cln_idcategorie.setCellValueFactory(new PropertyValueFactory<>("idcat"));
        cln_libcategorie.setCellValueFactory(new PropertyValueFactory<>("libcat"));  
        cate.addAll(catcon.findCategorieEntities());
        tableVcategorie.setItems(cate);
       
       
    }    

    @FXML
    private void btnAjouterClick(MouseEvent event) {
        c.setLibCategorie(txtLibCategorie.getText());
        catcon.create(c);
        cate.add(c);
        this.alert("alert","Catégorie ajoutée avec succès");
        
    }

    
}
