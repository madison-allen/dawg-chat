package com.onrender.dawgchat.dawgchat.controllers;

import com.onrender.dawgchat.dawgchat.utils.DatabaseHandler;
import io.getstream.chat.java.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import static com.onrender.dawgchat.dawgchat.utils.Constants.STREAM_SECRET;

@CrossOrigin
@RestController
public class TokenGenerator {

    @PostMapping("/createToken")
    public Map<HttpStatus, String> returnToken(@RequestBody Map<String, String> email){
        if(email.get("email") == null || email.get("email").isEmpty()){
            return Map.of(HttpStatus.BAD_REQUEST, "Invalid Email");
        }

        String token = User.createToken(STREAM_SECRET, email.get("email"), null, null);
        return Map.of(HttpStatus.OK, token);
    }

    @PostMapping("/getStudentId")
    public Map<HttpStatus, String> getStudentId(@RequestBody Map<String, String> email) throws SQLException {
        String query = "SELECT student_id FROM students WHERE email='%s'";
        String formattedQuery = String.format(query, email.get("email"));
        Statement statement = DatabaseHandler.getConnection().createStatement();
        ResultSet rs = statement.executeQuery(formattedQuery);
        rs.next();
        return Map.of(HttpStatus.OK, rs.getString("student_id"));
    }
}
