package boutique;




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


public class SuperClass {  
    
    /**
 *
 * @param title the title of alert windows
 * @param message the text to display in the alert windows
 * @param type The type of windows to display, like info, success or danger
 */
    public  void alert(String title, String message ){
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
        
        Scene scene =new Scene(content, 300, 250);
         window.setScene(scene);
         window.showAndWait();
    }
    
     public  void alert(String title, String message, String type ){
         
         if(type.contentEquals("danger"))
             title="DANGER: "+title;
         if(type.contentEquals("warning"))
             title="ATTENTION: "+title;
         if(type.contentEquals("success"))
             title="Validation: "+title;
          if(type.contentEquals("info")){
             alert(title,message);
             return;
          }
         
          Label label= new Label();
        Button closeButton= new Button("Femer");  
        Button openButton= new Button("Ouvrir");

        Stage window= new Stage();
        window.initModality(Modality.APPLICATION_MODAL);// fenetre modale
        window.setTitle(title);
        
        label.setText(message);
        closeButton.setOnAction(e ->window.close());
        
        HBox layoutButton   = new HBox(5); 
        HBox layoutText     = new HBox(10);
        VBox content        =new VBox(10);
        
        layoutButton.getChildren().addAll(closeButton);
        layoutText.getChildren().addAll(label);

        layoutButton.setAlignment(Pos.BOTTOM_RIGHT);
        layoutText.setAlignment(Pos.CENTER);
        openButton.setAlignment(Pos.BOTTOM_LEFT);
        content.getChildren().addAll(layoutText,layoutButton,openButton);
        
        Scene scene =new Scene(content, 300, 250);
         window.setScene(scene);
         window.showAndWait();
         
         
         
         
         
        
     }
}
