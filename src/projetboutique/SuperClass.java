package projetboutique;




import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author OBAM
 */
public class SuperClass {    
    public  void display(String title, String message){
        Label label= new Label();
        Button closeButton= new Button("Femer");
        Stage window= new Stage();
        window.initModality(Modality.APPLICATION_MODAL);// fenetre modale
        window.setTitle(title);
        
        label.setText(message);
        closeButton.setOnAction(e ->window.close());
        
        HBox layoutButton   = new HBox(5); 
        HBox layoutText     = new HBox(5);
        VBox content        =new VBox(10);
        
        layoutButton.getChildren().addAll(closeButton);
        layoutText.getChildren().addAll(label);

        layoutButton.setAlignment(Pos.BOTTOM_RIGHT);
        layoutText.setAlignment(Pos.CENTER);
        
        content.getChildren().addAll(layoutText,layoutButton);
        
        Scene scene =new Scene(content);
         window.setScene(scene);
         window.showAndWait();
    }
}
