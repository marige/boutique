
package boutique;

import entite.tableView.DetailFacture;
import entitie.Vente;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import jpaController.VenteJpaController;
import superpackage.SuperClass;

/**
 * FXML Controller class
 *
 * @author OBAM
 */
public class RapportVenteController extends SuperClass implements Initializable {
    VenteJpaController ventecon= new VenteJpaController();
    private final ObservableList<DetailFacture> listDetailFacture=FXCollections.observableArrayList();
    private DetailFacture df=null;
    
    @FXML
    private TableView<DetailFacture> tvRapport;
    @FXML
    private DatePicker dtDebut;
    @FXML
    private DatePicker dtFin;
    @FXML
    private Button btnAfficher;
    @FXML
    private TableColumn<DetailFacture, Date> cln_date;
    @FXML
    private TableColumn<DetailFacture, String> cln_article;
    @FXML
    private TableColumn<DetailFacture,Integer> cln_qte;
    @FXML
    private TableColumn<DetailFacture, Integer> cln_pu;
    @FXML
    private TableColumn<DetailFacture, Integer> cln_montant;
     @FXML
    private TableColumn<DetailFacture, String> cln_client;
     Vente v = new Vente();
    
     
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        
        cln_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        cln_article.setCellValueFactory(new PropertyValueFactory<>("libArticle"));
        cln_qte.setCellValueFactory(new PropertyValueFactory<>("qte"));
        cln_pu.setCellValueFactory(new PropertyValueFactory<>("pu"));
        cln_montant.setCellValueFactory(new PropertyValueFactory<>("montant"));
        cln_client.setCellValueFactory(new PropertyValueFactory<>("client"));
        tvRapport.setItems(listDetailFacture);
        
        
        
    }   

    @FXML
    private void clickBtnAfficher(MouseEvent event) {
        //vider pour eviter les repetitions de données
       listDetailFacture.clear();
    
        try {
            List<Vente> ventes=ventecon.getListVenteParDate(this.convertStringToDate(dtDebut.getValue()),this.convertStringToDate(dtFin.getValue()));
           
            //chargement de detailfacture
           
            for(Vente o:ventes){  
                    df=new DetailFacture();
                    //System.out.println("id vente: "+this.getDateFormatAffichage(o.getFacture().getDateFacture()));
                    df.setLibArticle(o.getArticle().getLibarticle());
                    df.setPu(o.getPu());
                    df.setQte(o.getQte());
                    df.setDate(this.getDateFormatAffichage(o.getFacture().getDateFacture()));          
                    df.setMOntant();   
                    df.setClient(o.getFacture().getClient());   
                    listDetailFacture.add(df);                            
            }
           
        //    System.out.println("taille de vente est "+(ventes.get(0)).getFacture().getDateFacture());
        } catch (ParseException ex) {
            Logger.getLogger(RapportVenteController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
    }
}
