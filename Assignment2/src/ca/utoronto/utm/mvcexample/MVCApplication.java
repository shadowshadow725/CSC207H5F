package ca.utoronto.utm.mvcexample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MVCApplication extends Application {
	// REMEMBER: To run this in the lab put 
	// --module-path "/usr/share/openjfx/lib" --add-modules javafx.controls,javafx.fxml
	// in the run configuration under VM arguments.
	// You can import the JavaFX.prototype launch configuration and use it as well. 
	
	@Override
	public void start(Stage stage) throws Exception {
	// Create and hook up the Model, View and the controller
		
		// MODEL
		MCounter mcounter = new MCounter();
		
		// CONTROLLER
		// CONTROLLER->MODEL hookup
		// https://docs.oracle.com/javase/8/javafx/events-tutorial/processing.htm#CEGJAAFD
		// https://docs.oracle.com/javase/8/javafx/api/javafx/event/Event.html
		CButtonPressEventHandler cpresshandler=new CButtonPressEventHandler(mcounter);
	
		// VIEW COMPONENTS
		Button vIncButton= new Button("increment");
		Button vDecButton= new Button("decrement"); 
		
		VCount vCount = new VCount();
		VParity vParity = new VParity();

		// VIEW LAYOUT
		HBox root = new HBox();
		root.setPadding(new Insets(5));
		root.getChildren().addAll(vIncButton, vDecButton, vCount, vParity);
		
		// VIEW SCENE
		Scene scene = new Scene(root); 
		// Scene scene = new Scene(root, 200, 200); 

		// VIEW STAGE
		stage.setTitle("Hi Bye");
		stage.setScene(scene);
		
		// VIEW->CONTROLLER hookup
		// Note, for code below, could have had two different
		// controllers, one for each button.
		vIncButton.setOnAction(cpresshandler);
		vDecButton.setOnAction(cpresshandler);
		
		// MODEL->VIEW hookup
		mcounter.attach(vCount);
		mcounter.attach(vParity);
				
		// LAUNCH THE GUI
		stage.show();	
	}

	public static void main(String[] args) {
		launch(args);
	}
}
