package com.ass2fp.controllers;

import javafx.geometry.Bounds;
import javafx.scene.Node;

public class DragController {

    private double anchorX;
    private double anchorY;

    public DragController(Node target, Bounds bounds) {
        createHandlers(target, bounds);
    }

    private void createHandlers(Node c, Bounds bounds) {
        c.setOnMousePressed(event -> {
            c.requestFocus();

            anchorX = event.getSceneX() - c.getTranslateX();
            anchorY = event.getSceneY() - c.getTranslateY();
        });
        c.setOnMouseDragged(event -> {
            double x = event.getSceneX() - anchorX;
            double y = event.getSceneY() - anchorY;
            System.out.println("x :" + x + "y : " + y);
            c.setTranslateX(event.getSceneX() - anchorX);
            c.setTranslateY(event.getSceneY() - anchorY);

        });
    }

}
