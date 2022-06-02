package com.tp2.htmleditor;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.web.WebView;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlEditorTab extends Tab {
    private VBox contentHolder, webviewHolder;
    private HBox displayHolder;
    private TextArea htmlContentDisplay;
    private WebView preview;
    private Label nChar, nLines;

    private String contentFile;
    private File linkedFile;
    private boolean isFullyLoaded, saved;
    private HtmlEditorController linkedController;

    public void setLinkedController(HtmlEditorController linkedController) {
        this.linkedController = linkedController;
    }

    public boolean isSaved() {
        return saved;
    }

    public HtmlEditorTab() throws IOException {
        nChar = new Label("None");
        nLines = new Label("None");

        VBox infosHolder = new VBox();

        HBox lines = new HBox();
        lines.getChildren().add(new Label("nb char : "));
        lines.getChildren().add(nChar);

        HBox chars = new HBox();
        chars.getChildren().add(new Label("nb lines : "));
        chars.getChildren().add(nLines);

        infosHolder.getChildren().add(lines);
        infosHolder.getChildren().add(chars);

        preview = new WebView();

        webviewHolder = new VBox();
        webviewHolder.getChildren().add(new Label("Preview"));
        webviewHolder.getChildren().add(preview);

        htmlContentDisplay = new TextArea();
        htmlContentDisplay.textProperty().addListener((obs, oldVal, newVal) -> {
            if (obs != null && oldVal != null && newVal != null) {
                this.setContentFile(newVal);
                if (linkedFile != null && isFullyLoaded) {
                    this.setText(linkedFile.getName() + "*");
                    saved = false;
                    if (linkedController != null)
                        linkedController.updateButtonsSave();
                    updateInfos();
                }
            }
        });

        displayHolder = new HBox();
        displayHolder.getChildren().add(htmlContentDisplay);
        displayHolder.getChildren().add(webviewHolder);

        contentHolder = new VBox();
        contentHolder.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.S && event.isControlDown()) {
                try {
                    save();
                    this.setText(linkedFile.getName());
                } catch (Exception e) {
                    // TODO handle error
                }
            }
            event.consume();
        });
        contentHolder.getChildren().add(displayHolder);
        contentHolder.getChildren().add(infosHolder);

        linkedFile = null;
        isFullyLoaded = false;
        saved = false;

        this.setContent(contentHolder);
        this.setText("Untitled");
        this.setOnCloseRequest(event -> {
            if (!saved) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Close " + linkedFile.getName() + " without saving ?");
                alert.showAndWait();
                if (alert.getResult() != ButtonType.OK) {
                    event.consume();
                }
            }
        });
    }

    public HtmlEditorTab(String s) throws Exception {
        throw new Exception("This method is not supported.");
    }

    public HtmlEditorTab(String s, Node node) throws Exception {
        throw new Exception("This method is not supported.");
    }

    public String getContentFile() {
        return contentFile;
    }

    public void updateInfos() {
        this.nChar.setText(contentFile.length()+"");
        this.nLines.setText(contentFile.lines().count()+"");
    }

    public void setContentFile(String contentFile) {
        this.contentFile = contentFile;
        this.htmlContentDisplay.setText(contentFile);
        this.preview.getEngine().loadContent(contentFile, "text/html");
        this.preview.getEngine().reload();
    }

    public void loadFile(String path) throws Exception {
        File file = new File(path);
        String extension = "";
        int i = path.lastIndexOf('.');
        if (i > 0) {
            extension = path.substring(i+1);
        }
        if (file.exists() && extension.equals("html")) {
            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
                String line;
                while ((line = br.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
            }
            linkedFile = file;
            this.setText(file.getName());
            setContentFile(stringBuilder.toString());
            updateInfos();
            isFullyLoaded = true;
            saved = true;
        } else throw new IllegalArgumentException("Invalid path given.");
                    this.setText(linkedFile.getName());
    }

    public void save() throws IOException {
        if (!linkedFile.exists()) {
            linkedFile.createNewFile();
        }
        if (linkedController != null) {
            linkedController.updateButtonsSave();
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(linkedFile.getAbsolutePath()));
        writer.write(contentFile);
        writer.close();
        saved = true;
    }

    public void saveAs(String path) throws Exception {
        File newFileSave = new File(path);

        if (linkedFile.exists()) {
            Pattern pattern = Pattern.compile(".html$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(newFileSave.getAbsolutePath());
            if (matcher.find()) {
                save();
            } else throw new Exception("Invalid file extension.");
        } else throw new Exception("Invalid path given.");
    }
}
