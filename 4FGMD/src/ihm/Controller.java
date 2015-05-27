package ihm;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import org.apache.commons.lang3.StringUtils;



public abstract class Controller extends Pane{
	private App app;
	protected abstract void setStartCondition();
	public static final Pane initialise(App app, Class<? extends Controller> clazz) throws InstantiationException, IllegalAccessException{
		Controller c = clazz.newInstance();
		Pane o = (Pane) loadFxml(c);
		if(o!=null){
			c.getChildren().setAll(o.getChildren());
		}
		
		c.setStartCondition();
		c.setApp(app);
		c.getApp().setGraphic(c);
		c.prefHeightProperty().bind(c.getApp().heightProperty());
		c.prefWidthProperty().bind(c.getApp().widthProperty());
		return c;
	}
	public final App getApp(){
		return app;
	}
	private void setApp(App app){
		this.app=app;
	}
	private static AnchorPane loadFxml(Controller c){
        String fxml = getFxml(c.getClass());
        if(StringUtils.isBlank(fxml)){
        	return null;
        }
		FXMLLoader fxmlLoader = new FXMLLoader(c.getClass().getResource(fxml));
        try {
            fxmlLoader.setController(c);
            AnchorPane load = (AnchorPane)fxmlLoader.load();
			return load;
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
	}
	public static final String getFxml(Class<? extends Controller> controllerClass){
		FxmlSource fxmlSource = controllerClass.getAnnotation(FxmlSource.class);
		return fxmlSource.value();
	}
}
/*import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import org.apache.commons.lang3.StringUtils;



public abstract class Controller extends Pane{
	private App app;
	protected abstract void setStartCondition();
	public static final Controller initialise(App app, Class<? extends Controller> clazz) throws InstantiationException, IllegalAccessException{
		Controller c = loadFxml(clazz);
		c.setStartCondition();
		c.setApp(app);
		app.setGraphic(c);
		return c;
	}
	public final App getApp(){
		return app;
	}
	private void setApp(App app){
		this.app=app;
	}
	private static Controller loadFxml(Class<? extends Controller> clazz) throws InstantiationException, IllegalAccessException{
		Controller c = (Controller) clazz.newInstance();
        String fxml = getFxml(c.getClass());
        if(StringUtils.isBlank(fxml)){
        	return null;
        }
		FXMLLoader fxmlLoader = new FXMLLoader(c.getClass().getResource(fxml));
        //fxmlLoader.setRoot(c);
        //fxmlLoader.setController(c);
        try {
        		Object load = fxmlLoader.load();
				if(load instanceof Controller) {
        			return (Controller)load;
				}
            return c;
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
	}
	public static final String getFxml(Class<? extends Controller> controllerClass){
		FxmlSource fxmlSource = controllerClass.getAnnotation(FxmlSource.class);
		return fxmlSource.value();
	}
}*/

