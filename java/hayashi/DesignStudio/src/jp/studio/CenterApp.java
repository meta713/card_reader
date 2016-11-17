package jp.studio;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CenterApp extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Label label = new Label("Welcome to javaFX!");
		label.setText("うんこべいべー");
		TextField field = new TextField();
		Button button = new Button("click me!"); 
		BorderPane pane = new BorderPane();
		pane.setTop(label);
		pane.setCenter(field);
		pane.setBottom(button);
		BorderPane.setAlignment(button, Pos.CENTER);
		BorderPane.setAlignment(label, Pos.CENTER);
		Scene scene = new Scene(pane , 300 , 300);
		primaryStage.setScene(scene);
		System.out.println(label.getText());
		primaryStage.show();

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}



