package main;

import javafx.application.Application;
import javafx.stage.Stage;
import view.*;

public class Launcher extends Application{

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		ConnectionWindow connectionWindow = new ConnectionWindow();
		connectionWindow.show();
	}

}
