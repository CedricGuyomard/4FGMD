package ihm;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
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
import javafx.util.StringConverter;
import model.Disease;
import model.Drug;

import org.apache.commons.lang3.StringUtils;

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
	

	
	@FXML VBox boxResultDiseases2;
	@FXML ComboBox<Drug>  cbDisResult2;
	@FXML TextArea taDisDescription2;
	@FXML HBox boxDisResult2;
	@FXML VBox boxDisDrugIndication2;
	@FXML ListView<Disease> lvDisDrugIndication2;
	@FXML VBox boxDisDrugAdverseEffect2;
	@FXML ListView<Disease> lvDisDrugAdverseEffect2;
	
	@FXML Button search;
	
	ListProperty<String> signs=new SimpleListProperty<String>(FXCollections.observableArrayList());
	ListProperty<String> drugs=new SimpleListProperty<String>(FXCollections.observableArrayList());
	ListProperty<Disease> results=new SimpleListProperty<Disease>(FXCollections.observableArrayList());
	ListProperty<Drug> results2=new SimpleListProperty<Drug>(FXCollections.observableArrayList());
	
	ListProperty<String> synonyms=new SimpleListProperty<String>(FXCollections.observableArrayList());
	ListProperty<Disease> symptoms=new SimpleListProperty<Disease>(FXCollections.observableArrayList());
	ListProperty<Drug> drugIndications=new SimpleListProperty<Drug>(FXCollections.observableArrayList());
	ListProperty<Drug> drugAdverseEffects=new SimpleListProperty<Drug>(FXCollections.observableArrayList());
	ListProperty<Disease> indications=new SimpleListProperty<Disease>(FXCollections.observableArrayList());
	ListProperty<Disease> adverseEffects=new SimpleListProperty<Disease>(FXCollections.observableArrayList());
	
	@Override
	protected void setStartCondition() {
		
		boxSearch.prefWidthProperty().bind(this.prefWidthProperty());
		boxSearch.minWidthProperty().bind(boxSearch.prefWidthProperty());
		boxSearch.maxWidthProperty().bind(boxSearch.prefWidthProperty());
		boxSearch.prefHeightProperty().set(4000);
		boxSearch.minHeightProperty().set(150);
		boxSearch.maxHeightProperty().bind(this.heightProperty().multiply(2/5));

		boxParam.prefWidthProperty().bind(this.prefWidthProperty());
		boxParam.minWidthProperty().bind(boxParam.prefWidthProperty());
		boxParam.maxWidthProperty().bind(boxParam.prefWidthProperty());
		boxParam.prefHeightProperty().bind(boxParam.minHeightProperty());
		boxParam.minHeightProperty().set(30);
		boxParam.maxHeightProperty().bind(boxParam.minHeightProperty());

		boxTitle.prefWidthProperty().bind(this.prefWidthProperty());
		boxTitle.minWidthProperty().bind(boxTitle.prefWidthProperty());
		boxTitle.maxWidthProperty().bind(boxTitle.prefWidthProperty());
		boxTitle.prefHeightProperty().bind(boxTitle.minHeightProperty());
		boxTitle.minHeightProperty().set(30);
		boxTitle.maxHeightProperty().bind(boxTitle.minHeightProperty());
		boxTitle.setPadding(new Insets(0,50,0,50));
		toggleGroup=new ToggleGroup();
		rbDrug.setToggleGroup(toggleGroup);
		rbSign.setToggleGroup(toggleGroup);

		boxTitleResult.prefWidthProperty().bind(this.prefWidthProperty());
		boxTitleResult.minWidthProperty().bind(boxTitleResult.prefWidthProperty());
		boxTitleResult.maxWidthProperty().bind(boxTitleResult.prefWidthProperty());
		boxTitleResult.prefHeightProperty().bind(boxTitleResult.minHeightProperty());
		boxTitleResult.minHeightProperty().set(30);
		boxTitleResult.maxHeightProperty().bind(boxTitleResult.minHeightProperty());
		boxTitleResult.setPadding(new Insets(0,50,0,50));
		
		
		
		boxSign.prefWidthProperty().bind(boxSearch.widthProperty().divide(2).subtract(10));
		boxSign.maxWidthProperty().bind(boxSign.prefWidthProperty());
		boxSign.minWidthProperty().bind(boxSign.prefWidthProperty());
		boxSign.disableProperty().bind(rbSign.selectedProperty().not());
		boxDrug.prefWidthProperty().bind(boxSearch.widthProperty().divide(2).subtract(10));
		boxDrug.maxWidthProperty().bind(boxDrug.prefWidthProperty());
		boxDrug.minWidthProperty().bind(boxDrug.prefWidthProperty());
		boxDrug.disableProperty().bind(rbDrug.selectedProperty().not());
		
		

		boxResult.prefWidthProperty().bind(this.prefWidthProperty());
		boxResult.minWidthProperty().bind(boxResult.prefWidthProperty());
		boxResult.maxWidthProperty().bind(boxResult.prefWidthProperty());
		boxResult.prefHeightProperty().set(4000);
		boxResult.minHeightProperty().set(300);
		//boxResult.maxHeightProperty().bind(this.prefWidthProperty().multiply(3/5));

		boxResultDiseases.prefWidthProperty().bind(boxResult.widthProperty());
		boxResultDiseases.minWidthProperty().bind(boxResultDiseases.prefWidthProperty());
		boxResultDiseases.maxWidthProperty().bind(boxResultDiseases.prefWidthProperty());
		boxResultDiseases.prefHeightProperty().bind(boxResult.heightProperty().subtract(20));
		boxResultDiseases.minHeightProperty().bind(boxResultDiseases.prefHeightProperty());
		boxResultDiseases.maxHeightProperty().bind(boxResultDiseases.prefHeightProperty());
		
		boxResultDiseases.disableProperty().bind(results.emptyProperty());
		
		boxDisResult.prefWidthProperty().bind(boxResultDiseases.widthProperty().subtract(15));
		boxDisResult.minWidthProperty().bind(boxDisResult.prefWidthProperty());
		boxDisResult.maxWidthProperty().bind(boxDisResult.prefWidthProperty());
		boxDisResult.prefHeightProperty().bind(boxResultDiseases.prefHeightProperty().subtract(15));
		boxDisResult.minHeightProperty().bind(boxDisResult.prefHeightProperty());
		boxDisResult.maxHeightProperty().bind(boxDisResult.prefHeightProperty());
		

		
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
		

		
		boxDisDrugIndication2.prefWidthProperty().bind(boxDisResult2.widthProperty().divide(2).subtract(30/2));
		boxDisDrugIndication2.maxWidthProperty().bind(boxDisDrugIndication2.prefWidthProperty());
		boxDisDrugIndication2.minWidthProperty().bind(boxDisDrugIndication2.prefWidthProperty());
		boxDisDrugAdverseEffect2.prefWidthProperty().bind(boxDisResult2.widthProperty().divide(2).subtract(30/2));
		boxDisDrugAdverseEffect2.maxWidthProperty().bind(boxDisDrugAdverseEffect2.prefWidthProperty());
		boxDisDrugAdverseEffect2.minWidthProperty().bind(boxDisDrugAdverseEffect2.prefWidthProperty());


		boxResultDiseases2.prefWidthProperty().bind(boxResult.widthProperty());
		boxResultDiseases2.minWidthProperty().bind(boxResultDiseases2.prefWidthProperty());
		boxResultDiseases2.maxWidthProperty().bind(boxResultDiseases2.prefWidthProperty());
		boxResultDiseases2.prefHeightProperty().bind(boxResult.heightProperty().subtract(20));
		boxResultDiseases2.minHeightProperty().bind(boxResultDiseases2.prefHeightProperty());
		boxResultDiseases2.maxHeightProperty().bind(boxResultDiseases2.prefHeightProperty());
		
		boxResultDiseases2.disableProperty().bind(results2.emptyProperty());
		
		boxDisResult2.prefWidthProperty().bind(boxResultDiseases2.widthProperty().subtract(15));
		boxDisResult2.minWidthProperty().bind(boxDisResult2.prefWidthProperty());
		boxDisResult2.maxWidthProperty().bind(boxDisResult2.prefWidthProperty());
		boxDisResult2.prefHeightProperty().bind(boxResultDiseases2.prefHeightProperty().subtract(15));
		boxDisResult2.minHeightProperty().bind(boxDisResult2.prefHeightProperty());
		boxDisResult2.maxHeightProperty().bind(boxDisResult2.prefHeightProperty());
		
		/*boxDisSynonym.prefHeightProperty().bind(boxDisResult.heightProperty().subtract(30));
		boxDisSynonym.maxHeightProperty().bind(boxDisSynonym.prefHeightProperty());
		boxDisSynonym.minHeightProperty().bind(boxDisSynonym.prefHeightProperty());
		boxDisSymptom.prefHeightProperty().bind(boxDisSynonym.prefHeightProperty());
		boxDisSymptom.maxHeightProperty().bind(boxDisSymptom.prefHeightProperty());
		boxDisSymptom.minHeightProperty().bind(boxDisSymptom.prefHeightProperty());
		boxDisDrugIndication.prefHeightProperty().bind(boxDisSynonym.prefHeightProperty());
		boxDisDrugIndication.maxHeightProperty().bind(boxDisDrugIndication.prefHeightProperty());
		boxDisDrugIndication.minHeightProperty().bind(boxDisDrugIndication.prefHeightProperty());
		boxDisDrugAdverseEffect.prefHeightProperty().bind(boxDisSynonym.prefHeightProperty());
		boxDisDrugAdverseEffect.maxHeightProperty().bind(boxDisDrugAdverseEffect.prefHeightProperty());
		boxDisDrugAdverseEffect.minHeightProperty().bind(boxDisDrugAdverseEffect.prefHeightProperty());*/
		
		boxResultDiseases.visibleProperty().bind(rbSign.selectedProperty());
		boxResultDiseases.managedProperty().bind(rbSign.selectedProperty());
		boxResultDiseases2.visibleProperty().bind(rbDrug.selectedProperty());
		boxResultDiseases2.managedProperty().bind(rbDrug.selectedProperty());
		
		search.setOnAction(e->{
			research();
		});
		search.disableProperty().bind(Bindings.or(rbDrug.selectedProperty().and(drugs.emptyProperty()), rbSign.selectedProperty().and(signs.emptyProperty())));
		
		lvSign.itemsProperty().bind(signs);
		lvDrug.itemsProperty().bind(drugs);
		cbDisResult.itemsProperty().bind(results);
		cbDisResult2.itemsProperty().bind(results2);
		
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

		lvDisDrugIndication2.itemsProperty().bind(indications);
		lvDisDrugAdverseEffect2.itemsProperty().bind(adverseEffects);
		
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
		cbDisResult.setConverter(new StringConverter<Disease>() {
			@Override
			public String toString(Disease object) {
				return object.getName();
			}
			@Override
			public Disease fromString(String string) {
				return null;
			}
		});
		//TODO faire une popover pour description medicament/origine symptome ...
		
		lvDisSymptom.setCellFactory(lv -> {
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
		lvDisDrugIndication.setCellFactory(lv -> {
            return new ListCell<Drug>() {
                @Override
                protected void updateItem(Drug item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty || StringUtils.isBlank(item.getName())) {
                        setText(null);
                        setGraphic(null);
                    } else {
                    	setGraphic(new Label(item.getName()));
                    }
                }
         };});
		lvDisDrugAdverseEffect.setCellFactory(lv -> {
            return new ListCell<Drug>() {
                @Override
                protected void updateItem(Drug item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty || StringUtils.isBlank(item.getName())) {
                        setText(null);
                        setGraphic(null);
                    } else {
                    	setGraphic(new Label(item.getName()));
                    }
                }
         };});
		
		cbDisResult2.valueProperty().addListener((obs,o,n)->{
			taDisDescription2.setText("");
			indications.clear();
			adverseEffects.clear();
			if(n!=null){
				taDisDescription2.setText(n.getDescription());
				adverseEffects.setAll(n.getListAdverseEffectDisease());
				indications.setAll(n.getListIndicationDisease());
			}
		});
		cbDisResult2.setCellFactory(lv -> {
            return new ListCell<Drug>() {
                @Override
                protected void updateItem(Drug item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty || StringUtils.isBlank(item.getName())) {
                        setText(null);
                        setGraphic(null);
                    } else {
                    	setGraphic(new Label(item.getName()));
                    }
                }
         };});
		cbDisResult2.setConverter(new StringConverter<Drug>() {
			@Override
			public String toString(Drug object) {
				return object.getName();
			}
			@Override
			public Drug fromString(String string) {
				return null;
			}
		});
		
		
		lvDisDrugIndication2.setCellFactory(lv -> {
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
		lvDisDrugAdverseEffect2.setCellFactory(lv -> {
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
		
	}
	private void research(){
		results.clear();
		results2.clear();
		if(rbSign.isSelected()){
		//trickyMove short
		results.setAll(AccueilService.requestDisease(signs).stream().sorted((e1,e2)->{
			if(e1!=null && e2 !=null){
				int le1 = e1.getListDrugAdverseEffect().size()+e1.getSynonym().size()+e1.getListSymptom().size()+e1.getListDrugIndication().size();
				int le2 = e2.getListDrugAdverseEffect().size()+e2.getSynonym().size()+e2.getListSymptom().size()+e2.getListDrugIndication().size();
				return le2 - le1 ;
			}
			return 1;
		}).collect(Collectors.toList()));
		}else{
			//trickyMove short
			results2.setAll(AccueilService.requestDrug(drugs).stream().sorted((e1,e2)->{
				if(e1!=null && e2 !=null){
					int le1 = e1.getListIndicationDisease().size()+e1.getListAdverseEffectDisease().size();
					int le2 = e2.getListIndicationDisease().size()+e2.getListAdverseEffectDisease().size();
					return le2 - le1 ;
				}
				return 1;
			}).collect(Collectors.toList()));
		}
	}
	
}
