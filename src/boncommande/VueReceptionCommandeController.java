
package boncommande;

import article.Article;
import article.ArticleJpaController;
import detailboncommande.DetailBonCommande;
import java.net.URL;
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
    @FXML
    private TextField txt_id_bon_commande;
    @FXML
    private TableView<DetailBonCommande> tbl_detail_commande;
    @FXML
    private TableColumn<DetailBonCommande, Article> cln_libArcticle;
    @FXML
    private TableColumn<DetailBonCommande, Integer> cln_quantite;
    @FXML
    private TableColumn<DetailBonCommande, Integer> cln_prix;
    @FXML
    private TableColumn<DetailBonCommande, Integer> cln_montant;
    @FXML
    private Button btn_valider;
    
    @FXML
    private DatePicker dt_date_reception;
    @FXML
    private TextField txtlibcommande;
    @FXML
    private TextField txtfournisseur;
    @FXML
    private TextField dtdatebon;
    @FXML
    private TextField txtmontantbon;
   

    /**
     * Initializes the controller class.
     */
    private  DetailBonCommandeJpaController detailController= new DetailBonCommandeJpaController();
    private BonCommandeJpaController bcc= new BonCommandeJpaController();
    private ArticleJpaController articleC= new ArticleJpaController();
    BonCommande bonC=null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tbl_detail_commande.setItems(les_details);
        cln_libArcticle.setCellValueFactory(new PropertyValueFactory<>("article"));
        cln_quantite.setCellValueFactory(new PropertyValueFactory<>("quantiteDetailBonCommande"));
        cln_prix.setCellValueFactory(new PropertyValueFactory<>("puachat"));             
    }    

    @FXML
    private void search(ActionEvent event) {
         tbl_detail_commande.getItems().clear();
         bonC= bcc.findBoncommande(Integer.parseInt(txt_id_bon_commande.getText()));
         if(bonC==null)
             alert("notication","Numéro de bon n\'existe pas");
         if(bonC.getReception())
              alert("notication","Le bon N°"+txt_id_bon_commande.getText()+" est déjà validé");
         else{
         txtfournisseur.setText(bonC.getFournisseur().getLibFournisseur());
         txtlibcommande.setText(bonC.getLibBonCommande());
         txtmontantbon.setText(this.formatageMontant(bonC.getMontant()));
         dtdatebon.setText(this.getDateFormatAffichage(bonC.getDateBonCommande()));
         les_details.addAll(detailController.findDetailBonCommande(Integer.parseInt(txt_id_bon_commande.getText())));   
        }
    }
   
    @FXML
    private void validerClicked(ActionEvent event) {      
        if(dt_date_reception.getValue()==null)
            alert("erreur","Choisissez date de validation");
        else{
        try {   
        bonC.setReception(true);
        bonC.setDatereception(this.convertStringToDate(dt_date_reception.getValue()));
            bcc.edit(bonC);
        alert("information","Bon de commande reçu");
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(VueReceptionCommandeController.class.getName()).log(Level.INFO, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(VueReceptionCommandeController.class.getName()).log(Level.INFO, null, ex);
        }
        }
        
    }

    
}

