package ihm;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Disease;
import text.IndexText;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
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
        Alert alert = App.progressAlert("Initialisation");
		Task<List<Disease>> init = new Task<List<Disease>>() {
			@Override
			protected List<Disease> call() throws Exception {
				initialiseData();
				return null;
			}
		};
		new Thread(init).start();
		init.setOnSucceeded(e->{
        	alert.close();
		});
		init.setOnCancelled(e->{
        	alert.close();
			try{
				init.getException().printStackTrace();
			}catch(Exception ex){}
			new Alert(AlertType.ERROR, "Error to initialise the software.", null).show();
		});
		alert.showingProperty().addListener((obs,o,n)->{
			if(!n && o){
				if(!init.isDone()){
					alert.show();
				}
			}
		});
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

	public static Alert progressAlert(String Text) {
		ButtonType close = new ButtonType("Close");
		Alert alert = new Alert(AlertType.NONE, "",close);
		alert.setTitle(Text);;
		ProgressBar progressBar = new ProgressBar();
		VBox vBox = new VBox(progressBar);
		alert.setGraphic(vBox);
		alert.getDialogPane().setPrefWidth(200);;
		progressBar.setPrefWidth(200);
		progressBar.setMinWidth(200);
		progressBar.setMaxWidth(200);
		vBox.setPrefWidth(200);
		vBox.setMinWidth(200);
		vBox.setMaxWidth(200);
		alert.show();
		return alert;
	}

    private void initialiseData() {
		// TODO Auto-generated method stub
		//IndexText.index();	
	}
    
}
