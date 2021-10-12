package assignment1;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;

import java.awt.*;
import java.io.InputStream;
import java.util.Map;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.yaml.snakeyaml.Yaml;

public class Font_Set {
    @FXML
    private ListView<String> list1;

    @FXML
    private ListView<String> list2;

    @FXML
    private ListView<String> list3;

    @FXML
    private TextField font_text;

    @FXML
    private TextField shape_text;

    @FXML
    private TextField size_text;

    @FXML
    private TextArea textarea;

    @FXML
    private Button surebn;

    @FXML
    private Button cancelbn;

    InputStream input = text_editor.class.getClassLoader().getResourceAsStream("text_editor.yml");
    Yaml yaml = new Yaml();
    Map<String, Object> object = (Map<String, Object>) yaml.load(input);

//    public Font Txtfont = Font.font((String) object.get("style"), FontWeight.valueOf((String) object.get("shape")),(int) object.get("size"));
//    private Font font =Font.font((String) object.get("style"), FontWeight.valueOf((String) object.get("shape")),(int) object.get("size"));

    public Font Txtfont = Font.font(12);
    private Font font = Font.font(12);

    String type = (String) object.get("style");
    String shape = (String) object.get("shape");
    int size = (int) object.get("size");

    @FXML
    void initialize(){
        ObservableList<String> fontname = FXCollections.observableArrayList(Font.getFontNames());
        list1.getItems().addAll(fontname);

        list2.getItems().addAll("normal","italic","bold","italic_bold");

        for(int i = 8;i <= 30;i = i + 2){
            list3.getItems().add(i+"");
        }

        list1.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        list1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                type = (String) list1.getSelectionModel().getSelectedItem();
                font_text.setText(list1.getSelectionModel().getSelectedItem());
                setFont();
            }
        });

        list2.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        list2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                shape = (String) list2.getSelectionModel().getSelectedItem();
                shape_text.setText(list2.getSelectionModel().getSelectedItem());
                setFont();
            }
        });

        list3.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        list3.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                size = Integer.parseInt(list3.getSelectionModel().getSelectedItem());
                size_text.setText(list3.getSelectionModel().getSelectedItem());
                setFont();
            }
        });
    }
    public void setFont(){
        font = Font.font(type,size);
        if(shape.equals("normal")){
            font = Font.font(type,FontWeight.NORMAL,FontPosture.REGULAR,size);
        }
        else if(shape.equals("italic")){
            font = Font.font(type,FontWeight.NORMAL,FontPosture.ITALIC,size);
        }
        else if(shape.equals("bold")){
            font = Font.font(type,FontWeight.BOLD,FontPosture.REGULAR,size);
        }
        else if(shape.equals("italic_bold")){
            font = Font.font(type,FontWeight.BOLD,FontPosture.ITALIC,size);
        }
        textarea.setFont(font);
    }

    @FXML
    void Sure(ActionEvent event){
        Txtfont = font;
        Scene scene = surebn.getScene();
        scene.setUserData(Txtfont);
        Stage stage = (Stage) surebn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void Cancel(ActionEvent event){
        Stage stage = (Stage) cancelbn.getScene().getWindow();
        stage.close();
    }

}
