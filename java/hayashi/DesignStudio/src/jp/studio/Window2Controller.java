package jp.studio;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Window2Controller {
	@FXML
	private Label label1;
	@FXML
	private TextField text1;
	
	@FXML
	protected void buttonAction(ActionEvent event){
		String str = text1.getText();
		label1.setText("you typed : '" + str + "'");
	}
}
