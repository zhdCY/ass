package assignment1;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class text_editor extends Application {
    private Stage primaryStage;

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
    private MenuItem timebn;

    @FXML
    private MenuItem displayw;

    @FXML
    private TextArea text;

    @FXML
    void AboutOnAction(ActionEvent event) {

    }

    @FXML
    void ExitFileOnAction(ActionEvent event) {

    }

    @FXML
    void NewFileOnAction(ActionEvent event) {

    }

    @FXML
    void OpenFileOnAction(ActionEvent event) {

    }

    @FXML
    void PrintFileOnAction(ActionEvent event) {

    }

    @FXML
    void SaveFileOnAction(ActionEvent event) {

    }

    @FXML
    void SearchTextOnAction(ActionEvent event) {

    }

    @FXML
    void TimeOnAction(ActionEvent event) {

    }


}
