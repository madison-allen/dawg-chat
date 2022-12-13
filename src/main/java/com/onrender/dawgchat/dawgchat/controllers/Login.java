package com.onrender.dawgchat.dawgchat.controllers;

import com.onrender.dawgchat.dawgchat.utils.DatabaseHandler;
import com.onrender.dawgchat.dawgchat.utils.PasswordManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

@CrossOrigin
@RestController
public class Login {

    @PostMapping("/login")
    public Map<HttpStatus, String> validateLoginCredentials(@RequestBody Map<String, String> credentials) throws SQLException {
        Statement statement = DatabaseHandler.getConnection().createStatement();

        //TODO Add error checking to make sure correct credentials are passed

        String getEncryptedPasswordQuery = "SELECT password FROM students WHERE email = '%s'";
        String formattedQuery = String.format(getEncryptedPasswordQuery,
                credentials.get("email"));

        ResultSet rs = statement.executeQuery(formattedQuery);

        //If the query did not have any results, the email is not in the database
        if(!rs.isBeforeFirst()){
            return Map.of(HttpStatus.BAD_REQUEST, "Invalid email address");
        }

        /*
         * rs.next() moves the result set to the first item to get the encrypted password
         * Then, the plain text password is checked against the encrypted password
         */
        rs.next();
        if(!PasswordManager.checkPassword(credentials.get("password"), rs.getString("password"))){
            return Map.of(HttpStatus.BAD_REQUEST, "Invalid password");
        }

        if(credentials.get("email").equals("admin@siu.edu")){
            return Map.of(HttpStatus.OK, "Admin User");
        }

        return Map.of(HttpStatus.OK, "Valid credentials");
    }

    public boolean verifyPassword(String password, String encryptedPassword){

        return PasswordManager.checkPassword(password, encryptedPassword);
    }
}