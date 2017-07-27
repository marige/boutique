/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetboutique;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author OBAM
 */
public class VueFactureController implements Initializable {
    @FXML
    private TextField txtLibarticle;
    @FXML
    private Button btn;
    @FXML
    private TableColumn<?, ?> NOM;
    @FXML
    private TableColumn<?, ?> PRENOMS;
    @FXML
    private TableColumn<?, ?> EMAIL;
    @FXML
    private TableView<?> tvDetailFacture;
    @FXML
    private TableColumn<?, ?> EMAIL2;
    @FXML
    private TableColumn<?, ?> EMAIL21;
    @FXML
    private TableView<?> tvListProduit;
    @FXML
    private TableColumn<?, ?> clnCodeP;
    @FXML
    private TableColumn<?, ?> clnLib;
    @FXML
    private TableColumn<?, ?> clnQteStock;
    @FXML
    private TextField txtRecherche;
    @FXML
    private Button btn1;
    @FXML
    private DatePicker dtDateFacture;
    @FXML
    private TextField txtQte;
    @FXML
    private TextField txtMontantTtc;
    @FXML
    private TextField txtQte1;
    @FXML
    private TextField txtQte2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    


    @FXML
    private void action(ActionEvent event) {
    }
    
}
