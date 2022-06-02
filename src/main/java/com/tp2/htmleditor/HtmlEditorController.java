package com.tp2.htmleditor;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlEditorController {
    @FXML
    AnchorPane content;
    @FXML
    TabPane contentTabPane;
    @FXML
    Hyperlink createFileLink, openFileLink;
    @FXML
    Button newFile, openFile, saveFile, saveAsFile;
    @FXML
    VBox noFileOpenedDialogue;

    public void createFileAction(Event event) {
        event.consume();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select where to create the file.");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("HTML file (*.html)", "*.html");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(openFileLink.getScene().getWindow());
        if (file != null) {
            Pattern pattern = Pattern.compile(".html$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(file.getAbsolutePath());
            if (!matcher.find()) {
                file = new File(file.getAbsolutePath() + ".html");
            }
            try {
                file.createNewFile();
            } catch (Exception e) {
                // TODO error handling
            }
            createHtmlEditorTab(file.getAbsolutePath());
        }
    }

    public void openFileAction(Event event) {
        event.consume();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select the file you want to open.");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("HTML file (*.html)", "*.html");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(createFileLink.getScene().getWindow());
        if (file != null && file.exists()) {
            Pattern pattern = Pattern.compile(".html$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(file.getAbsolutePath());
            if (matcher.find()) {
                createHtmlEditorTab(file.getAbsolutePath());
            } else {
                // TODO error
            }
        }
    }

    public void saveFileAction(Event event) {
        HtmlEditorTab selected = (HtmlEditorTab) contentTabPane.getSelectionModel().getSelectedItem();
        try {
            if (selected != null)
                selected.save();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void saveAsAction(Event event) {
        event.consume();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select the saving destination.");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("HTML file (*.html)", "*.html");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(createFileLink.getScene().getWindow());
        if (file != null) {
            HtmlEditorTab selected = (HtmlEditorTab) contentTabPane.getSelectionModel().getSelectedItem();
            try {
                if (selected != null)
                    selected.saveAs(file.getAbsolutePath());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void createHtmlEditorTab(String path) {
        try {
            HtmlEditorTab newTab = new HtmlEditorTab();
            newTab.loadFile(path);
            newTab.setLinkedController(this);

            contentTabPane.getTabs().add(newTab);

            if (!contentTabPane.isManaged()) {
                contentTabPane.setManaged(true);
            }

            if (noFileOpenedDialogue.isVisible()) {
                noFileOpenedDialogue.setVisible(false);
            }
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public void updateButtonsSave () {
        HtmlEditorTab selected = (HtmlEditorTab) contentTabPane.getSelectionModel().getSelectedItem();
        if (selected != null && !selected.isSaved()) {
            saveFile.setDisable(false);
            saveAsFile.setDisable(false);
        } else {
            saveFile.setDisable(true);
            saveAsFile.setDisable(true);
        }
    }

    public void updateForTabs() {
        if (contentTabPane.getTabs().size() < 1) {
            if (contentTabPane.isManaged()) {
                contentTabPane.setManaged(false);
            }
            if (!noFileOpenedDialogue.isVisible()) {
                noFileOpenedDialogue.setVisible(true);
            }

            saveFile.setDisable(true);
            saveAsFile.setDisable(true);
        } else {
            saveFile.setDisable(false);
            saveAsFile.setDisable(false);
        }
    }

    public void initialize() {
        createFileLink.setOnAction(event -> createFileAction(event));
        openFileLink.setOnAction(event -> openFileAction(event));
        newFile.setOnAction(event -> createFileAction(event));
        openFile.setOnAction(event -> openFileAction(event));

        contentTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        contentTabPane.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
                    @Override
                    public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
                        updateForTabs();
                        updateButtonsSave();
                    }
                }
        );
        contentTabPane.setManaged(false);
        saveFile.setDisable(true);
        saveAsFile.setDisable(true);
    }
}
