package assignment1;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.application.Application;
import javafx.application.Platform;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;

import javafx.scene.text.Font;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.print.*;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.rtf.RTFEditorKit;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;


public class text_editor extends Application {
    private Stage primaryStage;
    private String path=null;
    boolean save = false;
    int startindex = 0;
    File file;

    @Override
    public void start(Stage primaryStage) throws Exception {
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
    private CheckMenuItem linewrapbn;

    @FXML
    private MenuItem fontbn;

    @FXML
    private MenuItem timebn;

    @FXML
    private MenuItem aboutbn;

    @FXML
    private TextArea textarea;

    public void initialize(){
        searchtbn.setDisable(true);
        replacebn.setDisable(true);
        textarea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(textarea.getLength() > 0){
                    searchtbn.setDisable(false);
                    replacebn.setDisable(false);
                }
                else{
                    searchtbn.setDisable(true);
                    replacebn.setDisable(true);
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
        }else if (!textarea.getText().isEmpty()){
            Alert warning = new Alert(Alert.AlertType.WARNING);
            warning.titleProperty().set("Warning");
            warning.headerTextProperty().set("You don't save the current content!");
            warning.show();

//            Alert warning = new Alert(Alert.AlertType.WARNING);
//            warning.titleProperty().set("Warning");
//            warning.headerTextProperty().set("You don't save the current content!");
//            warning.show();
            if (file!=null){
                saveFile(file);
            }else {
                if (saveAsFile()){
                    Stage currentStage=(Stage) anchorpane.getScene().getWindow();
                    currentStage.setTitle("Text Editor-" + file.getName());
                }
            }
        }
        Platform.exit();
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
    void OpenFile(ActionEvent event) throws IOException {
        FileChooser fileChooser =new FileChooser();
        fileChooser.setTitle("Open file");
        fileChooser.getExtensionFilters().addAll( new FileChooser.ExtensionFilter("TXT","*.txt"),
                new FileChooser.ExtensionFilter("ALL","*.*"));
        Stage currentStage=(Stage) anchorpane.getScene().getWindow();
        file=fileChooser.showOpenDialog(currentStage);
        if (file==null){
            return;
        }
        currentStage.setTitle("Text Editor-"+file.getName());
        FileInputStream fileInputStream;
        Reader reader=null;
        String filePath=file.getPath();
        String fileName = filePath.substring(filePath.lastIndexOf("\\")+1);
        String[] strArray = fileName.split("\\.");
        int suffixIndex = strArray.length -1;
        System.out.println(strArray[suffixIndex]);
        if (strArray[suffixIndex].equals("txt")){
            try{
                fileInputStream=new FileInputStream(file);
                reader=new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
                String a;
                char[] byteArray=new char[(int) file.length()];
                reader.read(byteArray);
                a=new String(byteArray);
                textarea.setText(a);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                assert reader != null;
                reader.close();
            }
        }
        if (strArray[suffixIndex].equals("rtf")){
            textarea.setText(RtfContent(filePath));
        }
        if (strArray[suffixIndex].equals("odt")){

        }

    }

    public String RtfContent(String filePath){
        String result = null;
        try{
            DefaultStyledDocument styledDocument=new DefaultStyledDocument();
            InputStream inputStream=new FileInputStream(file);
            new RTFEditorKit().read(inputStream,styledDocument,0);
            result=new String(styledDocument.getText(0,styledDocument.getLength()).getBytes("ISO8859-1"),"GBK");

        }catch (IOException | BadLocationException e){
            e.printStackTrace();
        }
        return result;
    }


    @FXML
    void PrintFile(ActionEvent event) {
        Node node=textarea;
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job!=null){
            textarea.textProperty().bind(job.jobStatusProperty().asString());
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
    void SaveAsPDF(ActionEvent event) throws IOException, DocumentException {
        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Save as");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF","*.pdf"));
        Stage currentStage=(Stage)anchorpane.getScene().getWindow();
        File newFile=fileChooser.showSaveDialog(currentStage);
        if (newFile==null){
            return;
        }
        else {
            Document document = new Document();
            OutputStream outputStream=new FileOutputStream(newFile);
            PdfWriter.getInstance(document,outputStream);
            document.open();
            BaseFont baseFont=BaseFont.createFont("C:\\Windows\\Fonts\\simfang.ttf",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
            com.itextpdf.text.Font font=new com.itextpdf.text.Font(baseFont);
            InputStreamReader inputStreamReader=new InputStreamReader(new FileInputStream(file.getPath()));
            BufferedReader b6ufferedReader=new BufferedReader(inputStreamReader);
            String str;
            while ((str = b6ufferedReader.readLine())!=null){
                document.add(new Paragraph(str,font));
            }
            document.close();
        }

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
        Label l2 = new Label("Replace text:  ");
        TextField t2 = new TextField();
        h_st2.getChildren().addAll(l2,t2);

        VBox v_st1 = new VBox();
        v_st1.getChildren().addAll(h_st1,h_st2);

        VBox v_st2 = new VBox();
        v_st2.setPadding(new Insets(20));
        v_st2.setSpacing(20);
        Button b_st1 = new Button("Search");
        Button b_st2 = new Button("Replace");
        Button b_st = new Button("Replace All");
        Button b_st3 = new Button("Cancel");
        v_st2.getChildren().addAll(b_st1,b_st2,b_st,b_st3);

        HBox h = new HBox();
        h.setSpacing(20);
        h.getChildren().addAll(v_st1,v_st2);

        Stage stage_st = new Stage();
        Scene scene = new Scene(h,500,200);
        stage_st.setResizable(false);
        stage_st.setTitle("REPLACE");
        stage_st.setScene(scene);
        stage_st.show();

        b_st1.setOnAction(event1 -> {
            String text_string = textarea.getText();
            String t1_str = t1.getText();
            String t2_str = t2.getText();
            if(!t1.getText().isEmpty()){
                if(text_string.contains(t1_str)){
                    if(startindex == -1){
                        Alert alert1 = new Alert(Alert.AlertType.WARNING);
                        alert1.titleProperty().set("WARNING");
                        alert1.headerTextProperty().set("CAN'T find the text!");
                        alert1.show();
                    }
                    startindex = textarea.getText().indexOf(t1.getText(),startindex);
                    if(startindex >= 0 && startindex < textarea.getText().length()){
                        textarea.selectRange(startindex,startindex + t1.getText().length());
                        startindex += t1.getText().length();
                    }
                    b_st2.setOnAction(event2 -> {
                        if(t2.getText() == null){
                            Alert alert1 = new Alert(Alert.AlertType.WARNING);
                            alert1.titleProperty().set("WARNING");
                            alert1.headerTextProperty().set("Replacement content is EMPTY!");
                            alert1.show();
                        }
                        else {
                                textarea.replaceSelection(t2_str);
                        }
                    });
                }
                if(!text_string.contains(t1_str)){
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

        b_st.setOnAction(event2 -> {
            String text_string = textarea.getText();
            String t1_str = t1.getText();
            String t2_str = t2.getText();
            if(!t1.getText().isEmpty()){
                if(text_string.contains(t1_str)){
                    if(startindex == -1){
                        Alert alert1 = new Alert(Alert.AlertType.WARNING);
                        alert1.titleProperty().set("WARNING");
                        alert1.headerTextProperty().set("CAN'T find the text!");
                        alert1.show();
                    }
                    Boolean flag = true;
                    while(flag){
                        startindex = textarea.getText().indexOf(t1.getText(),startindex);
                        if(startindex >= 0 && startindex < textarea.getText().length()){
                            textarea.selectRange(startindex,startindex + t1.getText().length());
                            startindex += t1.getText().length();
                            textarea.replaceSelection(t2_str);
                            flag = true;
                        }
                        else{
                            flag = false;
                        }
                    }
                }
                if(!text_string.contains(t1_str)){
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
        Scene scene1 = new Scene(gp_st,350,150);
        stage_st1.setResizable(false);
        stage_st1.setTitle("SEARCH");
        stage_st1.setScene(scene1);
        stage_st1.show();

        b1.setOnAction(event1 -> {
            String textstring = textarea.getText();
            String tf_st_str = tf_st.getText();
            if(!tf_st.getText().isEmpty()){
                if(textstring.contains(tf_st_str)){
                    if(startindex == -1){
                        Alert alert1 = new Alert(Alert.AlertType.WARNING);
                        alert1.titleProperty().set("WARNING");
                        alert1.headerTextProperty().set("CAN'T find the text!");
                        alert1.show();
                    }
                    startindex = textarea.getText().indexOf(tf_st.getText(),startindex);
                    if(startindex >= 0 && startindex < textarea.getText().length()){
                        textarea.selectRange(startindex,startindex + tf_st.getText().length());
                        startindex += tf_st.getText().length();
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
    void Line_Wrap(ActionEvent event){
        if(linewrapbn.isSelected()){
            textarea.setWrapText(true);
        }
        else {
            textarea.isWrapText();
        }
    }

    @FXML
    void Font(ActionEvent event) throws IOException {
        Stage font_stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/Font_Set.fxml"));
        Scene scene = new Scene(root);
        font_stage.setScene(scene);
        font_stage.showAndWait();

//        InputStream input = text_editor.class.getClassLoader().getResourceAsStream("text_editor.yml");
//        Yaml yaml = new Yaml();
//        Map<String, Object> object = (Map<String, Object>) yaml.load(input);

        if(font_stage.getUserData() != null){
            Font font = (Font) scene.getUserData();
            textarea.setFont(font);
        }
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
