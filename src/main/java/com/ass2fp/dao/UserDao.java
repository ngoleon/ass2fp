package com.ass2fp.dao;

import java.sql.SQLException;

import com.ass2fp.model.User;

public interface UserDao {
    void setup() throws SQLException;

    User getUser(String username, String password) throws SQLException;

    void replaceImgFL(User user) throws SQLException;

    User createUser(String username, String password, String fname, String lname, String imagepath) throws SQLException;
}
