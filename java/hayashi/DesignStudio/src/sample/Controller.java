package sample;
 
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.*;
 
public class Controller implements Initializable{
	@FXML
	private TextField text1;
	@FXML
	private Label label1;
	@FXML
	private TextField text2;
	@FXML
	private Label label2;
	@FXML
	private TextField text3;
	@FXML
	private Label label3;
	@FXML
	private TextField text4;
	@FXML
	private Label label4;
	
    public void handlePrev(ActionEvent event) {
        Main.getInstance().prevPage();
    }
    
    public void handleNext(ActionEvent event) {
        Main.getInstance().nextPage();
    }
    
    //データ正当性の確認
    public void buttonAction(ActionEvent event){
    	//エラー格納用配列
    	List <String> error = new ArrayList<String>();
    	//氏名
    	String name = text1.getText();
    	//学籍番号
    	String s_number = text2.getText();
    	//電話番号
    	String p_number = text3.getText();
    	//メールアドレス
    	String email = text4.getText();
    	
    	//エラー確認処理
    	if(name == null || name.matches("[ ]*")){ 
    		error.add("名前がありません");
    	}
    	if(s_number == null || s_number.matches("[ ]*")){
    		error.add("学籍番号がありません");
    	}
    	if(p_number == null || p_number.matches("[ ]*")){
    		error.add("電話番号がありません");
    	}
    	if(email == null || email.matches("[ ]*")){
    		error.add("メールアドレスがありません");
    	}
    	
    	//エラー格納数が0を超えているならエラー対応
    	if(error.size() > 0){
    		//エラーの内容をfxmlにpushして視覚化する
    		for(int i = 0 ; i < error.size() ; i++){
    			System.out.println(error.get(i));
    		}
    		System.out.println();
    	}else{
    		//mainにデータを格納する（画面遷移時のデータ保持のため）
    		Main main = Main.getInstance();
    		main.setStudent_Name(name);
    		main.setStudent_Number(s_number);
    		main.setPhone_Number(p_number);
    		main.setMail_Address(email);
    		//ページ遷移用の関数（だだし、飛び先は次の内部インデックス）
    		main.nextPage();
    	}
    	
    }
    
    //セッタ
    public void set_name(String text){
    	text1.setText(text);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}
