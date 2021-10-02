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
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;



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

    }

    @FXML
    void OpenFile(ActionEvent event) {

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
    }

}
