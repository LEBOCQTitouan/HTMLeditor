package com.tp2.htmleditor;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;

public class HtmlEditorController {
    /* FXML */
    @FXML
    AnchorPane content;
    @FXML
    TabPane contentTabPane;
    @FXML
    Hyperlink createFileLink, openFileLink;
    @FXML
    Button newFile, openFile;
    @FXML
    VBox noFileOpenedDialogue;
    /* end FXML */

    public void createFileAction(Event event) {
        event.consume();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select where to create the file.");
        FileChooser.ExtensionFilter extFiler = new FileChooser.ExtensionFilter("HTML file (*.html)", "*.html");
        File file = fileChooser.showSaveDialog(openFileLink.getScene().getWindow());
    }

    public void openFileAction(Event event) {
        event.consume();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select the file you want to open.");
        FileChooser.ExtensionFilter extFiler = new FileChooser.ExtensionFilter("HTML file (*.html)", "*.html");
        File file = fileChooser.showOpenDialog(createFileLink.getScene().getWindow());
    }

    public void saveFileAction(Event event) {

    }

    public void saveAsAction(Event event) {

    }

    public void createHtmlEditorTab() {
        HtmlEditorTab test = new HtmlEditorTab();
        System.out.println(test);

        contentTabPane.getTabs().add(test);
        contentTabPane.setManaged(true);
        noFileOpenedDialogue.setVisible(false);
    }

    public void initialize() {
        createFileLink.setOnAction(event -> createFileAction(event));
        openFileLink.setOnAction(event -> openFileAction(event));
        newFile.setOnAction(event -> createFileAction(event));
        openFile.setOnAction(event -> openFileAction(event));

        contentTabPane.setManaged(false);
        createHtmlEditorTab();
    }
}
