package com.ass2fp.model;

public class User {
    private String username;
    private String password;
    private String fName;
    private String lName;
    private String imagepath;


    public User() {
    }

    public User(String username, String password, String fName, String lName, String imagepath) {
        this.username = username;
        this.password = password;
        this.fName = fName;
        this.lName = lName;
        this.imagepath = imagepath;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }
}