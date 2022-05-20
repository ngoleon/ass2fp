package com.ass2fp.controllers;

import com.ass2fp.model.Model;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;

public class EditProfileController {
    @FXML
    private ImageView imageview;
    @FXML
    private Label usernameLabel;
    @FXML
    private TextField fNameField;
    @FXML
    private Label welcomeText1;
    @FXML
    private TextField lNameField;
    @FXML
    private Button okButton;
    @FXML
    private Button closeButton;
    @FXML
    private Label status;

    Model model;
    private SmartCanvasController smartCanvasController;

    public EditProfileController(Model model) {
        this.model = model;
    }

    public void setParentController(SmartCanvasController smartCanvasController) {
        this.smartCanvasController = smartCanvasController;
    }

    public void initialize() {
        imageview.setImage(new Image(model.getCurrentUser().getImagepath()));

        okButton.setOnAction(ActionEvent -> {
            if (!fNameField.getText().isEmpty() && !lNameField.getText().isEmpty()) {
                model.getCurrentUser().setfName(fNameField.getText());
                model.getCurrentUser().setlName(lNameField.getText());
                try {
                    model.getUserDao().replaceImgFL(model.getCurrentUser());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                smartCanvasController.setImageAndUser();
                Node source = (Node) ActionEvent.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
            }
        });

        closeButton.setOnAction(ActionEvent -> {
            Node source = (Node) ActionEvent.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        });

        imageview.setOnMouseClicked(ActionEvent -> {
            System.out.println(imageview.getImage().getUrl());
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("*.jpeg", "*.png", "*.jpg"));
            Node source = (Node) ActionEvent.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            File file = fc.showOpenDialog(stage);
            model.getCurrentUser().setImagepath(file.getAbsolutePath());
            if (file != null) {
                try {
                    InputStream fis = new FileInputStream(file);
                    imageview.setImage(new Image(fis));
                } catch (FileNotFoundException e) {
                    System.out.println("File not found");
                }
            }
        });
    }
}
