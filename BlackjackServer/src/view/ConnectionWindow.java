package view;

import control.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class ConnectionWindow extends Stage{


	//UI Elements
	private Scene scene;
	private Label labelInstrucciones;


	private ConnectionController control;


	public ConnectionWindow() {

		labelInstrucciones = new Label("Esperando los clientes para iniciar el juego");

		VBox vBox = new VBox();
		vBox.getChildren().add(labelInstrucciones);
		scene = new Scene(vBox, 400,400);
		this.setScene(scene);
		control = new ConnectionController(this);

	}



}
