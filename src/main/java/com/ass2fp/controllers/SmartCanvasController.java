package com.ass2fp.controllers;

import com.ass2fp.model.Model;
import com.ass2fp.Main;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.URL;

import static java.lang.Integer.parseInt;

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
    private StackPane stackPaneCanvas;
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
    private VBox imageVBox;
    @FXML
    private VBox circleVBox;
    @FXML
    private VBox rectVBox;
    @FXML
    private VBox textVBox;
    @FXML
    private ChoiceBox rectCbBgColour;
    @FXML
    private ChoiceBox circCbBorderColour;
    @FXML
    private ChoiceBox circCbBgColour;
    @FXML
    private TextField circTfBorderWidth;
    @FXML
    private TextField rectTfBorderWidth;
    @FXML
    private ChoiceBox rectCbBorderColour;

    @FXML
    private ToggleButton textButtonBold;

    @FXML
    private ToggleButton textButtonItalic;

    @FXML
    private Button textButtonLeftAlign;

    @FXML
    private Button textButtonMiddleAlign;

    @FXML
    private Button textButtonRightAlign;

    @FXML
    private ChoiceBox textCbBgColour;

    @FXML
    private ChoiceBox textCbBorderColour;

    @FXML
    private ChoiceBox textCbColour;

    @FXML
    private ChoiceBox textCbFont;

    @FXML
    private TextField textTf;

    @FXML
    private TextField textTfBorderWidth;
    @FXML
    private TextField textTfFontSize;
    @FXML
    private Slider rectSliderRotate;
    @FXML
    private Slider rectSliderWidth;
    @FXML
    private Slider rectSliderHeight;
    @FXML
    private Slider textSliderRotate;
    @FXML
    private Slider textSliderWidth;
    @FXML
    private Slider textSliderHeight;
    @FXML
    private Slider imgSliderRotate;
    @FXML
    private Slider imgSliderResize;
    @FXML
    private Slider circSliderRotate;
    @FXML
    private Slider circSliderResize;
    @FXML
    private Label labelx, labely, labelw, labelh, labelangle;
    @FXML
    private Button ImgButtonChangePath;
    @FXML
    private Slider globalSliderZoom;
    private Label txt;
    private Circle c;
    private Rectangle r;
    private Canvas canvas;
    private ImageView iv;
    private Stage stage;
    private Stage subStage;
    private Scene scene;
    private boolean canvasExists = false;

    private Model model;
    private Node currentNode;

    public SmartCanvasController(Stage stage, Model model) {
        this.stage = stage;
        this.model = model;
    }

    // Creates a new canvas
    public void setCanvas() {
        // Create a new canvas
        canvas = new Canvas();

        // Sets canvas to max canvas size
        canvas.setHeight(model.getMaxCanvasHeight());
        canvas.setWidth(model.getMaxCanvasWidth());

        // Boolean for canvas existing
        canvasExists = true;

        // Fills in the canvas with a white background
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, model.getMaxCanvasWidth(), model.getMaxCanvasWidth());

        // Sets stackPane container to max canvas size
        stackPaneCanvas.setMaxHeight(model.getMaxCanvasHeight());
        stackPaneCanvas.setMaxWidth(model.getMaxCanvasHeight());

        // Add canvas to stackPane container
        stackPaneCanvas.getChildren().add(canvas);

        // Zoom slider is set to current zoom value
        globalSliderZoom.setValue(model.getCurrentZoom());

        // Scale the canvas to size
        canvas.setScaleX(model.getCurrentZoom());
        canvas.setScaleY(model.getCurrentZoom());
    }

    // Toggles node visibility
    public void visibleNodes(Node node) {
        Node[] nodes = {bgHBox, imageVBox, circleVBox, rectVBox, textVBox};
        // Iterate and only make selected node visible
        for (Node n : nodes) {
            if (!n.equals(node)) {
                n.setVisible(false);
            } else {
                n.setVisible(true);
            }
        }
        currentNode = node;
    }

    // Set XY at the bottom
    public void setXY(Node n) {
        labelx.setText("x: " + (int) n.getBoundsInParent().getCenterX());
        labely.setText("y: " + (int) n.getBoundsInParent().getCenterY());
    }

    // Set circle event handlers
    public void setCircleOptions(Circle c) {
        setXY(c);
        // Resize circle slider value
        circSliderResize.setMax(model.getMaxCanvasHeight() / 2.0);

        // Resize circle
        circSliderResize.setOnMouseDragged(ActionEvent -> {
            c.setRadius(circSliderResize.getValue());
            labelh.setText("h: " + (int) circSliderResize.getValue());
            labelw.setText("w: " + (int) circSliderResize.getValue());
        });

        // Rotate circle
        circSliderRotate.setOnMouseDragged(ActionEvent -> {
                    System.out.println(circSliderRotate.getValue());
                    c.setRotate(circSliderRotate.getValue());
                    labelangle.setText("angle: " + (int) circSliderRotate.getValue());
                }
        );

        // Adjust border size
        circTfBorderWidth.setOnAction(ActionEvent -> {
            String test = circTfBorderWidth.getText();
            try {
                double dbl = Double.parseDouble(test);
                if (dbl < c.getRadius()) {
                    c.setStrokeWidth(dbl);
                } else {
                    throw new NumberFormatException("Please pick a smaller border size!");
                }
            } catch (Exception e) {
                System.out.println(e);
                System.out.println("Invalid number");
            }
        });

        // Change circle background colour
        circCbBgColour.setOnAction(ActionEvent -> {
            int index = circCbBgColour.getSelectionModel().getSelectedIndex();
            String[] str = model.getColourCodes();
            c.setFill(Paint.valueOf(str[index]));
        });

        // Change circle border colour
        circCbBorderColour.setOnAction(ActionEvent -> {
            int index = circCbBorderColour.getSelectionModel().getSelectedIndex();
            String[] str = model.getColourCodes();
            c.setStroke(Paint.valueOf(str[index]));
        });

        // Delete node if selected after selecting "delete element"
        if (model.getDelete()) {
            stackPaneCanvas.getChildren().remove(c);
            model.setDelete(false);
        }
    }

    // Set imageview handlers
    public void setImgOptions(ImageView i) {
        setXY(i);
        // Resize slider max value
        imgSliderResize.setMax(model.getMaxCanvasHeight());


        // Change imagepath button
        ImgButtonChangePath.setOnAction(ActionEvent -> {
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("*.jpeg", "*.png", "*.jpg"));

            File file = fc.showOpenDialog(stage);

            if (file != null) {
                try {
                    InputStream fis = new FileInputStream(file);
                    i.setImage(new Image(fis));
                    i.setFitHeight(model.getCanvasHeight());
                    i.setFitWidth(model.getCanvasWidth());
                    i.setPreserveRatio(true);
                } catch (FileNotFoundException e) {
                    System.out.println("File not found");
                }
            }
        });

        // Resize image
        imgSliderResize.setOnMouseDragged(ActionEvent -> {
            i.setFitHeight(imgSliderResize.getValue());
            i.setPreserveRatio(true);
            labelh.setText("h: " + i.getFitHeight());
            labelw.setText("w: " + i.getFitWidth());
        });

        // Rotate image
        imgSliderRotate.setOnMouseDragged(ActionEvent -> {
                    System.out.println(imgSliderRotate.getValue());
                    i.setRotate(imgSliderRotate.getValue());
                    labelangle.setText("angle: " + (int) imgSliderRotate.getValue());
                }
        );

        // Delete node if selected after selecting "delete element"
        if (model.getDelete()) {
            stackPaneCanvas.getChildren().remove(i);
            model.setDelete(false);
        }
    }


    // Set text label handlers
    public void setTextOptions(Label l) {
        setXY(l);

        // Set font based on saved variables
        model.setFont(l.getFont().getName());
        model.setFontSize(l.getFont().getSize());
        textSliderHeight.setMax(model.getMaxCanvasHeight());
        textSliderWidth.setMax(model.getMaxCanvasWidth());

        // Change text
        textTf.setOnAction(ActionEvent -> {
            l.setText(textTf.getText());
        });

        // Change font
        textCbFont.setOnAction(ActionEvent -> {
            int index = textCbFont.getSelectionModel().getSelectedIndex();
            String[] str = model.getFontTypes();

            // Different font combinations
            if (model.isBold() && model.isItalic()) {
                l.setFont(Font.font(str[index], FontWeight.BOLD, FontPosture.ITALIC, model.getFontSize()));
            } else if (model.isBold() && !model.isItalic()) {
                l.setFont(Font.font(str[index], FontWeight.BOLD, FontPosture.REGULAR, model.getFontSize()));
            } else if (!model.isBold() && model.isItalic()) {
                l.setFont(Font.font(str[index], FontWeight.NORMAL, FontPosture.ITALIC, model.getFontSize()));
            } else if (!model.isBold() && !model.isItalic()) {
                l.setFont(Font.font(str[index], FontWeight.NORMAL, FontPosture.REGULAR, model.getFontSize()));
            }
        });

        // Change font size
        textTfFontSize.setOnAction(ActionEvent -> {
            try {
                double fs = Double.parseDouble(textTfFontSize.getText());

                // Different font combinations
                if (model.isBold() && model.isItalic()) {
                    l.setFont(Font.font(model.getFont(), FontWeight.BOLD, FontPosture.ITALIC, fs));
                } else if (model.isBold() && !model.isItalic()) {
                    l.setFont(Font.font(model.getFont(), FontWeight.BOLD, FontPosture.REGULAR, fs));
                } else if (!model.isBold() && model.isItalic()) {
                    l.setFont(Font.font(model.getFont(), FontWeight.NORMAL, FontPosture.ITALIC, fs));
                } else if (!model.isBold() && !model.isItalic()) {
                    l.setFont(Font.font(model.getFont(), FontWeight.NORMAL, FontPosture.REGULAR, fs));
                }
                model.setFontSize(fs);
            } catch (Exception e) {
                System.out.println(e);
            }
        });

        // Set bold button
        textButtonBold.setOnAction(ActionEvent -> {

            // Different combinations
            if (textButtonBold.isSelected()) {
                if (model.isItalic()) {
                    l.setFont(Font.font(model.getFont(), FontWeight.BOLD, FontPosture.ITALIC, model.getFontSize()));
                    model.setBold(true);
                } else {
                    l.setFont(Font.font(model.getFont(), FontWeight.BOLD, FontPosture.REGULAR, model.getFontSize()));
                    model.setBold(true);
                }
            } else {
                if (model.isItalic()) {
                    l.setFont(Font.font(model.getFont(), FontWeight.NORMAL, FontPosture.ITALIC, model.getFontSize()));
                    model.setBold(false);
                } else {
                    l.setFont(Font.font(model.getFont(), FontWeight.NORMAL, FontPosture.REGULAR, model.getFontSize()));
                    model.setBold(false);
                }
            }
        });

        // Set italic button
        textButtonItalic.setOnAction(ActionEvent -> {
            // Different combinations
            if (textButtonItalic.isSelected()) {
                if (model.isBold()) {
                    l.setFont(Font.font(model.getFont(), FontWeight.BOLD, FontPosture.ITALIC, model.getFontSize()));
                    model.setItalic(true);
                } else {
                    l.setFont(Font.font(model.getFont(), FontWeight.NORMAL, FontPosture.ITALIC, model.getFontSize()));
                    model.setItalic(true);
                }
            } else {
                if (model.isBold()) {
                    l.setFont(Font.font(model.getFont(), FontWeight.BOLD, FontPosture.REGULAR, model.getFontSize()));
                    model.setItalic(false);
                } else {
                    l.setFont(Font.font(model.getFont(), FontWeight.NORMAL, FontPosture.REGULAR, model.getFontSize()));
                    model.setItalic(false);
                }
            }
        });

        // Change text colour
        textCbColour.setOnAction(ActionEvent -> {
            int index = textCbColour.getSelectionModel().getSelectedIndex();
            String[] str = model.getColourCodes();
            l.setTextFill(Paint.valueOf(str[index]));
        });

        // Align left button
        textButtonLeftAlign.setOnAction(ActionEvent -> {
            l.setAlignment(Pos.BASELINE_LEFT);
        });

        // Align right button
        textButtonRightAlign.setOnAction(ActionEvent -> {
            l.setAlignment(Pos.BASELINE_RIGHT);
        });

        // Align middle button
        textButtonMiddleAlign.setOnAction(ActionEvent -> {
            l.setAlignment(Pos.BASELINE_CENTER);
        });

        // Change border colour
        textCbBorderColour.setOnAction(ActionEvent -> {
            int index = textCbBorderColour.getSelectionModel().getSelectedIndex();
            String[] str = model.getColourCodes();
            BorderStroke test = new BorderStroke(Paint.valueOf(str[index]), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(model.getBorderWidth()));
            Border border = new Border(test);
            l.setBorder(border);
            model.setBorderColour(str[index]);
        });

        // Change border width
        textTfBorderWidth.setOnAction(ActionEvent -> {
            try {
                double fs = Double.parseDouble(textTfBorderWidth.getText());
                BorderStroke test = new BorderStroke(Paint.valueOf(model.getBorderColour()), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(fs));
                Border border = new Border(test);
                l.setBorder(border);
                model.setBorderWidth(fs);
            } catch (Exception e) {
            }
        });

        // Change background colour
        textCbBgColour.setOnAction(ActionEvent -> {
            int index = textCbBgColour.getSelectionModel().getSelectedIndex();
            String[] str = model.getColourCodes();
            l.setBackground(Background.fill(Paint.valueOf(str[index])));

        });

        // Change text height slider
        textSliderHeight.setOnMouseDragged(ActionEvent -> {
            l.setMinHeight(textSliderHeight.getValue());
            l.setMaxHeight(textSliderHeight.getValue());
            labelh.setText("h: " + (int) textSliderHeight.getValue());
        });

        // Change text width slider
        textSliderWidth.setOnMouseDragged(ActionEvent -> {
            l.setMinWidth(textSliderWidth.getValue());
            l.setMaxWidth(textSliderWidth.getValue());

            labelw.setText("w: " + (int) textSliderWidth.getValue());
        });

        // Rotate text slider
        textSliderRotate.setOnMouseDragged(ActionEvent -> {
                    System.out.println(textSliderRotate.getValue());
                    l.setRotate(textSliderRotate.getValue());
                    labelangle.setText("angle: " + (int) textSliderRotate.getValue());
                }
        );

        // Delete node if selected after selecting "delete element"
        if (model.getDelete()) {
            stackPaneCanvas.getChildren().remove(l);
            model.setDelete(false);
        }
    }

    // Set rectangle handlers
    public void setRectangleOptions(Rectangle r) {
        setXY(r);
        // Rectangle height slider value
        rectSliderHeight.setMax(model.getMaxCanvasHeight());
        // Rectangle width slider value
        rectSliderWidth.setMax(model.getMaxCanvasWidth());

        // Change rectangle background colour
        rectCbBgColour.setOnAction(ActionEvent -> {
            int index = rectCbBgColour.getSelectionModel().getSelectedIndex();
            String[] str = model.getColourCodes();
            r.setFill(Paint.valueOf(str[index]));
            if (index == 0) {
                r.setStroke(Color.BLACK);
            } else {
                r.setStroke(Color.TRANSPARENT);
            }
        });

        // Change rectangle border colour
        rectCbBorderColour.setOnAction(ActionEvent -> {
            int index = rectCbBorderColour.getSelectionModel().getSelectedIndex();
            String[] str = model.getColourCodes();
            r.setStroke(Paint.valueOf(str[index]));
        });

        // Set rectangle height slider
        rectSliderHeight.setOnMouseDragged(ActionEvent -> {
            r.setHeight(rectSliderHeight.getValue());
            labelh.setText("h: " + (int) rectSliderHeight.getValue());
        });

        // Set rectangle width slider
        rectSliderWidth.setOnMouseDragged(ActionEvent -> {
            r.setWidth(rectSliderWidth.getValue());
            labelw.setText("w: " + (int) rectSliderWidth.getValue());
        });

        // Rotate rectangle slider
        rectSliderRotate.setOnMouseDragged(ActionEvent -> {
                    System.out.println(rectSliderRotate.getValue());
                    r.setRotate(rectSliderRotate.getValue());
                    labelangle.setText("angle: " + (int) rectSliderRotate.getValue());
                }
        );

        // Set rectangle border width
        rectTfBorderWidth.setOnAction(ActionEvent -> {
            String test = rectTfBorderWidth.getText();
            try {
                double dbl = Double.parseDouble(test);
                if (dbl < r.getWidth()) {
                    r.setStrokeWidth(dbl);
                } else {
                    throw new NumberFormatException("Please pick a smaller border size!");
                }
            } catch (Exception e) {
                System.out.println(e);
                System.out.println("Invalid number");
            }
        });

        // Delete node if selected after selecting "delete element"
        if (model.getDelete()) {
            stackPaneCanvas.getChildren().remove(r);
            model.setDelete(false);
        }
    }

    // Set username and profile image on smart canvas menu
    public void setImageAndUser() {
        usernameLabel.setText(model.getCurrentUser().getfName() + " " + model.getCurrentUser().getlName());

        try {
            String filepath = model.getCurrentUser().getImagepath();
            URL imageURL = getClass().getResource(filepath);
            Image image = new Image(imageURL.toExternalForm());
            imageview.setImage(image);
        } catch (Exception e) {
            imageview.setImage(new Image(model.getCurrentUser().getImagepath()));
        }


    }

    // Populate choice boxes
    public void setChoiceBoxItems() {
        bgChoiceBox.getItems().addAll(model.getColourNames());
        rectCbBgColour.getItems().addAll(model.getColourNames());
        circCbBgColour.getItems().addAll(model.getColourNames());
        circCbBorderColour.getItems().addAll(model.getColourNames());
        rectCbBorderColour.getItems().addAll(model.getColourNames());
        textCbColour.getItems().addAll(model.getColourNames());
        textCbBgColour.getItems().addAll(model.getColourNames());
        textCbBorderColour.getItems().addAll(model.getColourNames());
        textCbFont.getItems().addAll(model.getFontTypes());
    }

    public void initialize() {

        setImageAndUser();
        setChoiceBoxItems();

        // Scaling zoom slider for canvas
        globalSliderZoom.setOnMouseDragged(ActionEvent -> {
            double objScale = globalSliderZoom.getValue() + model.getObjectZoom();
            double canvaScale = globalSliderZoom.getValue();

            // Rectangle scaling
            for (int i = 0; i < model.getRectArrL().size(); i++) {
                model.getRectArrL().get(i).setScaleX(objScale);
                model.getRectArrL().get(i).setScaleY(objScale);
            }

            // Circle scaling
            for (int i = 0; i < model.getCircArrL().size(); i++) {
                model.getCircArrL().get(i).setScaleX(objScale);
                model.getCircArrL().get(i).setScaleY(objScale);
            }

            // Imageview scaling
            for (int i = 0; i < model.getImgViewArrL().size(); i++) {
                model.getImgViewArrL().get(i).setScaleX(objScale);
                model.getImgViewArrL().get(i).setScaleY(objScale);
            }

            // Text scaling
            for (int i = 0; i < model.getLabArrL().size(); i++) {
                model.getLabArrL().get(i).setScaleX(objScale);
                model.getLabArrL().get(i).setScaleY(objScale);
            }

            // Canvas scaling
            canvas.setScaleX(canvaScale);
            canvas.setScaleY(canvaScale);

            // StackPane scaling
            stackPaneCanvas.setScaleX(canvaScale);
            stackPaneCanvas.setScaleY(canvaScale);
        });

        // Return focus to anchorpane if clicked
        anchorpane.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                anchorpane.requestFocus();
            }
        });

        // Open profile substage if profile button is clicked
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

        // Open new canvas substage if new canvas is selected
        newCanvasItem.setOnAction(ActionEvent -> {

            stackPaneCanvas.getChildren().clear();
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

        // Open about item substage if about is selected
        aboutItem.setOnAction(ActionEvent -> {
            subStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("about.fxml"));
            AboutController aboutController = new AboutController(model);
            fxmlLoader.setController(aboutController);
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            subStage.setTitle("Edit Profile");
            subStage.setScene(scene);
            subStage.show();
        });

        // Clears the canvas
        clearCanvasItem.setOnAction(ActionEvent -> {
            if (canvasExists) {
                // Reset canvas colour to white
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.setFill(Color.WHITE);
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                Canvas copy = canvas;
                // Clear stackPane of all children nodes
                stackPaneCanvas.getChildren().clear();
                stackPaneCanvas.getChildren().add(canvas);
            } else {
                System.out.println("No canvas exists!");
            }
        });

        // Toggle element delete boolean
        deleteElementItem.setOnAction(ActionEvent -> {
            model.setDelete(true);

        });

        // Create a new text object
        textButton.setOnAction(ActionEvent -> {
            if (canvasExists) {
                txt = new Label();
                txt.setText("Sample text");
                txt.setMinSize(60, 60);
                labelh.setText("h: 60");
                labelw.setText("w: 60");
                ObjectController oc = new ObjectController(txt, this, textVBox, txt, model);
                stackPaneCanvas.getChildren().add(txt);
            }
        });

        // Create a new image object
        imageButton.setOnAction(ActionEvent -> {
            if (canvasExists) {
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
                        labelh.setText("h: 60");
                        labelw.setText("w: 60");
                        iv.setPreserveRatio(true);
                    } catch (FileNotFoundException e) {
                        System.out.println("File not found");
                    }
                }
                ObjectController oc = new ObjectController(iv, this, imageVBox, iv, model);
                stackPaneCanvas.getChildren().add(iv);
            }
        });

        // Create a new rectangle
        rectButton.setOnAction(ActionEvent -> {
            if (canvasExists) {
                r = new Rectangle();
                r.setHeight(100);
                r.setWidth(50);
                labelh.setText("h: 100");
                labelw.setText("w: 50");
                r.setFill(Color.GRAY);
                ObjectController oc = new ObjectController(r, this, rectVBox, r, model);
                stackPaneCanvas.getChildren().add(r);
            }
        });

        // Create a new circle
        circleButton.setOnAction(ActionEvent -> {
            if (canvasExists) {
                c = new Circle();
                c.setRadius(32.0f);
                labelh.setText("h: 32");
                labelw.setText("w: 32");
                c.setFill(Color.GRAY);
                ObjectController oc = new ObjectController(c, this, circleVBox, c, model);
                stackPaneCanvas.getChildren().add(c);
            }
        });

        // Reveal the canvas background menu
        canvasButton.setOnAction(ActionEvent -> {
            if (canvasExists) {
                visibleNodes(bgHBox);
            }
        });

        // Edit the colour of the canvas background
        bgChoiceBox.setOnAction(ActionEvent -> {
            GraphicsContext gc = canvas.getGraphicsContext2D();
            int index = bgChoiceBox.getSelectionModel().getSelectedIndex();
            String[] str = {"FFFFFF", "#000000", "#FF2D00", "#27FF00", "#00FFFB"};
            gc.setFill(Paint.valueOf(str[index]));
            gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        });

        // Save canvas to file
        saveAsItem.setOnAction(ActionEvent -> {
            FileChooser savefile = new FileChooser();
            savefile.setTitle("Save File");

            File file = savefile.showSaveDialog(stage);

            try {
                WritableImage writableImage = new WritableImage((int) model.getCanvasWidth(), (int) model.getCanvasHeight());
                stackPaneCanvas.snapshot(new SnapshotParameters(), writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "png", file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Log out to log in menu
        logoutButton.setOnAction(ActionEvent -> {
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
    }

}
