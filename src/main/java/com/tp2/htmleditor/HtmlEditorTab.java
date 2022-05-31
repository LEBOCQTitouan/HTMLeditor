package com.tp2.htmleditor;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HtmlEditorTab extends Tab {
    VBox contentHolder, infosHolder;
    HBox mainContent, infosCharHolder, infosLinesHolder;
    Label staticInfosChar, staticInfosLines, infosLines, infosChar;
    public HtmlEditorTab() {
        super();
        contentHolder = new VBox();

        mainContent = new HBox();

        infosHolder = new VBox();

        infosCharHolder = new HBox();
        staticInfosChar = new Label();
        staticInfosChar.setText("Nb char :");
        infosChar = new Label();
        infosChar.setText("X");

        infosLinesHolder = new HBox();
        staticInfosLines = new Label();
        staticInfosLines.setText("Nb lines :");
        infosLines = new Label();
        infosLines.setText("X");

        contentHolder.getChildren().add(mainContent);
        contentHolder.getChildren().add(infosHolder);

        infosCharHolder.getChildren().add(staticInfosChar);
        infosCharHolder.getChildren().add(infosChar);

        infosLinesHolder.getChildren().add(staticInfosLines);
        infosLinesHolder.getChildren().add(infosChar);

        this.setContent(contentHolder);
    }

    public HtmlEditorTab(String s) throws Exception {
        throw new Exception("This method is not supported.");
    }

    @Override
    public String toString() {
        return "HtmlEditorTab{" +
                "contentHolder=" + contentHolder +
                ", infosHolder=" + infosHolder +
                ", mainContent=" + mainContent +
                ", infosCharHolder=" + infosCharHolder +
                ", infosLinesHolder=" + infosLinesHolder +
                ", staticInfosChar=" + staticInfosChar +
                ", staticInfosLines=" + staticInfosLines +
                ", infosLines=" + infosLines +
                ", infosChar=" + infosChar +
                "inherited{"+ super.toString() +
                "}}";
    }

    public HtmlEditorTab(String s, Node node) throws Exception {
        throw new Exception("This method is not supported.");
    }
}
