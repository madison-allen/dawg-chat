package com.onrender.dawgchat.dawgchat;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

@RestController
public class SignUp {

    @PostMapping("/signup")
    public String createUser(@RequestParam Map<String, String> credentials) throws SQLException {
        User user = new User(credentials);
        //TODO validate email if we don't do it in the front-end

        String existingUserQuery = "SELECT student_id FROM students WHERE student_id=%s AND email=%s";
        Connection conn = DatabaseHandler.getConnection();
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(String.format(existingUserQuery, user.getId(), user.getEmail()));

        return String.format(existingUserQuery, "id", "email");
    }

    private boolean validateEmail(String email){
        return email.endsWith("@siu.edu");
    }
}
