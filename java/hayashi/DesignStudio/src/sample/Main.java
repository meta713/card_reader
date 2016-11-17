package sample;
 
import javafx.application.Application;
import javafx.stage.Modality;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.event.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
 
public class Main extends Application {
    public static Main singleton;
    private Parent root;
   /* private  String[] PAGE = new String[] {
        "sample.fxml",
        "sample2.fxml",
        "sample3.fxml"
    };
    */
    private  String[] PAGE = new String[] {
            "sample4.fxml",
            "sample2.fxml",
            "sample3.fxml"
    };
    private  int page_index =0;
    private  int page_num = 3;
    private Stage stage;
    //氏名
    private String student_name;
    //学籍番号
    private String student_number;
    //電話番号
    private String phone_number;
    //メールアドレス
    private String mail_address;
    //操作用のTextField要素
    private TextField field[] = new TextField[4];
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        singleton = this;
        stage = primaryStage;
        Parent root1 = FXMLLoader.load(getClass().getResource("sample.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample4.fxml"));
        root = fxmlLoader.load();
        Controller controller = (Controller) fxmlLoader.getController();
        controller.set_name("うんこちんちん");
        stage.setTitle("タイトル");
        stage.setScene(new Scene(root, 360, 220));
        Stage secondStage = new Stage();
        secondStage.setTitle("Second Stage");
        secondStage.setScene(new Scene(root1,300,300));
        
        secondStage.initModality(Modality.WINDOW_MODAL);
        //secondStage.initOwner(((Node)event.getSource()).getScene().getWindow());
        
        secondStage.show();
        stage.show();
    }
 
    public  void nextPage(){
        page_index++;
        page_index %= page_num;
        setPage();
    }
 
    public void prevPage(){
        page_index--;
        if (page_index<0){
            page_index += 3;
        }
        page_index %= page_num;
        setPage();
    }
 
    private void setPage(){
        try {
            root = FXMLLoader.load(getClass().getResource(PAGE[page_index]));
            //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PAGE[page_index]));
            stage.setTitle(PAGE[page_index]);
            stage.setScene(new Scene(root, 360, 220));
            System.out.print(PAGE[page_index] + " : ");
            System.out.print(student_name + " ");
            System.out.print(student_number + " ");
            System.out.print(phone_number + " ");
            System.out.println(mail_address);
            //final Controller controller = (Controller) fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //氏名のセッタ
    public void setStudent_Name(String name){
    	this.student_name = name;
    }
    
    //学籍番号のセッタ
    public void setStudent_Number(String s_number){
    	this.student_number = s_number;
    }
    
    //電話番号のセッタ
    public void setPhone_Number(String p_number){
    	this.phone_number = p_number;
    }
    
    //メールアドレスのセッタ
    public void setMail_Address(String email){
    	this.mail_address = email;
    }
 
    public  static Main getInstance(){
        return singleton;
    }
 
 
    public static void main(String[] args) {
        launch(args);
    }
}
