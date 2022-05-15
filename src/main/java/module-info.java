module com.example.ass2fp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ass2fp to javafx.fxml;
    exports com.example.ass2fp;
}