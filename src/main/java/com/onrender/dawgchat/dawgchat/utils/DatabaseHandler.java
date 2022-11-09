package com.onrender.dawgchat.dawgchat.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.onrender.dawgchat.dawgchat.utils.Constants.*;

public class DatabaseHandler {

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}