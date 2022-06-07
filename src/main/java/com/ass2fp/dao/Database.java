package com.ass2fp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Database {
    public Database() {
    }

    // Connects to database via DriverManager
    public static Connection getConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            return DriverManager.getConnection("jdbc:sqlite:test.db");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
