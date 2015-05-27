package ihm;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Disease;





@FxmlSource("fxml/Accueil.fxml")
public class AccueilController extends Controller{

	@FXML VBox boxResult;
	@FXML HBox boxParam;
	@FXML VBox boxSearch;

	
	@FXML VBox boxDisease;
	@FXML TextField tfDisease;
	@FXML Button bDisease;
	@FXML ListView lvDisease;
	
	@FXML VBox boxSign;
	@FXML TextField tfSign;
	@FXML Button bSign;
	@FXML ListView lvSign;
	
	@FXML VBox boxDrug;
	@FXML TextField tfDrug;
	@FXML Button bDrug;
	@FXML ListView lvDrug;
	
	@FXML VBox boxResultDiseases;
	@FXML ComboBox<Disease>  cbDisResult;
	@FXML TextArea taDisDescription;
	@FXML HBox boxDisResult;
	@FXML VBox boxDisSynonym;
	@FXML ListView lvDisSynonym;
	@FXML VBox boxDisSymptom;
	@FXML ListView lvDisSymptom;
	@FXML VBox boxDisDrugIndication;
	@FXML ListView lvDisDrugIndication;
	@FXML VBox boxDisDrugAdverseEffect;
	@FXML ListView lvDisDrugAdverseEffect;
	
	@FXML CheckBox cb;
	@FXML Button search;
	
	List<Disease> lCouchDB=new ArrayList<Disease>();
	List<Disease> lMySql=new ArrayList<Disease>();
	List<Disease> lText=new ArrayList<Disease>();
	List<Disease> lXml=new ArrayList<Disease>();
	
	@Override
	protected void setStartCondition() {
		boxResult.prefWidthProperty().bind(this.widthProperty());
		boxResult.minWidthProperty().bind(boxResult.prefWidthProperty());
		boxResult.maxWidthProperty().bind(boxResult.prefWidthProperty());
		boxSearch.prefWidthProperty().bind(this.widthProperty());
		boxSearch.minWidthProperty().bind(boxSearch.prefWidthProperty());
		boxSearch.maxWidthProperty().bind(boxSearch.prefWidthProperty());
		boxSearch.prefHeightProperty().bind(this.heightProperty().multiply(2/5));
		boxSearch.minHeightProperty().set(150);
		boxSearch.maxHeightProperty().bind(this.heightProperty().multiply(2/5));

		boxParam.prefWidthProperty().bind(this.widthProperty());
		boxParam.minWidthProperty().bind(boxParam.prefWidthProperty());
		boxParam.maxWidthProperty().bind(boxParam.prefWidthProperty());
		boxParam.prefHeightProperty().bind(boxParam.minHeightProperty());
		boxParam.minHeightProperty().set(30);
		boxParam.maxHeightProperty().bind(boxParam.minHeightProperty());
		

		boxResultDiseases.prefWidthProperty().bind(this.widthProperty());
		boxResultDiseases.minWidthProperty().bind(boxResultDiseases.prefWidthProperty());
		boxResultDiseases.maxWidthProperty().bind(boxResultDiseases.prefWidthProperty());
		boxDisResult.prefWidthProperty().bind(boxResultDiseases.widthProperty().subtract(15));
		boxDisResult.minWidthProperty().bind(boxDisResult.prefWidthProperty());
		boxDisResult.maxWidthProperty().bind(boxDisResult.prefWidthProperty());
		
		
		boxDisease.prefWidthProperty().bind(boxSearch.widthProperty().divide(3).subtract(10));
		boxDisease.maxWidthProperty().bind(boxDisease.prefWidthProperty());
		boxDisease.minWidthProperty().bind(boxDisease.prefWidthProperty());
		boxSign.prefWidthProperty().bind(boxSearch.widthProperty().divide(3).subtract(10));
		boxSign.maxWidthProperty().bind(boxSign.prefWidthProperty());
		boxSign.minWidthProperty().bind(boxSign.prefWidthProperty());
		boxDrug.prefWidthProperty().bind(boxSearch.widthProperty().divide(3).subtract(10));
		boxDrug.maxWidthProperty().bind(boxDrug.prefWidthProperty());
		boxDrug.minWidthProperty().bind(boxDrug.prefWidthProperty());
		

		
		boxDisSynonym.prefWidthProperty().bind(boxDisResult.widthProperty().divide(4).subtract(30/4));
		boxDisSynonym.maxWidthProperty().bind(boxDisSynonym.prefWidthProperty());
		boxDisSynonym.minWidthProperty().bind(boxDisSynonym.prefWidthProperty());
		boxDisSymptom.prefWidthProperty().bind(boxDisResult.widthProperty().divide(4).subtract(30/4));
		boxDisSymptom.maxWidthProperty().bind(boxDisSymptom.prefWidthProperty());
		boxDisSymptom.minWidthProperty().bind(boxDisSymptom.prefWidthProperty());
		boxDisDrugIndication.prefWidthProperty().bind(boxDisResult.widthProperty().divide(4).subtract(30/4));
		boxDisDrugIndication.maxWidthProperty().bind(boxDisDrugIndication.prefWidthProperty());
		boxDisDrugIndication.minWidthProperty().bind(boxDisDrugIndication.prefWidthProperty());
		boxDisDrugAdverseEffect.prefWidthProperty().bind(boxDisResult.widthProperty().divide(4).subtract(30/4));
		boxDisDrugAdverseEffect.maxWidthProperty().bind(boxDisDrugAdverseEffect.prefWidthProperty());
		boxDisDrugAdverseEffect.minWidthProperty().bind(boxDisDrugAdverseEffect.prefWidthProperty());
		
		search.setOnAction(e->{
			research();
		});
	}
	private void research(){
		BooleanProperty endResearch = new SimpleBooleanProperty(false);
		BooleanProperty endCouchDB = new SimpleBooleanProperty(false);
		BooleanProperty endMySql = new SimpleBooleanProperty(false);
		BooleanProperty endText = new SimpleBooleanProperty(false);
		BooleanProperty endXml = new SimpleBooleanProperty(false);
		//ButtonType close = ButtonType.CLOSE;
		ButtonType close = new ButtonType("Close");
		Alert alert = new Alert(AlertType.NONE, "",close);
		Label label=new Label("Research ...");
		ProgressBar progressBar = new ProgressBar();
		VBox vBox = new VBox(label,progressBar);
		alert.setGraphic(vBox);
		alert.getDialogPane().setPrefWidth(200);;
		progressBar.setPrefWidth(200);
		progressBar.setMinWidth(200);
		progressBar.setMaxWidth(200);
		vBox.setPrefWidth(200);
		vBox.setMinWidth(200);
		vBox.setMaxWidth(200);
		alert.show();
		Task<List<Disease>> taskCouchDB = new Task<List<Disease>>() {
			@Override
			protected List<Disease> call() throws Exception {
				return researchCouchDB();
			}
		};
		Task<List<Disease>> taskMySql = new Task<List<Disease>>() {
			@Override
			protected List<Disease> call() throws Exception {
				return researchMySql();
			}
		};
		Task<List<Disease>> taskText = new Task<List<Disease>>() {
			@Override
			protected List<Disease> call() throws Exception {
				return researchText();
			}
		};
		Task<List<Disease>> taskXml = new Task<List<Disease>>() {
			@Override
			protected List<Disease> call() throws Exception {
				return researchXml();
			}
		};
		new Thread(taskCouchDB).start();
		new Thread(taskMySql).start();
		new Thread(taskText).start();
		new Thread(taskXml).start();

		lCouchDB.clear();
		lMySql.clear();
		lText.clear();
		lXml.clear();
		taskCouchDB.setOnSucceeded(e->{
			endCouchDB.set(true);
			try {
				lCouchDB.addAll(taskCouchDB.get());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		taskCouchDB.setOnCancelled(e->{
			endCouchDB.set(true);
			try{
				taskCouchDB.getException().printStackTrace();
			}catch(Exception ex){}
			new Alert(AlertType.ERROR, "Error to collect CouchDB datas.", null).show();
		});
		taskMySql.setOnSucceeded(e->{
			endMySql.set(true);
			try {
				lMySql.addAll(taskMySql.get());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		taskMySql.setOnCancelled(e->{
			endMySql.set(true);
			try{
				taskMySql.getException().printStackTrace();
			}catch(Exception ex){}
			new Alert(AlertType.ERROR, "Error to collect MySQL datas.", null).show();
		});
		taskText.setOnSucceeded(e->{
			endText.set(true);
			try {
				lText.addAll(taskText.get());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		taskText.setOnCancelled(e->{
			endText.set(true);
			try{
				taskText.getException().printStackTrace();
			}catch(Exception ex){}
			new Alert(AlertType.ERROR, "Error to collect Text datas.", null).show();
		});
		taskXml.setOnSucceeded(e->{
			endXml.set(true);
			try {
				lXml.addAll(taskXml.get());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		taskXml.setOnCancelled(e->{
			endXml.set(true);
			try{
				taskXml.getException().printStackTrace();
			}catch(Exception ex){}
			new Alert(AlertType.ERROR, "Error to collect XML datas.", null).show();
		});
		endResearch.bind(endCouchDB.and(endMySql).and(endText).and(endXml));

		Task<List<Disease>> taskMerge = new Task<List<Disease>>() {
			@Override
			protected List<Disease> call() throws Exception {
				return mergeData(lCouchDB, lMySql, lText, lXml);
			}
		};
		endResearch.addListener((obs,o,n)->{
			if(n){
				label.setText("Data merging ...");
				new Thread(taskMerge).start();
			}
		});
		taskMerge.setOnSucceeded(e->{
			//TODO affichage données
			//TODO gras sur les champs de recherche
        	alert.close();
		});
		taskMerge.setOnFailed(e->{
        	alert.close();
			try{
				taskText.getException().printStackTrace();
			}catch(Exception ex){}
			new Alert(AlertType.ERROR, "Error to merge datas.", null).show();
		});
		alert.showingProperty().addListener((obs,o,n)->{
			if(!n && o){
				if(!taskMerge.isDone()){
					alert.show();
				}
			}
		});
	}
	private List<Disease> mergeData(List<Disease> listCouchDB ,List<Disease> listMySql, List<Disease> listText, List<Disease> listXml){
		return listXml;
	}
	private List<Disease> researchCouchDB(){
		return new ArrayList<Disease>();
	}
	private List<Disease> researchMySql(){
		return new ArrayList<Disease>();
	}
	private List<Disease> researchText(){
		return new ArrayList<Disease>();
	}
	private List<Disease> researchXml(){
		return new ArrayList<Disease>();
	}
	
}
