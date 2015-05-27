package ihm;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
 

public class App extends Application {
	Logger log = Logger.getLogger(App.class.getName());
    private final AnchorPane root = new AnchorPane();

     

    @Override 
    public void start(Stage primaryStage) throws Exception {
        gotoScreen(AccueilController.class);
        primaryStage.setHeight(600);
        primaryStage.setWidth(800);
        root.setPrefHeight(600);
        root.setPrefWidth(800);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

 

    public static void main(String[] args) {
        Application.launch(args);
    } 

    public Controller gotoScreen(Class<? extends Controller> clazz) {
        try {
        	return (Controller) Controller.initialise(this,clazz);
        } catch (Exception ex) {
            log.log(Level.SEVERE, "Erreur lors du chargement de: "+clazz.getName(), ex);
            return null;
        }
    }
    public void setGraphic(Node node){
    	root.getChildren().clear();
    	root.getChildren().add(node);
    }
    public ReadOnlyDoubleProperty heightProperty(){
    	return root.heightProperty();
    }
    public ReadOnlyDoubleProperty widthProperty(){
    	return root.widthProperty();
    }
}
