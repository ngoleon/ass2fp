package com.ass2fp.controllers;

import com.ass2fp.model.Model;
import com.ass2fp.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class SmartCanvasController {
    @FXML
    private MenuItem newCanvasItem;
    @FXML
    private MenuItem clearCanvasItem;
    @FXML
    private MenuItem saveAsItem;
    @FXML
    private MenuItem deleteElementItem;
    @FXML
    private MenuItem aboutItem;
    @FXML
    private Button textButton;
    @FXML
    private Button imageButton;
    @FXML
    private Button rectButton;
    @FXML
    private Button circleButton;
    @FXML
    private Button canvasButton;
    @FXML
    private Button profileButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Label usernameLabel;
    @FXML
    private StackPane hBoxCanvas;
    @FXML
    private Pane paneCanvas;
    @FXML
    private AnchorPane anchorpane;
    @FXML
    private ImageView imageview;
    @FXML
    private ChoiceBox bgChoiceBox;
    @FXML
    private HBox bgHBox;
    @FXML
    private HBox imageHBox;
    @FXML
    private VBox circleVBox;
    @FXML
    private VBox rectVBox;
    @FXML
    private VBox textVBox;

    private TextArea tf;
    private Circle c;
    private Rectangle r;
    private Canvas canvas;
    private ImageView iv;
    private Stage stage;
    private Stage subStage;
    private Scene scene;

    private boolean canvasExists = false;

    private Model model;

    public SmartCanvasController(Stage stage, Model model) {
        this.stage = stage;
        this.model = model;
    }

    public void setCanvas() {

        canvas = new Canvas();
        canvas.setHeight(model.getHeight());
        canvas.setWidth(model.getWidth());

        canvasExists = true;

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        hBoxCanvas.getChildren().add(canvas);
    }

    public void visibleNodes(Node node) {
        Node[] nodes = {bgHBox, imageHBox, circleVBox, rectVBox, textVBox};
        for (Node n : nodes) {
            if (!n.equals(node)) {
                n.setVisible(false);
            } else {
                n.setVisible(true);
            }
        }
    }

    public void setImageAndUser() {
        usernameLabel.setText(model.getCurrentUser().getfName() + " " + model.getCurrentUser().getlName());
        imageview.setImage(new Image(model.getCurrentUser().getImagepath()));
    }

    public void initialize() {

//        System.out.println(model.getCurrentUser().getImagepath());
        setImageAndUser();
        //       imageview.setImage(new Image("ass2fp/download.jpg"));
        anchorpane.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                anchorpane.requestFocus();
            }
        });

        profileButton.setOnAction(ActionEvent -> {
            subStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("editprofile.fxml"));
            EditProfileController editProfileController = new EditProfileController(model);
            fxmlLoader.setController(editProfileController);
            editProfileController.setParentController(this);
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            subStage.setTitle("Edit Profile");
            subStage.setScene(scene);
            subStage.show();
        });

        newCanvasItem.setOnAction(ActionEvent -> {

            hBoxCanvas.getChildren().clear();
            subStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("newcanvas.fxml"));
            NewCanvasController newCanvasController = new NewCanvasController(model);
            fxmlLoader.setController(newCanvasController);
            newCanvasController.setParentController(this);
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            subStage.setTitle("New Canvas");
            subStage.setScene(scene);
            subStage.show();
        });

        clearCanvasItem.setOnAction(ActionEvent -> {
            if (canvasExists) {
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.setFill(Color.WHITE);
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                Canvas copy = canvas;
                hBoxCanvas.getChildren().clear();
                hBoxCanvas.getChildren().add(canvas);
            } else {
                System.out.println("No canvas exists!");
            }
        });

        textButton.setOnAction(ActionEvent -> {
            if (canvasExists) {
                visibleNodes(textVBox);
                tf = new TextArea();
                tf.setMaxSize(90, 90);
                DragController dragController = new DragController(tf, canvas.getLayoutBounds());
                hBoxCanvas.getChildren().add(tf);
            }
        });

        imageButton.setOnAction(ActionEvent -> {
            if (canvasExists) {
                visibleNodes(imageHBox);
                iv = new ImageView();
                FileChooser fc = new FileChooser();
                fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("*.jpeg", "*.png", "*.jpg"));

                File file = fc.showOpenDialog(stage);

                if (file != null) {
                    try {
                        InputStream fis = new FileInputStream(file);
                        iv.setImage(new Image(fis));
                        iv.setFitHeight(60);
                        iv.setFitWidth(60);
                        iv.setPreserveRatio(true);
                    } catch (FileNotFoundException e) {
                        System.out.println("File not found");
                    }
                }
                DragController dragController = new DragController(iv, canvas.getLayoutBounds());
                hBoxCanvas.getChildren().add(iv);
            }
        });

        rectButton.setOnAction(ActionEvent -> {
            if (canvasExists) {
                visibleNodes(rectVBox);
                r = new Rectangle();
                r.setHeight(10);
                r.setWidth(20);
                DragController dragController = new DragController(r, canvas.getLayoutBounds());
                hBoxCanvas.getChildren().add(r);
            }
        });

        circleButton.setOnAction(ActionEvent -> {
            if (canvasExists) {
                visibleNodes(circleVBox);
                final Bounds bounds = hBoxCanvas.getLayoutBounds();
                c = new Circle();
                c.setRadius(32.0f);
                c.setFill(Color.BLACK);
                DragController dragController = new DragController(c, canvas.getLayoutBounds());
                hBoxCanvas.getChildren().add(c);
            }
        });

        canvasButton.setOnAction(ActionEvent -> {
            if (canvasExists) {
                visibleNodes(bgHBox);
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.setFill(Color.GREY);
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            }
        });
    }
}
