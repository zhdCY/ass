package assignment1;



import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;



public class text_editor extends Application {
    private Stage primaryStage;
    private String path=null;
    boolean save = false;
    int startindex=0;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/text_editor.fxml"));
        primaryStage.setTitle("Text Editor");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    @FXML
    private MenuItem newbn;

    @FXML
    private MenuItem openbn;

    @FXML
    private MenuItem savebn;

    @FXML
    private MenuItem printbn;

    @FXML
    private MenuItem exit;

    @FXML
    private MenuItem searchtbn;

    @FXML
    private MenuItem selectbn;

    @FXML
    private MenuItem replacebn;

    @FXML
    private MenuItem copybn;

    @FXML
    private MenuItem pastebn;

    @FXML
    private MenuItem cutbn;

    @FXML
    private MenuItem timebn;

    @FXML
    private MenuItem aboutbn;

    @FXML
    private TextArea textarea;

    @FXML
    void About(ActionEvent event) {
        Stage window=new Stage();
        VBox layout=new VBox(10);
        window.setTitle("About");
        Label label1= new Label("Team Menber: Kexin Li and Yucai.");
        Label label2=new Label("The Text Editor is created by two of us.");
        layout.getChildren().addAll(label1,label2);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout,300,100);
        window.setScene(scene);
        window.show();
    }

    @FXML
    void Copy(ActionEvent event) {

    }

    @FXML
    void Cut(ActionEvent event) {

    }

    @FXML
    void Exit(ActionEvent event) {

    }

    @FXML
    void NewFile(ActionEvent event) {
        if (save){
            textarea.setText("");
            primaryStage.setTitle("new file"+"-Text Editor");
            path=null;
        }else if (!save&&!textarea.getText().isEmpty()){
            Stage stage1=new Stage();
            VBox layout1=new VBox();
            layout1.setSpacing(5);
            Label label1=new Label("You don't save current file!");
            layout1.getChildren().addAll(label1);
            Button yes=new Button("Yes");
            Button cancel=new Button("Cancel");
            HBox layout2=new HBox();
            layout2.setSpacing(10);
            layout2.getChildren().addAll(yes,cancel);
            layout1.setAlignment(Pos.CENTER);
            layout2.setAlignment(Pos.CENTER);
            BorderPane pane=new BorderPane();
            pane.setTop(layout1);
            pane.setCenter(layout2);
            Scene scene=new Scene(pane,300,150);
            stage1.setTitle("Warning!");
            stage1.setScene(scene);
            stage1.show();
            yes.setOnAction(event1 -> {
                stage1.close();
                textarea.setText("");
                primaryStage.setTitle("new file"+"-Text Editor");
                path=null;

            });
            cancel.setOnAction(event1 -> {
                stage1.close();
            });
        }
    }

    @FXML
    void OpenFile(ActionEvent event) {
        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Choose a file and open");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT","*.txt"));
        File file=fileChooser.showOpenDialog(primaryStage);
        if (file!=null&&file.exists()){
            try{
                FileInputStream read=new FileInputStream(file);
                byte[] m=new byte[(int) file.length()];
                read.read(m);
                textarea.setText(new String(m));
                read.close();
                path=file.getPath();
                int lasttext;
                String t;
                lasttext=path.lastIndexOf("\\");
                t=path.substring(lasttext+1);
                primaryStage.setTitle(t+"-Text Editor");
            }catch (Exception e){
//                e.printStackTrace();
            }
        }
    }

    @FXML
    void Paste(ActionEvent event) {

    }

    @FXML
    void PrintFile(ActionEvent event) {

    }

    @FXML
    void Replace(ActionEvent event) {

    }

    @FXML
    void SaveFile(ActionEvent event) {

    }

    @FXML
    void SearchText(ActionEvent event) {

    }

    @FXML
    void SelectText(ActionEvent event) {

    }

    @FXML
    void Time(ActionEvent event) {
        Date currenttime=new Date();
        SimpleDateFormat time=new SimpleDateFormat("HH:mm yyyy-MM-dd");
        textarea.insertText(textarea.getCaretPosition(), time.format(currenttime));

    }

}
