package assignment1;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
//import javafx.print.Paper;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.HBox;

import javafx.scene.layout.VBox;
//import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.print.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
//import javafx.scene.Node;


import java.awt.*;
//import java.awt.print.PrinterException;
//import java.awt.print.PrinterJob;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;


public class text_editor extends Application {
    private Stage primaryStage;
    private String path=null;
    boolean save = false;
    int startindex=0;
    File file;

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
    private AnchorPane anchorpane;
    @FXML
    private MenuItem newbn;

    @FXML
    private MenuItem openbn;

    @FXML
    private MenuItem savebn;

    @FXML
    private MenuItem printbn;

    @FXML
    private MenuItem SaveAsPDF;

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

    int startIndex = 0;

    public void initialize(){
        searchtbn.setDisable(true);
        selectbn.setDisable(true);
        textarea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(textarea.getLength() > 0){
                    searchtbn.setDisable(false);
                    selectbn.setDisable(false);
                }
                else{
                    searchtbn.setDisable(true);
                    selectbn.setDisable(true);
                }
            }
        });
    }


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
        Clipboard cb = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        String text = textarea.getSelectedText();
        content.putString(text);
        cb.setContent(content);
    }

    @FXML
    void Paste(ActionEvent event) {
        Clipboard cb2 = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        Clipboard cb3 = Clipboard.getSystemClipboard();
        if(cb3.hasContent(DataFormat.PLAIN_TEXT)){
            String s = cb3.getContent(DataFormat.PLAIN_TEXT).toString();
            if(textarea.getSelectedText() != null){
                textarea.replaceSelection(s);
            }
            else{
                int mouse = textarea.getCaretPosition();
                textarea.insertText(mouse,s);
            }
        }
    }

    @FXML
    void Cut(ActionEvent event) {
        Clipboard cb4 = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        String text = textarea.getSelectedText();
        content.putString(text);
        cb4.setContent(content);
        textarea.replaceSelection("");
    }

    @FXML
    void Exit(ActionEvent event) throws IOException {
        if (save){
            Platform.exit();
        }else if (!save && !textarea.getText().isEmpty()){
            Alert warning = new Alert(Alert.AlertType.WARNING);
            warning.titleProperty().set("Warning");
            warning.headerTextProperty().set("You don't save the current content!");
            warning.show();
        }
    }

    @FXML
    void NewFile(ActionEvent event) throws IOException {
        Alert alert=new Alert(Alert.AlertType.INFORMATION,"If you save current file",new ButtonType("yes", ButtonBar.ButtonData.YES),new ButtonType("no",ButtonBar.ButtonData.NO));
        alert.setHeaderText("Warning");
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get().getButtonData().equals(ButtonBar.ButtonData.YES)){
            if(file !=null)
                saveFile(file);
        }else{
            return;
        }

        textarea.setText("");
        file = null;
        Stage currentStage = (Stage)anchorpane.getScene().getWindow();
        currentStage.setTitle("Text Editor-"+"Unnamed");

    }

    @FXML
    void OpenFile(ActionEvent event) {
//        FileChooser fileChooser =new FileChooser();
//        fileChooser.setTitle("Open file");
//        fileChooser.getExtensionFilters().addAll( new FileChooser.ExtensionFilter("TXT","*.txt"),
//                new FileChooser.ExtensionFilter("ALL","*.*"));
//        Stage currentStage=(Stage) anchorpane.getScene().getWindow();
//        file=fileChooser.showOpenDialog(currentStage);
//        if (file==null){
//            return;
//        }
//        currentStage.setTitle("Text Editor-"+file.getName());

        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Choose a file and open");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT","*.txt"),
                new FileChooser.ExtensionFilter("ALL","*.*"));
        file=fileChooser.showOpenDialog(primaryStage);
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
    void PrintFile(ActionEvent event) {
        Node node=textarea;
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job!=null){
            textarea.textProperty().bind(job.jobStatusProperty().asString());
//            System.out.println(job.jobStatusProperty().asString());
            boolean printed =job.printPage(node);
            if (printed){
                job.endJob();
            }else{
                textarea.textProperty().unbind();
                textarea.setText("Printing failed.");
//                System.out.println("Printing failed.");
            }
        }else{
            textarea.setText("Could not create a printer job.");
//            System.out.println("Could not create a printer job.");
        }


    }



    @FXML
    void SaveFile(ActionEvent event) {
        if(path==null){
            FileChooser fileChooser=new FileChooser();
            fileChooser.setTitle("Choose a file and save");
            File file=fileChooser.showSaveDialog(primaryStage);
            if (file!=null &&file.exists()){
                try{
                    FileOutputStream print=new FileOutputStream(file);
                    print.write(textarea.getText().getBytes(StandardCharsets.UTF_8));
                    print.flush();
                    print.close();
                    save=true;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

    @FXML
    void SaveAsPDF(ActionEvent event) throws IOException {
        PDDocument document=new PDDocument();
        PDPage page=new PDPage(PDRectangle.A4);
        document.addPage(page);
        PDPageContentStream stream=new PDPageContentStream(document,page);
        stream.setNonStrokingColor(Color.BLACK);
//        stream.addRect(29, 797, 100, 14);
        stream.fill();
        stream.beginText();
//        stream.setLeading(18f);
//        stream.newLineAtOffset(30, 800);
        stream.showText(textarea.getText());
        stream.endText();
        stream.close();
        document.close();


    }

    @FXML
    void SaveFile(ActionEvent event) throws IOException {
        if (file!=null){
            saveFile(file);
        }else {
            if (saveAsFile()){
                Stage currentStage=(Stage) anchorpane.getScene().getWindow();
                currentStage.setTitle("Text Editor-" + file.getName());
            }
        }

    }

    @FXML
    void Replace(ActionEvent event) {
        HBox h_st1 = new HBox();
        h_st1.setPadding(new Insets(20));
        h_st1.setSpacing(5);
        Label l1 = new Label("Search text:  ");
        TextField t1 = new TextField();
        h_st1.getChildren().addAll(l1,t1);

        HBox h_st2 = new HBox();
        h_st2.setPadding(new Insets(20));
        h_st2.setSpacing(5);
        Label l2 = new Label("Select text:  ");
        TextField t2 = new TextField();
        h_st2.getChildren().addAll(l2,t2);

        VBox v_st1 = new VBox();
        v_st1.getChildren().addAll(h_st1,h_st2);

        VBox v_st2 = new VBox();
        v_st2.setPadding(new Insets(20));
        v_st2.setSpacing(20);
        Button b_st1 = new Button("Search");
        Button b_st2 = new Button("Select");
        Button b_st3 = new Button("Cancel");
        v_st2.getChildren().addAll(b_st1,b_st2,b_st3);

        HBox h = new HBox();
        h.setSpacing(20);
        h.getChildren().addAll(v_st1,v_st2);

        Stage stage_st = new Stage();
        Scene scene = new Scene(h,400,150);
        stage_st.setTitle("SELECT");
        stage_st.setScene(scene);
        stage_st.show();


        b_st1.setOnAction(event1 -> {
            String textstring = textarea.getText();
            String t1_str = t1.getText();
            String t2_str = t2.getText();
            if(!t1.getText().isEmpty()){
                if(textstring.contains(t1_str)){
                    if(startIndex == -1){
                        Alert alert1 = new Alert(Alert.AlertType.WARNING);
                        alert1.titleProperty().set("WARNING");
                        alert1.headerTextProperty().set("CAN'T find the text!");
                        alert1.show();
                    }
                    startIndex = textarea.getText().indexOf(t1.getText(),startIndex);
                    if(startIndex >= 0 && startIndex < textarea.getText().length()){
                        textarea.selectRange(startIndex,startIndex + t1.getText().length());
                        startIndex += t1.getText().length();
                    }
                    b_st2.setOnAction(event2 -> {
                        textarea.replaceSelection(t2_str);
                    });
                }
                if(!textstring.contains(t1_str)){
                    Alert alert1 = new Alert(Alert.AlertType.WARNING);
                    alert1.titleProperty().set("WARNING");
                    alert1.headerTextProperty().set("CAN'T find the text!");
                    alert1.show();
                }
            }else if(t1.getText().isEmpty()){
                Alert alert1 = new Alert(Alert.AlertType.WARNING);
                alert1.titleProperty().set("ERROR");
                alert1.headerTextProperty().set("Input is EMPTY!");
                alert1.show();
            }
        });
        b_st3.setOnAction(event1 ->  {
            stage_st.close();
        });

    }

    @FXML
    void SearchText(ActionEvent event) {
        GridPane gp_st = new GridPane();
        gp_st.setPadding(new Insets(40, 25, 25, 25));
        Label label_st = new Label("Search text: ");
        TextField tf_st = new TextField();
        Button b1 = new Button("Search");
        Button b2 = new Button("Cancel");
        gp_st.add(label_st,1,0);
        gp_st.add(tf_st,2,0);
        gp_st.add(b1,2,1);
        gp_st.add(b2,3,1);
        gp_st.setVgap(10.0);

        Stage stage_st1 = new Stage();
        stage_st1.setTitle("SEARCH");
        Scene scene1 = new Scene(gp_st,350,150);
        stage_st1.setScene(scene1);
        stage_st1.show();

        b1.setOnAction(event1 -> {
            String textstring = textarea.getText();
            String tf_st_str = tf_st.getText();
            if(!tf_st.getText().isEmpty()){
                if(textstring.contains(tf_st_str)){
                    if(startIndex == -1){
                        Alert alert1 = new Alert(Alert.AlertType.WARNING);
                        alert1.titleProperty().set("WARNING");
                        alert1.headerTextProperty().set("CAN'T find the text!");
                        alert1.show();
                    }
                    startIndex = textarea.getText().indexOf(tf_st.getText(),startIndex);
                    if(startIndex >= 0 && startIndex < textarea.getText().length()){
                        textarea.selectRange(startIndex,startIndex + tf_st.getText().length());
                        startIndex += tf_st.getText().length();
                    }
                }
                if(!textstring.contains(tf_st_str)){
                    Alert alert1 = new Alert(Alert.AlertType.WARNING);
                    alert1.titleProperty().set("WARNING");
                    alert1.headerTextProperty().set("CAN'T find the text!");
                    alert1.show();
                }
            }else if(tf_st.getText().isEmpty()){
                Alert alert1 = new Alert(Alert.AlertType.WARNING);
                alert1.titleProperty().set("ERROR");
                alert1.headerTextProperty().set("Input is EMPTY!");
                alert1.show();
            }
        });

        b2.setOnAction(event1 ->  {
            stage_st1.close();
        });
    }

    @FXML
    void SelectText(ActionEvent event) {
        textarea.selectAll();
    }

    @FXML
    void Time(ActionEvent event) {
        Date currenttime=new Date();
        SimpleDateFormat time=new SimpleDateFormat("HH:mm yyyy-MM-dd");
        textarea.insertText(textarea.getCaretPosition(), time.format(currenttime));

    }

    private boolean saveFile(File file) throws IOException{
        String content=textarea.getText();
        Writer writer=null;
        try{
            writer=new FileWriter(file);
            char[] byteArray=content.toCharArray();
            writer.write(byteArray);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            writer.close();
        }
    }

    protected boolean saveAsFile() throws IOException{
        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Save as");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT","*.txt"),
                new FileChooser.ExtensionFilter("ALL","*.*"));
        Stage currentStage=(Stage)anchorpane.getScene().getWindow();
        File newFile=fileChooser.showSaveDialog(currentStage);
        if (newFile==null){
            return false;
        }
        saveFile(newFile);
        file=newFile;
        System.out.println(newFile.getName());
        save=true;
        return true;
    }

}