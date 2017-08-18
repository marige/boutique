/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vente;

import article.Article;
import exceptions.NonexistentEntityException;
import facture.Facture;
import facture.FactureJpaController;
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
import report.REPORT;
import superpackage.SuperClass;

/**
 * FXML Controller class
 *
 * @author OBAM
 */
public class ListeVenteController extends SuperClass implements Initializable {
    
    @FXML
    private TableView<Facture> tbv_facture;

    @FXML
    private TableView<Vente> tbv_detailfacture;


    @FXML
    private TableColumn<Vente,Integer> cln_qte;

    @FXML
    private TableColumn<Facture,Integer> cln_code_facture;

    @FXML
    private TableColumn<Vente,Integer> cln_pu;

    @FXML
    private TableColumn<Facture, String> cln_client;

    @FXML
    private TableColumn<Facture, Date> cln_date;


    @FXML
    private TableColumn<Facture,Integer> cln_montant_facture;
    
    @FXML
    private TableColumn<Vente,Article> cln_libarticle;
    
    Facture f= null;
    Vente v= null;
    VenteJpaController ventecon= new VenteJpaController();
    private final ObservableList<Vente> listVente=FXCollections.observableArrayList();
    private final ObservableList<Facture> listFacture=FXCollections.observableArrayList();
    private FactureJpaController fcon= new FactureJpaController();
    private VenteJpaController vcon=new VenteJpaController();
    @FXML
    private DatePicker dtDateDebut;
    @FXML
    private DatePicker dtDateFin;
    
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      //mappage de facture
       cln_client.setCellValueFactory(new PropertyValueFactory<>("client"));
       cln_code_facture.setCellValueFactory(new PropertyValueFactory<>("idFacture"));
       cln_date.setCellValueFactory(new PropertyValueFactory<>("dateFacture"));
       cln_montant_facture.setCellValueFactory(new PropertyValueFactory<>("montant"));
      //mappage de vente
       cln_pu.setCellValueFactory(new PropertyValueFactory<>("pu"));
       cln_qte.setCellValueFactory(new PropertyValueFactory<>("qte"));
       cln_libarticle.setCellValueFactory(new PropertyValueFactory<>("article"));
       //remplissage de la table vue       
       tbv_facture.setItems(listFacture);
       tbv_detailfacture.setItems(listVente); 
    }   
    
    @FXML
    private void clickAfficher(MouseEvent event) {
        listFacture.clear();
        try {
            listFacture.addAll(fcon.getListFactureParDate(this.convertStringToDate(dtDateDebut.getValue()),
                    this.convertStringToDate(dtDateFin.getValue())));
        } catch (ParseException | NullPointerException ex ) {
           alert("erreur","choisissez dates début et fin");
        }
    }

    @FXML
    private void clickFacture(MouseEvent event) {
        if(!tbv_facture.getSelectionModel().isEmpty()){
        //renseigenement de article en vue de maj après
        f=tbv_facture.getSelectionModel().getSelectedItem();
        //liste de vente dans une facture
        listVente.clear();
        listVente.addAll(f.getVenteFacture());
        }
    }
    @FXML
    void clickAnnulerVente(MouseEvent event) {
        if(tbv_detailfacture.getItems().isEmpty()||f==null){
         alert("notification","Choisissez une facture à annuler");
        }
        else if(this.confirmation("Attention","L\'annulation vente va supprimer la facture\nEtes-vous prêt?")){
            try {
                fcon.destroy(f.getIdFacture());
                 alert("notification","Annulation réussie");
                 listFacture.remove(f);
                 f=null;
            } catch (NonexistentEntityException ex) {
               alert("notification","Suppression impossible");
            }
        };
    }

    @FXML
    void clickReimpression(MouseEvent event) {
         if(tbv_detailfacture.getItems().isEmpty()||f==null){
         alert("notification","Choisissez une facture à annuler");
        }
         else{
        try {
            new REPORT().etatFacture(f);
        } catch (Exception ex) {
           alert("error","Facture non chargeable");
        }
        }
    }
    
}
