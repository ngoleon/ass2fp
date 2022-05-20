package com.ass2fp.model;

import com.ass2fp.dao.UserDao;
import com.ass2fp.dao.UserDaoIm;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class Model {
    private UserDao userDao;

    private User currentUser;
    private int height;
    private int width;

    public Model() {
        userDao = new UserDaoIm();
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void storeHeightWidth(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public void setCurrentUser(User user) {
        currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    ArrayList<Circle> circles = new ArrayList<>();

    void addCircle(Circle c) {
        circles.add(c);
    }

    Circle getCircle(int index) {
        return circles.get(index);
    }

    int getSize() {
        return circles.size();
    }

    ArrayList<Circle> getCircleArray() {
        return circles;
    }
}
