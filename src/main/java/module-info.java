module com.example.gui_final_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.sql;
    requires mysql.connector.java;


    opens com.example.gui_final_project to javafx.fxml;
    exports com.example.gui_final_project;
}