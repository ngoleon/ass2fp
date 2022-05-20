package com.ass2fp.controllers;

import com.ass2fp.Main;
import com.ass2fp.model.Model;
import com.ass2fp.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private Label usernameLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private Label passwordLabel;
    @FXML
    private TextField passwordField;
    @FXML
    private Button signinButton;
    @FXML
    private Button closeButton;
    @FXML
    private Hyperlink newUserHyperlink;
    @FXML
    private Label status;

    private Stage stage;
    private Scene scene;
    private Model model;

    public LoginController(Stage stage, Model model) {
        this.stage = stage;
        this.model = model;
    }

    @FXML
    public void initialize() {
        signinButton.setOnAction(actionEvent -> {
            if (!usernameField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
                User user;
                try {
                    user = model.getUserDao().getUser(usernameField.getText(), passwordField.getText());
                    model.setCurrentUser(user);
                    status.setText("Login success for " + user.getUsername());
                    status.setTextFill(Color.GREEN);
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("smartcanvas.fxml"));
                    SmartCanvasController smartCanvasController = new SmartCanvasController(stage, model);
                    fxmlLoader.setController(smartCanvasController);
                    try {
                        scene = new Scene(fxmlLoader.load());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    stage.setScene(scene);
                    stage.setTitle("Smart Canvas");
                    stage.show();
                } catch (SQLException e) {
                    status.setText(e.getMessage());
                    status.setTextFill(Color.RED);
                } catch (NullPointerException e) {
                    status.setText("Incorrect Password");
                    status.setTextFill(Color.RED);
                }
//                boolean valid = userDaoTemp.loginUser(usernameField.getText(), passwordField.getText());
//                if(valid) {
//                    status.setText("Login success");
//                    status.setTextFill(Color.GREEN);
//                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("smartcanvas.fxml"));
//                    SmartCanvasController smartCanvasController = new SmartCanvasController(stage, userDaoTemp);
//                    fxmlLoader.setController(smartCanvasController);
//                    try {
//                        scene = new Scene(fxmlLoader.load());
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                    stage.setScene(scene);
//                    stage.show();
//                } else {
//                    status.setText("Incorrect username or password");
//                    status.setTextFill(Color.RED);
//                }
            } else {
                status.setText("Empty username or password");
                status.setTextFill(Color.RED);
            }
        });

        newUserHyperlink.setOnAction(actionEvent -> {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("createuser.fxml"));
            CreateUserController createuserController = new CreateUserController(stage, model);
            fxmlLoader.setController(createuserController);
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.setScene(scene);
            stage.setTitle("Register");
            stage.show();
        });

    }
}
