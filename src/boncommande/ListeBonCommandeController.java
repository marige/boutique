
package boncommande;

import detailboncommande.DetailBonCommande;
import detailboncommande.DetailBonCommandeJpaController;
import exceptions.NonexistentEntityException;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import report.REPORT;
import superpackage.SuperClass;
import vente.VenteJpaController;

/**
 * FXML Controller class
 *
 * @author OBAM
 */
public class ListeBonCommandeController extends SuperClass implements Initializable {
   
    @FXML
    private DatePicker dtDateDebut;
    @FXML
    private DatePicker dtDateFin;
    @FXML
    private Text txtEtat;
    @FXML
    private TableView<BonCommande> tbv_bonCommande;
    @FXML
    private TableView<DetailBonCommande> tbv_detailBOn;
    @FXML
    private TableColumn<DetailBonCommande, String> cln_produit_lib;
    @FXML
    private TableColumn<DetailBonCommande, Integer> cln_prix;
    @FXML
    private TableColumn<DetailBonCommande, Integer> cln_quantite;
    @FXML
    private TableColumn<BonCommande, Integer> cln_codebon;
    @FXML
    private TableColumn<BonCommande, String> cln_lib_bon;
    @FXML
    private TableColumn<BonCommande, Date> cln_date_bon;
    @FXML
    private TableColumn<BonCommande, String> cln_fournisseur;
    @FXML
    private TableColumn<BonCommande, Integer> cln_montant_bon;
    @FXML
    private TableColumn<BonCommande, Integer> cln_montant_article;
    
    BonCommande b= null;
    DetailBonCommande d= null;
    VenteJpaController ventecon= new VenteJpaController();
    private final ObservableList<DetailBonCommande> listDetailBon=FXCollections.observableArrayList();
    private final ObservableList<BonCommande> listBon=FXCollections.observableArrayList();
    private BonCommandeJpaController bcon= new BonCommandeJpaController();
    private DetailBonCommande dcon=new DetailBonCommande();
    private DetailBonCommandeJpaController dconcon= new DetailBonCommandeJpaController();
    REPORT r=new REPORT();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      //mappage de bon
       cln_codebon.setCellValueFactory(new PropertyValueFactory<>("idBonCommande"));
       cln_date_bon.setCellValueFactory(new PropertyValueFactory<>("dateBonCommande"));
       cln_fournisseur.setCellValueFactory(new PropertyValueFactory<>("fournisseur"));
       cln_lib_bon.setCellValueFactory(new PropertyValueFactory<>("libBonCommande"));
       cln_montant_bon.setCellValueFactory(new PropertyValueFactory<>("montant"));
        //mappage des details 
       cln_produit_lib.setCellValueFactory(new PropertyValueFactory<>("article"));
       cln_prix.setCellValueFactory(new PropertyValueFactory<>("puachat"));
       cln_quantite.setCellValueFactory(new PropertyValueFactory<>("quantiteDetailBonCommande"));
       
       tbv_bonCommande.setItems(listBon);
       tbv_detailBOn.setItems(listDetailBon); 
       
       txtEtat.setText("");
    }   
    
    @FXML
    private void clickAfficher(MouseEvent event) {
        listBon.clear();
        try {
            listBon.addAll(bcon.getListBonCommandeParDate(this.convertStringToDate(dtDateDebut.getValue()),
                    this.convertStringToDate(dtDateFin.getValue())));
        } catch (ParseException | NullPointerException ex ) {
           alert("erreur","choisissez dates début et fin");
        }
    }

    @FXML
    private void clickBonCommande(MouseEvent event) {
        //renseigenement de article en vue de maj après
        if(!tbv_bonCommande.getSelectionModel().isEmpty()){
        b=tbv_bonCommande.getSelectionModel().getSelectedItem();
        //liste de vente dans une facture
        listDetailBon.clear();
        listDetailBon.addAll(dconcon.getListDetailParBon(b));
        if(b.getReception()){
            txtEtat.setFill(Color.RED);
            txtEtat.setText("Réception effectuée déjà");
         
        }
        else if(!b.getReception()){
           txtEtat.setText("Réception non effectuée");
            txtEtat.setFill(Color.GREEN);
        }
       }
    }
    @FXML
    void clickAnnulerVente(MouseEvent event) {
        if(tbv_detailBOn.getItems().isEmpty()||b==null){
         alert("notification","Choisissez un bon de commande à annuler");
        }
        else if(this.confirmation("Attention","L\'annulation commande va supprimer le bon \nEtes-vous prêt?")){
            try {
                bcon.destroy(b.getIdBonCommande());
                 alert("notification","Annulation réussie");
                 listBon.remove(b);
                 b=null;
            } catch (NonexistentEntityException ex) {
               alert("notification","Suppression impossible:"+ex.getMessage());
            }
        };
    }

    @FXML
    void clickReimpressionBon(MouseEvent event) {
         if(tbv_detailBOn.getItems().isEmpty()||b==null){
         alert("notification","Choisissez un bon à imprimer");
        }
        else{
        try {
            r.etatBonCommande(b);
        } catch (Exception ex) {
           alert("error","bon de commande non chargeable");
        }
        }
    }

    @FXML
    private void clickReimpressionBordereau(MouseEvent event) {
        if(tbv_detailBOn.getItems().isEmpty()||b==null){
         alert("notification","Choisissez un bon à imprimer");
        }
        else if(!b.getReception())
            alert("","reception non effectuée\n Bordereau inexistant");
        else{
        try {
            r.etatBordereau(b);
        } catch (Exception ex) {
           alert("error","bon de commande non chargeable");
        }
        }
    }
    
}
