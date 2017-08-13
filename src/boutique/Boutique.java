/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boutique;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import superpackage.SuperClass;

/**
 *
 * @author geres
 */
public class Boutique extends Application {
    SuperClass sc= new SuperClass();
    
         @Override
      public void start(Stage stage) throws Exception {
        //initialisation de la session de em pour faciliter le lancement des formulaire après
        EntityManager em=sc.getEntityManager();
        em.getTransaction().begin();
        em.getTransaction().commit();
        em.close();
        Parent root = FXMLLoader.load(getClass().getResource("authentification.fxml"));       
        Scene scene = new Scene(root);
        stage.setScene(scene);
        AuthentificationController.stage=stage;
        stage.show();
        
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
