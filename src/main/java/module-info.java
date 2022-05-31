module com.tp2.htmleditor {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.tp2.htmleditor to javafx.fxml;
    exports com.tp2.htmleditor;
}