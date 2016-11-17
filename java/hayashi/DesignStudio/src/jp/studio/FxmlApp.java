package jp.studio;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.fxml.FXMLLoader;

public class FxmlApp extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Parent root = FXMLLoader.load(getClass().getResource("window.fxml"));
		Scene scene = new Scene(root , 300 , 150);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
