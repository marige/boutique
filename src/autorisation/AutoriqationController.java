/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autorisation;

import com.sun.xml.internal.bind.v2.model.core.ValuePropertyInfo;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import utilisateur.Utilisateur;

/**
 * FXML Controller class
 *
 * @author OBAM
 */
public class AutoriqationController implements Initializable {
    @FXML
    private ComboBox<Utilisateur> cmbUtilisateur;
    @FXML
    private TableView<Autorisation> tbv_fonction_dispo;
    @FXML
    private TableView<Autorisation> tbv_fonction_user;    
    @FXML
    private TableColumn<Autorisation, String> cln_fonctional_user;
    @FXML
    private TableColumn<Autorisation, String> cln_fonctionalite_exist;
    
    
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {    
        cln_fonctional_user.setCellValueFactory(new PropertyValueFactory<>("idarticle"));
    }    
    
}
