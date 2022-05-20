package com.ass2fp.dao;

import com.ass2fp.model.User;

import java.sql.*;

public class UserDaoIm implements UserDao {
    private static final String TABLE_NAME = "Users";

    public UserDaoIm() {
    }

    @Override
    public void setup() throws SQLException {
        try (Connection c = Database.getConnection(); Statement s = c.createStatement()) {
            System.out.println("Connection success");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public User getUser(String username, String password) throws SQLException {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE username = ? AND password = ?";
        try (Connection c = Database.getConnection()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setfName(rs.getString("fname"));
                    user.setlName(rs.getString("lname"));
                    user.setImagepath(rs.getString("imagepath"));
                    return user;
                }
                return null;
            }
        }
    }

    @Override
    public void replaceImgFL(User user) throws SQLException {
        String sql = "UPDATE " + TABLE_NAME + " SET fname = ? ,lname = ? ,imagepath = ? WHERE username = ?";
        try (Connection c = Database.getConnection()) {
            System.out.println(user.getfName() + user.getlName());
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, user.getfName());
            ps.setString(2, user.getlName());
            ps.setString(3, user.getImagepath());
            ps.setString(4, user.getUsername());
            ps.executeUpdate();
        }
    }

    @Override
    public User createUser(String username, String password, String fname, String lname, String imagepath) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?, ?, ?)";
        try (Connection c = Database.getConnection()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, fname);
            ps.setString(4, lname);
            ps.setString(5, imagepath);
            ps.executeUpdate();
            return new User(username, password, fname, lname, imagepath);
        }
    }
}
