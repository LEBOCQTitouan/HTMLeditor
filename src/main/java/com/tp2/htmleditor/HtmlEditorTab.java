package com.tp2.htmleditor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.*;

public class HtmlEditorTab extends Tab {
    @FXML
    TextArea fxmlContent;
    public HtmlEditorTab() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("tabContent.fxml"));
        this.setContent(fxmlLoader.load());
    }

    public HtmlEditorTab(String s) throws Exception {
        throw new Exception("This method is not supported.");
    }

    public HtmlEditorTab(String s, Node node) throws Exception {
        throw new Exception("This method is not supported.");
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
            fxmlContent.setText(stringBuilder.toString());
        } else throw new IllegalArgumentException("Invalid path given.");
    }
}
