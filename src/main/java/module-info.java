module com.example.ass2fp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;


    opens com.ass2fp to javafx.fxml;
    exports com.ass2fp;
    exports com.ass2fp.controllers;
    opens com.ass2fp.controllers to javafx.fxml;
    exports com.ass2fp.dao;
    opens com.ass2fp.dao to javafx.fxml;
    exports com.ass2fp.model;
    opens com.ass2fp.model to javafx.fxml;
}