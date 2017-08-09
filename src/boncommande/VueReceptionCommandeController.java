
package boncommande;

import article.Article;
import article.ArticleJpaController;
import detailboncommande.DetailBonCommande;
import java.net.URL;
import java.util.Date;
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
import javafx.scene.text.Text;
import detailboncommande.DetailBonCommandeJpaController;
import exceptions.NonexistentEntityException;
import superpackage.SuperClass;

/**
 * FXML Controller class
 *
 * @author geres
 */
public class VueReceptionCommandeController extends SuperClass implements Initializable {
    
    private ObservableList<DetailBonCommande> les_details= FXCollections.observableArrayList();
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
    private DatePicker txt_date_reception;
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
    private ArticleJpaController articleC= new ArticleJpaController();
    
    @FXML
    private TableColumn<DetailBonCommande, Date> cln_exp;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tbl_detail_commande.setItems(les_details);
        cln_libArcticle.setCellValueFactory(new PropertyValueFactory<>("idArticle"));
        cln_quantite.setCellValueFactory(new PropertyValueFactory<>("quantiteDetailBonCommande"));
        cln_prix.setCellValueFactory(new PropertyValueFactory<>("puachat"));
       // cln_exp.setCellValueFactory(new PropertyValueFactory<>("dateperemption"));
        
    }    

    @FXML
    private void search(ActionEvent event) {
        les_details.addAll(detailController.findDetailBonCommande(Integer.parseInt(txt_id_bon_commande.getText())));
       // les_details.addAll(detailController.findDetailBonByIdBon(Integer.parseInt(txt_id_bon_commande.getText())));
        
    }

    @FXML
    private void tableClicked(MouseEvent event) {
         DetailBonCommande selectItems;
         selectItems=tbl_detail_commande.getSelectionModel().getSelectedItem();
        
        if(selectItems!=null){
                       
                        txt_prix.setText(""+selectItems.getPuachat());
                       // txt_date.setValue(superClass.LOCAL_DATE(selectItems.getDateperemption().toString()));          
        }
    }

    @FXML
    private void modifierClicked(ActionEvent event) {
        
         DetailBonCommande nouveau =new DetailBonCommande(tbl_detail_commande.getSelectionModel().getSelectedItem().getIdDetailBonCommande());
            nouveau.setPuachat(Integer.parseInt(txt_prix.getText()));
        try {
            detailController.edit(nouveau);
        } catch (Exception ex) {
            Logger.getLogger(VueReceptionCommandeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void validerClicked(ActionEvent event) {
        try {
        Article a=null;
        for(DetailBonCommande detailbon:tbl_detail_commande.getItems()){
            a= detailbon.getArticle();
            a.setStock(a.getStock()+detailbon.getQuantiteDetailBonCommande());  
                articleC.edit(a);                 
            }    
        BonCommande bonC= bcc.findBoncommande(Integer.parseInt(txt_id_bon_commande.getText()));
        bonC.setReception(true);
        bonC.setDatereception(this.convertStringToDate(txt_date_reception.getValue()));
            bcc.edit(bonC);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(VueReceptionCommandeController.class.getName()).log(Level.INFO, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(VueReceptionCommandeController.class.getName()).log(Level.INFO, null, ex);
        }
        
    }

    
}
