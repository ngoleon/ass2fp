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
    private String filepath = "@download.jpg";

    public CreateUserController(Stage stage, Model model) {
        this.stage = stage;
        this.model = model;
    }

//    public void switchLoginScene(ActionEvent event) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
//        LoginController loginController = new LoginController(stage);
//        fxmlLoader.setController(loginController);
//        scene = new Scene(fxmlLoader.load());
//        stage.setScene(scene);
//        stage.show();
//    }

//    public void createUser(ActionEvent event) throws IOException{
//        if(!lNameField.getText().isEmpty() && !fNameField.getText().isEmpty() && !usernameField.getText().isEmpty() && !passField.getText().isEmpty()) {
//            status.setText("It worked.");
//        }
//    }

    public void initialize() {
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

        createUserButton.setOnAction(ActionEvent -> {
            if (!lNameField.getText().isEmpty() && !fNameField.getText().isEmpty() && !usernameField.getText().isEmpty() && !passField.getText().isEmpty()) {
                //User user = new User(usernameField.getText(), passField.getText(), fNameField.getText(), lNameField.getText());
//                boolean valid = userDaoTemp.setUserAccounts(usernameField.getText(), passField.getText(), fNameField.getText(), lNameField.getText());
//                if(valid){
//                    status.setText("Created " + userDaoTemp.getRegisteredUser());
//                    status.setTextFill(Color.GREEN);
//                } else {
//                    status.setText("Cannot create user");
//                    status.setTextFill(Color.RED);
//                }
//            } else {
//                status.setText("One or more fields are empty");
//                status.setTextFill(Color.RED);
//            }
                User user;
                try {
                    user = model.getUserDao().createUser(usernameField.getText(), passField.getText(), fNameField.getText(), lNameField.getText(), filepath);
                    status.setText("Created " + user.getUsername());
                    status.setTextFill(Color.GREEN);
                } catch (SQLException e) {
                    System.out.println(e);
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

        imageview.setOnMouseClicked(ActionEvent -> {
            System.out.println(imageview.getImage().getUrl());
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("*.jpeg", "*.png", "*.jpg"));

            File file = fc.showOpenDialog(stage);
            filepath = file.getAbsolutePath();
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
