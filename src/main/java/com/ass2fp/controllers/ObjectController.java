package com.ass2fp.controllers;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import com.ass2fp.model.Model;

public class ObjectController {

    private double anchorX;
    private double anchorY;
    private SmartCanvasController parentController;
    private Model model;

    // Rectangle constructor
    public ObjectController(Node target, SmartCanvasController parentController, Node menu, Rectangle r, Model model) {
        model.setRectArrL(r);
        this.parentController = parentController;
        createHandlers(target, menu, r);
        this.model = model;
    }

    // Overloaded circle constructor
    public ObjectController(Node target, SmartCanvasController parentController, Node menu, Circle c, Model model) {
        model.setCircArrL(c);
        this.parentController = parentController;
        createHandlers(target, menu, c);
        this.model = model;
    }

    // Overloaded imageview constructor
    public ObjectController(Node target, SmartCanvasController parentController, Node menu, ImageView i, Model model) {
        model.setImgViewArrL(i);
        this.parentController = parentController;
        createHandlers(target, menu, i);
        this.model = model;
    }

    // Overloaded label constructor
    public ObjectController(Node target, SmartCanvasController parentController, Node menu, Label l, Model model) {
        model.setLabArrL(l);
        this.parentController = parentController;
        createHandlers(target, menu, l);
        this.model = model;
    }

    //credit to RMIT example code

    // Rectangle object handler
    private void createHandlers(Node n, Node menu, Rectangle r) {
        n.setOnMousePressed(event -> {
            n.requestFocus();
            parentController.visibleNodes(menu);
            parentController.setRectangleOptions(r);

            anchorX = event.getSceneX() - n.getTranslateX();
            anchorY = event.getSceneY() - n.getTranslateY();
        });

        setDrag(n);
    }

    // Overloaded label object handler
    private void createHandlers(Node n, Node menu, Label l) {
        n.setOnMousePressed(event -> {
            n.requestFocus();
            parentController.setTextOptions(l);
            parentController.visibleNodes(menu);

            anchorX = event.getSceneX() - n.getTranslateX();
            anchorY = event.getSceneY() - n.getTranslateY();
        });
        setDrag(n);
    }

    // Overloaded circle object handler
    private void createHandlers(Node n, Node menu, Circle c) {
        n.setOnMousePressed(event -> {
            n.requestFocus();
            parentController.visibleNodes(menu);
            parentController.setCircleOptions(c);

            anchorX = event.getSceneX() - n.getTranslateX();
            anchorY = event.getSceneY() - n.getTranslateY();
        });
        setDrag(n);
    }

    // Overloaded imageview object handler
    private void createHandlers(Node n, Node menu, ImageView i) {
        n.setOnMousePressed(event -> {
            n.requestFocus();
            parentController.visibleNodes(menu);
            parentController.setImgOptions(i);

            anchorX = event.getSceneX() - n.getTranslateX();
            anchorY = event.getSceneY() - n.getTranslateY();
        });
        setDrag(n);
    }

    // Sets dragging capabilities
    private void setDrag(Node n) {
        n.setOnMouseDragged(event -> {
            double x = event.getSceneX() - anchorX;
            double y = event.getSceneY() - anchorY;

            parentController.setXY(n);

            // Checks East and West Bounds
            if (n.getBoundsInParent().getMaxX() < model.getMaxCanvasWidth() && n.getBoundsInParent().getMinX() > 0) {
                // Sets x and y value to return to if out of bounds
                if (x > 0) {
                    model.setBoundsX(x - 1);
                } else {
                    model.setBoundsX(x + 1);
                }
                // Set new X location for object
                n.setTranslateX(event.getSceneX() - anchorX);
            } else {
                n.setTranslateX(event.getSceneX() - anchorX);
                model.setValidLocation(false);
            }

            // Checks North and South Bounds
            if (n.getBoundsInParent().getMaxY() < model.getMaxCanvasHeight() && n.getBoundsInParent().getMinY() > 0) {
                // Sets x and y value to return to if out of bounds
                if (y > 0) {
                    model.setBoundsY(y - 1);
                } else {
                    model.setBoundsY(y + 1);
                }
                // Set new Y location for object
                n.setTranslateY(event.getSceneY() - anchorY);
            } else {
                n.setTranslateY(event.getSceneY() - anchorY);
                model.setValidLocation(false);
            }
        });

        // Reverts X and Y of object if out of bounds
        n.setOnMouseReleased(event -> {
            if (!model.isValidLocation()) {
                n.setTranslateX(model.getBoundsX());
                n.setTranslateY(model.getBoundsY());
                model.setValidLocation(true);
            }
        });
    }
}

