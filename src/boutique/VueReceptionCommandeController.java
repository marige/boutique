 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boutique;

import entitie.DetailBonCommande;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
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
import javafx.scene.text.Text;
import jpaController.BonCommandeJpaController;
import jpaController.DetailBonCommandeJpaController;
import superpackage.SuperClass;

/**
 * FXML Controller class
 *
 * @author geres
 */
public class VueReceptionCommandeController implements Initializable {
    
    private ObservableList<DetailBonCommande> les_details;
     SuperClass superClass =new SuperClass();
   
    
    @FXML
    private TextField txt_id_bon_commande;
    @FXML
    private Text lab_libBonCommande;
    @FXML
    private TableView<DetailBonCommande> tbl_detail_commande;
    @FXML
    private TableColumn<DetailBonCommande, String> cln_libArcticle;
    @FXML
    private TableColumn<DetailBonCommande, Integer> cln_quantite;
    @FXML
    private TableColumn<DetailBonCommande, Integer> cln_prix;
    @FXML
    private DatePicker txt_date_now;
    @FXML
    private DatePicker txt_date_exp;
    @FXML
    private TextField txt_prix;
    @FXML
    private Button btn_modifier;
    @FXML
    private Button btn_valider;

    /**
     * Initializes the controller class.
     */
    private  DetailBonCommandeJpaController detailController= new DetailBonCommandeJpaController();
    private BonCommandeJpaController bcc= new BonCommandeJpaController();
    
    @FXML
    private TableColumn<DetailBonCommande, Date> cln_exp;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tbl_detail_commande.setItems(les_details);
        cln_libArcticle.setCellValueFactory(new PropertyValueFactory<>("idArticle"));
        cln_quantite.setCellValueFactory(new PropertyValueFactory<>("quantiteDetailBonCommande"));
        cln_prix.setCellValueFactory(new PropertyValueFactory<>("puachat"));
        cln_exp.setCellValueFactory(new PropertyValueFactory<>("dateperemption"));
        
    }    

    @FXML
    private void search(ActionEvent event) {
        les_details=FXCollections.observableArrayList(detailController.findDetailBonByIdBon(bcc.findBonCommande(Integer.parseInt(txt_id_bon_commande.getText()))));
       // les_details.addAll(detailController.findDetailBonByIdBon(Integer.parseInt(txt_id_bon_commande.getText())));
        
    }
    
}
