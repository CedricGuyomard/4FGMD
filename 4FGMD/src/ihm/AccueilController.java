package ihm;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Disease;
import model.Drug;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.queryparser.classic.ParseException;

import text.TextService;





@FxmlSource("fxml/Accueil.fxml")
public class AccueilController extends Controller{

	@FXML VBox boxResult;
	@FXML HBox boxParam;
	@FXML HBox boxTitle;
	@FXML HBox boxTitleResult;
	@FXML VBox boxSearch;
	
	ToggleGroup toggleGroup;
	
	@FXML VBox boxSign;
	@FXML TextField tfSign;
	@FXML Button bSign;
	@FXML ListView<String> lvSign;
	@FXML Button bSignAnd;
	@FXML Button bSignOr;
	@FXML RadioButton rbSign;
	
	@FXML VBox boxDrug;
	@FXML TextField tfDrug;
	@FXML Button bDrug;
	@FXML ListView<String> lvDrug;
	@FXML Button bDrugAnd;
	@FXML Button bDrugOr;
	@FXML RadioButton rbDrug;
	
	@FXML VBox boxResultDiseases;
	@FXML ComboBox<Disease>  cbDisResult;
	@FXML TextArea taDisDescription;
	@FXML HBox boxDisResult;
	@FXML VBox boxDisSynonym;
	@FXML ListView<String> lvDisSynonym;
	@FXML VBox boxDisSymptom;
	@FXML ListView<Disease> lvDisSymptom;
	@FXML VBox boxDisDrugIndication;
	@FXML ListView<Drug> lvDisDrugIndication;
	@FXML VBox boxDisDrugAdverseEffect;
	@FXML ListView<Drug> lvDisDrugAdverseEffect;
	
	@FXML Button search;
	
	List<Disease> lCouchDB=new ArrayList<Disease>();
	List<Disease> lCSV=new ArrayList<Disease>();
	List<Disease> lMySql=new ArrayList<Disease>();
	List<Disease> lText=new ArrayList<Disease>();
	List<Disease> lXml=new ArrayList<Disease>();
	
	ListProperty<String> signs=new SimpleListProperty<String>(FXCollections.observableArrayList());
	ListProperty<String> drugs=new SimpleListProperty<String>(FXCollections.observableArrayList());
	ListProperty<Disease> results=new SimpleListProperty<Disease>(FXCollections.observableArrayList());
	
	ListProperty<String> synonyms=new SimpleListProperty<String>(FXCollections.observableArrayList());
	ListProperty<Disease> symptoms=new SimpleListProperty<Disease>(FXCollections.observableArrayList());
	ListProperty<Drug> drugIndications=new SimpleListProperty<Drug>(FXCollections.observableArrayList());
	ListProperty<Drug> drugAdverseEffects=new SimpleListProperty<Drug>(FXCollections.observableArrayList());
	
	ChangeListener<? super Boolean> endResearchListener;
	BooleanProperty endResearch = new SimpleBooleanProperty(false);
	BooleanProperty endCouchDB = new SimpleBooleanProperty(false);
	BooleanProperty endCSV = new SimpleBooleanProperty(false);
	BooleanProperty endMySql = new SimpleBooleanProperty(false);
	BooleanProperty endText = new SimpleBooleanProperty(false);
	BooleanProperty endXml = new SimpleBooleanProperty(false);
	
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

		boxTitle.prefWidthProperty().bind(this.widthProperty());
		boxTitle.minWidthProperty().bind(boxTitle.prefWidthProperty());
		boxTitle.maxWidthProperty().bind(boxTitle.prefWidthProperty());
		boxTitle.prefHeightProperty().bind(boxTitle.minHeightProperty());
		boxTitle.minHeightProperty().set(30);
		boxTitle.maxHeightProperty().bind(boxTitle.minHeightProperty());
		boxTitle.setPadding(new Insets(0,50,0,50));
		toggleGroup=new ToggleGroup();
		rbDrug.setToggleGroup(toggleGroup);
		rbSign.setToggleGroup(toggleGroup);

		boxTitleResult.prefWidthProperty().bind(this.widthProperty());
		boxTitleResult.minWidthProperty().bind(boxTitleResult.prefWidthProperty());
		boxTitleResult.maxWidthProperty().bind(boxTitleResult.prefWidthProperty());
		boxTitleResult.prefHeightProperty().bind(boxTitleResult.minHeightProperty());
		boxTitleResult.minHeightProperty().set(30);
		boxTitleResult.maxHeightProperty().bind(boxTitleResult.minHeightProperty());
		boxTitleResult.setPadding(new Insets(0,50,0,50));
		

		boxResultDiseases.prefWidthProperty().bind(this.widthProperty());
		boxResultDiseases.minWidthProperty().bind(boxResultDiseases.prefWidthProperty());
		boxResultDiseases.maxWidthProperty().bind(boxResultDiseases.prefWidthProperty());
		boxResultDiseases.disableProperty().bind(results.emptyProperty());
		boxDisResult.prefWidthProperty().bind(boxResultDiseases.widthProperty().subtract(15));
		boxDisResult.minWidthProperty().bind(boxDisResult.prefWidthProperty());
		boxDisResult.maxWidthProperty().bind(boxDisResult.prefWidthProperty());
		
		
		boxSign.prefWidthProperty().bind(boxSearch.widthProperty().divide(2).subtract(10));
		boxSign.maxWidthProperty().bind(boxSign.prefWidthProperty());
		boxSign.minWidthProperty().bind(boxSign.prefWidthProperty());
		boxSign.disableProperty().bind(rbSign.selectedProperty().not());
		boxDrug.prefWidthProperty().bind(boxSearch.widthProperty().divide(2).subtract(10));
		boxDrug.maxWidthProperty().bind(boxDrug.prefWidthProperty());
		boxDrug.minWidthProperty().bind(boxDrug.prefWidthProperty());
		boxDrug.disableProperty().bind(rbDrug.selectedProperty().not());
		

		
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
		
		
		lvSign.itemsProperty().bind(signs);
		lvDrug.itemsProperty().bind(drugs);
		cbDisResult.itemsProperty().bind(results);
		
		lvSign.setCellFactory(lv -> {
            return new ListCell<String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        Label text = new Label(item);
                        text.setPrefWidth(lvSign.getWidth()-40);
                        text.setMaxWidth(lvSign.getWidth()-40);
                        Label x = new Label("X");
                        x.setMinWidth(10);
                        x.setMaxWidth(10);
                        x.setOnMouseClicked(e->{
                        	signs.remove(item);
                        });
                        x.setTooltip(new Tooltip("Remove"));
                        setGraphic(new HBox(text,x));
                    }
                }
            };
        });
		lvDrug.setCellFactory(lv -> {
            return new ListCell<String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        Label text = new Label(item);
                        text.setPrefWidth(lvDrug.getWidth()-40);
                        text.setMaxWidth(lvDrug.getWidth()-40);
                        Label x = new Label("X");
                        x.setMinWidth(10);
                        x.setMaxWidth(10);
                        x.setOnMouseClicked(e->{
                        	drugs.remove(item);
                        });
                        x.setTooltip(new Tooltip("Remove"));
                        setGraphic(new HBox(text,x));
                    }
                }
            };
        });
		bSignAnd.setOnAction(e->{
			//TODO
			if(tfSign.textProperty().isNotEmpty().get() ){
				String text = StringUtils.capitalize(tfSign.getText().trim());
				if(!signs.contains(text)){
					signs.add(text);
				}
				tfSign.setText("");
			}
		});
		bDrugAnd.setOnAction(e->{
			if(tfSign.textProperty().isNotEmpty().get() ){
				String text = StringUtils.capitalize(tfSign.getText().trim());
				if(!signs.contains(text)){
					signs.add(text);
				}
				tfSign.setText("");
			}
		});
		//TODO
		bSignOr.setManaged(false);
		bSignOr.setVisible(false);
		bDrugOr.setManaged(false);
		bDrugOr.setVisible(false);
		/*bSignOr.setOnAction(e->{
			//TODO
		});
		bDrugOr.setOnAction(e->{
			//TODO
		});*/
		
		
		lvDisSynonym.itemsProperty().bind(synonyms);
		lvDisSymptom.itemsProperty().bind(symptoms);
		lvDisDrugIndication.itemsProperty().bind(drugIndications);
		lvDisDrugAdverseEffect.itemsProperty().bind(drugAdverseEffects);
		
		cbDisResult.valueProperty().addListener((obs,o,n)->{
			taDisDescription.setText("");
			synonyms.clear();
			symptoms.clear();
			drugIndications.clear();
			drugAdverseEffects.clear();
			if(n!=null){
				taDisDescription.setText(n.getDescription());
				synonyms.setAll(n.getSynonym());
				symptoms.setAll(n.getListSymptom());
				drugIndications.setAll(n.getListDrugIndication());
				drugAdverseEffects.setAll(n.getListDrugAdverseEffect());
			}
		});
		cbDisResult.setCellFactory(lv -> {
            return new ListCell<Disease>() {
                @Override
                protected void updateItem(Disease item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty || StringUtils.isBlank(item.getName())) {
                        setText(null);
                        setGraphic(null);
                    } else {
                    	setGraphic(new Label(item.getName()));
                    }
                }
         };});
		endResearch.bind(endCouchDB.and(endMySql).and(endText).and(endXml).and(endCSV));
	}
	private void research(){
			AccueilService.Request(lvSign.getItems());
	}
	/*private void research(){
		endCouchDB.set(false);
		endCSV.set(false);
		endMySql.set(false);
		endText.set(false);
		endXml.set(false);
		
		Alert alert = App.progressAlert("Research ...");
		Task<List<Disease>> taskCouchDB = new Task<List<Disease>>() {
			@Override
			protected List<Disease> call() throws Exception {
				return researchCouchDB();
			}
		};
		Task<List<Disease>> taskCSV = new Task<List<Disease>>() {
			@Override
			protected List<Disease> call() throws Exception {
				return researchCSV();
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
				return researchText(tfSign.getText());
			}
		};
		Task<List<Disease>> taskXml = new Task<List<Disease>>() {
			@Override
			protected List<Disease> call() throws Exception {
				return researchXml();
			}
		};
		new Thread(taskCouchDB).start();
		new Thread(taskCSV).start();
		new Thread(taskMySql).start();
		new Thread(taskText).start();
		new Thread(taskXml).start();

		lCouchDB.clear();
		lCSV.clear();
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
			System.out.println("fin search couch");
		});
		taskCouchDB.setOnCancelled(e->{
			endCouchDB.set(true);
			try{
				taskCouchDB.getException().printStackTrace();
			}catch(Exception ex){}
			new Alert(AlertType.ERROR, "Error to collect CouchDB datas.").show();
		});
		taskCSV.setOnSucceeded(e->{
			endCSV.set(true);
			try {
				lCSV.addAll(taskCSV.get());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			System.out.println("fin search CSV");
		});
		taskCSV.setOnCancelled(e->{
			endCSV.set(true);
			try{
				taskCSV.getException().printStackTrace();
			}catch(Exception ex){}
			new Alert(AlertType.ERROR, "Error to collect CSV datas.").show();
		});
		taskMySql.setOnSucceeded(e->{
			endMySql.set(true);
			try {
				lMySql.addAll(taskMySql.get());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			System.out.println("fin search MySql");
		});
		taskMySql.setOnCancelled(e->{
			endMySql.set(true);
			try{
				taskMySql.getException().printStackTrace();
			}catch(Exception ex){}
			new Alert(AlertType.ERROR, "Error to collect MySQL datas.").show();
		});
		taskText.setOnSucceeded(e->{
			endText.set(true);
			try {
				lText.addAll(taskText.get());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			System.out.println("fin search Text");
		});
		taskText.setOnCancelled(e->{
			endText.set(true);
			try{
				taskText.getException().printStackTrace();
			}catch(Exception ex){}
			new Alert(AlertType.ERROR, "Error to collect Text datas.").show();
		});
		taskXml.setOnSucceeded(e->{
			endXml.set(true);
			try {
				lXml.addAll(taskXml.get());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			System.out.println("fin search xml");
		});
		taskXml.setOnCancelled(e->{
			endXml.set(true);
			try{
				taskXml.getException().printStackTrace();
			}catch(Exception ex){}
			new Alert(AlertType.ERROR, "Error to collect XML datas.").show();
		});

		Task<List<Disease>> taskMerge = new Task<List<Disease>>() {
			@Override
			protected List<Disease> call() throws Exception {
				return mergeData(lCouchDB, lCSV, lMySql, lText, lXml);
			}
		};
		try{
			endResearch.removeListener(endResearchListener);
		}catch(Exception e){}
		endResearchListener = (obs,o,n)->{
			if(n){
				alert.setTitle("Data merging ...");
				new Thread(taskMerge).start();
			}
		};
		endResearch.addListener(endResearchListener);
		taskMerge.setOnSucceeded(e->{
			try {
				results.setAll(taskMerge.get());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			cbDisResult.valueProperty().set(null);
			System.out.println("fin merge");
        	alert.close();
		});
		taskMerge.setOnFailed(e->{
        	alert.close();
			try{
				taskText.getException().printStackTrace();
			}catch(Exception ex){}
			new Alert(AlertType.ERROR, "Error to merge datas.").show();
		});
		alert.showingProperty().addListener((obs,o,n)->{
			if(!n && o){
				if(!taskMerge.isDone()){
					alert.show();
				}
			}
		});
	}*/
	private List<Disease> mergeData(List<Disease> listCouchDB, List<Disease> lCSV ,List<Disease> listMySql, List<Disease> listText, List<Disease> listXml){
		System.out.println("Start merge data");
		return listText;
	}
	private List<Disease> researchCouchDB(){
		System.out.println("Start research CouchDB");
		return new ArrayList<Disease>();
	}
	private List<Disease> researchCSV(){
		System.out.println("Start research CSV");
		return new ArrayList<Disease>();
	}
	private List<Disease> researchMySql(){
		System.out.println("Start research MySql");
		return new ArrayList<Disease>();
	}
	private List<Disease> researchText(String s) throws IOException, ParseException{
		System.out.println("Start research Text");
		return TextService.getDiseases(s);
	}
	private List<Disease> researchXml(){
		System.out.println("Start research Xml");
		return new ArrayList<Disease>();
	}
	
}
