/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.Controler;
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
import superpackage.SuperClass;
import exceptions.NonexistentEntityException;

/**
 * FXML Controller class
 *
 * @author OBAM
 */
public class AjoutcontrolerController extends SuperClass implements Initializable {
    @FXML
    private TextField txtNomControler;
    @FXML
    private TextField txtdescription;    
    @FXML
    private TableColumn<Controler, String> cln_nomcontroler;
    @FXML
    private TableView<Controler> tbvListControler;
    @FXML
    private TableColumn<Controler, String> cln_description;
    
    private ObservableList<Controler> listcontroler= FXCollections.observableArrayList();
    Controler c = new Controler();
    ControlerJpaController concon= new ControlerJpaController();
    @FXML
    private Button btnAjouter;
    @FXML
    private Button btnSupprimer;
    @FXML
    private Button btnModifier;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       cln_description.setCellValueFactory(new PropertyValueFactory<>("description"));
       cln_nomcontroler.setCellValueFactory(new PropertyValueFactory<>("nomcontroler"));
       listcontroler.addAll(concon.findControlerEntities());
       tbvListControler.setItems(listcontroler);
      /* btnModifier.setDisable(true);
       btnSupprimer.setDisable(true);*/
       
       
    }    
    private boolean controle(){
        boolean ok=false;
        if(txtNomControler.getText().isEmpty())
            alert("","taper le nom du controler");
        else if(txtdescription.getText().isEmpty())
            alert("","taper description du controler");
        else 
            ok=true;
        return ok;
                   
    }
    @FXML
    private void clickAjouter(MouseEvent event) {
       if(controle()){
        c.setDescription(txtdescription.getText());
        c.setNomcontroler(txtNomControler.getText());
        try {
            concon.create(c);
            alert("notification","Controler ajouté");
            listcontroler.add(c);
        } catch (Exception ex) {
             alert("notification","erreur :"+ex.getMessage());
        }
       }
    }
    Controler cselect=null;
    @FXML
    private void clickTableV(MouseEvent event){
      cselect= tbvListControler.getSelectionModel().getSelectedItem();
      if(cselect!=null){
          txtNomControler.setText(cselect.getNomcontroler());
          txtdescription.setText(cselect.getDescription());     
        }
    }
    @FXML
    private void clickSuppression(MouseEvent event){
      if(cselect==null){
          alert("","choisissez un controler à supprimer");
      }
      else{
        try {
            concon.destroy(cselect.getNomcontroler());
            alert("notification","Suppression effectué");
            listcontroler.remove(cselect);
        } catch (NonexistentEntityException ex) {
           alert("","suppression impossible");
        }
        }
    }
    @FXML
    private void clickModification(MouseEvent event){
        if(cselect==null){
        alert("","choisissez un controler à modifier");
         }else{
        try {
            listcontroler.remove(cselect);
            concon.edit(cselect);
            listcontroler.add(cselect);
            alert("notification","modification effectué");
        } catch (Exception ex) {
           alert("","suppression impossible:"+ex.getMessage());
           }
        }
    }
}
