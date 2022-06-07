package com.ass2fp.model;

import com.ass2fp.dao.UserDao;
import com.ass2fp.dao.UserDaoIm;
import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Model {

    // User & Database
    private UserDao userDao;
    private User currentUser;

    // Canvas function variables
    private double canvasHeight;
    private double canvasWidth;
    private Bounds bounds;

    //Delete function variable
    private boolean delete = false;

    // Font function variables
    private double fontSize;
    private String font;
    private String[] fontTypes = {"Verdana", "Times New Roman", "Helvetica", "Comic Sans MS", "Impact", "Lucida Sans Unicode"};
    private boolean italic = false;
    private boolean bold = false;
    private String borderColour;
    private double borderWidth = 1;

    // Choicebox variables
    private String[] colourCodes = {"FFFFFF", "#000000", "#FF2D00", "#27FF00", "#00FFFB"};
    private String[] colourNames = {"WHITE", "BLACK", "RED", "GREEN", "BLUE"};


    // Global Zoom variables
    private double currentZoom;
    private ArrayList<Rectangle> rectArrL = new ArrayList<>();
    private ArrayList<ImageView> imgViewArrL = new ArrayList<>();
    private ArrayList<Circle> circArrL = new ArrayList<>();
    private ArrayList<Label> labArrL = new ArrayList<>();

    // Bounds checking variables
    boolean validLocation = true;
    private double boundsX;
    private double boundsY;


    // Bounds checking getters and setters
    public boolean isValidLocation() {
        return validLocation;
    }

    public void setValidLocation(boolean valid) {
        this.validLocation = valid;
    }

    public double getBoundsY() {
        return boundsY;
    }

    public void setBoundsY(double boundsY) {
        this.boundsY = boundsY;
    }

    public double getBoundsX() {
        return boundsX;
    }

    public void setBoundsX(double boundsX) {
        this.boundsX = boundsX;
    }

    public void addBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    public Bounds getBounds() {
        return bounds;
    }


    // Global zoom getters and setters

    public ArrayList<Rectangle> getRectArrL() {
        return rectArrL;
    }

    public void setRectArrL(Rectangle r) {
        rectArrL.add(r);
    }

    public ArrayList<ImageView> getImgViewArrL() {
        return imgViewArrL;
    }

    public void setImgViewArrL(ImageView i) {
        imgViewArrL.add(i);
    }

    public ArrayList<Circle> getCircArrL() {
        return circArrL;
    }

    public void setCircArrL(Circle c) {
        circArrL.add(c);
    }

    public ArrayList<Label> getLabArrL() {
        return labArrL;
    }

    public void setLabArrL(Label l) {
        labArrL.add(l);
    }


    public double getCurrentZoom() {
        double bigSide;
        if (canvasHeight > canvasWidth) {
            bigSide = canvasHeight;
        } else {
            bigSide = canvasWidth;
        }
        currentZoom = bigSide / 500;
        return currentZoom;
    }

    public double getObjectZoom() {
        return 1 - getCurrentZoom();
    }

    public void setCurrentZoom(double currentZoom) {
        this.currentZoom = currentZoom;
    }

    // Font getters and setters

    public boolean isItalic() {
        return italic;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public void setFontSize(double d) {
        this.fontSize = d;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public double getFontSize() {
        return fontSize;
    }

    public String getFont() {
        return font;
    }

    public String[] getFontTypes() {
        return fontTypes;
    }

    public double getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(double borderWidth) {
        this.borderWidth = borderWidth;
    }

    public String getBorderColour() {
        return borderColour;
    }

    public void setBorderColour(String borderColour) {
        this.borderColour = borderColour;
    }

    // Delete boolean getter and setter
    public void setDelete(boolean b) {
        delete = b;
    }

    public boolean getDelete() {
        return delete;
    }


    // Choicebox getter and setters
    public String[] getColourCodes() {
        return colourCodes;
    }

    public void setColourCodes(String[] colourCodes) {
        this.colourCodes = colourCodes;
    }

    public String[] getColourNames() {
        return colourNames;
    }

    public void setColourNames(String[] colourNames) {
        this.colourNames = colourNames;
    }


    // User and Database getters and setters
    public Model() {
        userDao = new UserDaoIm();
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setCurrentUser(User user) {
        currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    // Canvas getters and setters
    public void storeHeightWidth(double height, double width) {
        this.canvasHeight = height;
        this.canvasWidth = width;
    }

    public double getCanvasHeight() {
        return canvasHeight;
    }

    public double getCanvasWidth() {
        return canvasWidth;
    }

    public double getMaxCanvasWidth() {
        double maxCanvasWidth = 500;
        return maxCanvasWidth;
    }

    public double getMaxCanvasHeight() {
        double maxCanvasHeight = 500;
        return maxCanvasHeight;
    }


}
