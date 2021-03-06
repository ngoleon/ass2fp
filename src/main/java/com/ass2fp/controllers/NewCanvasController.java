package com.ass2fp.controllers;

import com.ass2fp.model.Model;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class NewCanvasController {
    @FXML
    private TextField widthField;
    @FXML
    private TextField heightField;
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label status;
    private SmartCanvasController smartCanvasController;
    private Model model;

    public NewCanvasController(Model model) {
        this.model = model;
    }

    public void setParentController(SmartCanvasController smartCanvasController) {
        this.smartCanvasController = smartCanvasController;
    }


    public void initialize() {

        // Set new canvas button
        okButton.setOnAction(ActionEvent -> {
            // Checks if width and height fields are empty
            if (!widthField.getText().isEmpty() && !heightField.getText().isEmpty()) {
                try {
                    int height = Integer.parseInt(heightField.getText());
                    int width = Integer.parseInt(widthField.getText());
                    // Height and width bounds
                    if (height > model.getMaxCanvasHeight() || width > model.getMaxCanvasWidth()) {
                        throw new NumberFormatException();
                    }
                    Node source = (Node) ActionEvent.getSource();
                    Stage stage = (Stage) source.getScene().getWindow();
                    model.storeHeightWidth(height, width);
                    smartCanvasController.setCanvas();
                    stage.close();
                } catch (NumberFormatException e) {
                    status.setText("Invalid size");
                    status.setTextFill(Color.RED);
                }
            }
        });

        // Closes stage
        cancelButton.setOnAction(ActionEvent -> {
            Node source = (Node) ActionEvent.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        });
    }
}
