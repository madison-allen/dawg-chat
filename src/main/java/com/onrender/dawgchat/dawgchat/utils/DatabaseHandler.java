package com.onrender.dawgchat.dawgchat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static com.onrender.dawgchat.dawgchat.Constants.*;

public class DatabaseHandler {

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
