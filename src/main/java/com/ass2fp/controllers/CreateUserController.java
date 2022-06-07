package com.ass2fp.controllers;

import com.ass2fp.model.Model;
import com.ass2fp.Main;
import com.ass2fp.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.sql.SQLException;

public class CreateUserController {

    @FXML
    private TextField fNameField;

    @FXML
    private TextField lNameField;

    @FXML
    private TextField passField;

    @FXML
    private Button createUserButton;

    @FXML
    private Button closeButton;

    @FXML
    private TextField usernameField;

    @FXML
    private Label status;

    @FXML
    private ImageView imageview;

    private Model model;

    private Stage stage;
    private Scene scene;
    private String filepath = "/com/ass2fp/download.jpg";

    public CreateUserController(Stage stage, Model model) {
        this.stage = stage;
        this.model = model;
    }

    public void initialize() {
        // Swap to log in stage
        closeButton.setOnAction(actionEvent -> {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
            LoginController loginController = new LoginController(stage, model);
            fxmlLoader.setController(loginController);
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.setScene(scene);
            stage.setTitle("Log In");
            stage.show();
        });

        // Create a new user
        createUserButton.setOnAction(ActionEvent -> {
            // Checks if fields are empty
            if (!lNameField.getText().isEmpty() && !fNameField.getText().isEmpty() && !usernameField.getText().isEmpty() && !passField.getText().isEmpty()) {
                User user;
                // Passes variables from fields to userDAO
                try {
                    user = model.getUserDao().createUser(usernameField.getText(), passField.getText(), fNameField.getText(), lNameField.getText(), filepath);
                    status.setText("Created " + user.getUsername());
                    status.setTextFill(Color.GREEN);
                    //If user is already taken change label and clear fields
                } catch (SQLException e) {
                    status.setText("Username already taken");
                    status.setTextFill(Color.RED);
                    usernameField.clear();
                    passField.clear();
                    fNameField.clear();
                    lNameField.clear();
                }
            } else {
                status.setText("One or more fields are empty");
                status.setTextFill(Color.RED);
            }
        });

        // Set profile image
        imageview.setOnMouseClicked(ActionEvent -> {
            System.out.println(imageview.getImage().getUrl());
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("*.jpeg", "*.png", "*.jpg"));

            File file = fc.showOpenDialog(stage);
            try {
                filepath = file.getAbsolutePath();
            } catch (NullPointerException e) {
                System.out.println("No file chosen!");
            }
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
