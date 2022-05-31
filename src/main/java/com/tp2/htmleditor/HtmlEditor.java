package com.tp2.htmleditor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HtmlEditor extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HtmlEditor.class.getResource("HTMLeditor.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("HTML editor");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
