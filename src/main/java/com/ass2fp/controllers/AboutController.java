package com.ass2fp.controllers;

import com.ass2fp.model.Model;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;

public class AboutController {
    @FXML
    private Button closeButton;
    Model model;

    public AboutController(Model model) {
        this.model = model;
    }

    public void initialize() {
        // Close stage
        closeButton.setOnAction(ActionEvent -> {
            Node source = (Node) ActionEvent.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        });
    }
}
