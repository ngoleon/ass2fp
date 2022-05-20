package com.ass2fp;

import com.ass2fp.controllers.LoginController;
import com.ass2fp.model.Model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {

    private Model model;

    @Override
    public void init() {
        model = new Model();
    }

    @Override

    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
            LoginController loginController = new LoginController(stage, model);
            fxmlLoader.setController(loginController);
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Log In");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}