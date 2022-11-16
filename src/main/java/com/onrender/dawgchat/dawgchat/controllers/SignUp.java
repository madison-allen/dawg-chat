package com.onrender.dawgchat.dawgchat.controllers;

import com.onrender.dawgchat.dawgchat.utils.DatabaseHandler;
import com.onrender.dawgchat.dawgchat.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

@CrossOrigin
@RestController
public class SignUp {

    @PostMapping("/signup")
    public Map<HttpStatus, String> createUser(@RequestBody Map<String, String> credentials) throws SQLException {
        //TODO Add error checking to make sure correct credentials are passed
        User user = new User(credentials);
        Statement statement = createStatement();

        //TODO remove email validation if we move it to the front-end
        if(!validateEmail(user.getEmail())){
            return Map.of(HttpStatus.BAD_REQUEST, "Invalid email address");
        }
        if(user.getId().length() != 12){
            return Map.of(HttpStatus.BAD_REQUEST, "Invalid Dawg Tag");
        }
        if(isExistingUser(user, statement)){
            return Map.of(HttpStatus.BAD_REQUEST, "student_id or email is already in use");
        }

        String createUserUpdate = "INSERT INTO students (student_id, first_name, last_name, email, password) " +
                "VALUES ('%s', '%s', '%s', '%s', '%s')";
        String formattedUpdate = String.format(createUserUpdate,
                user.getId(),
                user.getFirst(),
                user.getLast(),
                user.getEmail(),
                user.getPassword());

        statement.executeUpdate(formattedUpdate);

        return Map.of(HttpStatus.OK, "User created");
    }

    public boolean validateEmail(String email){
        return email.endsWith("@siu.edu");
    }

    /*
     * Method to check if the student or email is already associated with an existing user
     * If the query does not return any data, then there is not an existing user with the credentials.
     * In that case it return false, otherwise it returns true;
     */
    public boolean isExistingUser(User user, Statement statement) throws SQLException {
        String existingUserQuery = "SELECT student_id FROM students WHERE student_id='%s' OR email='%s'";
        String formattedQuery = String.format(existingUserQuery, user.getId(), user.getEmail());

        ResultSet rs = statement.executeQuery(formattedQuery);

        return rs.isBeforeFirst();
    }

    /*
     * This method sets up a statement that is connected to only one connection.
     * This is done to prevent repeated code since there are multiple queries in this class.
     */
    private Statement createStatement() throws SQLException {
        return DatabaseHandler.getConnection().createStatement();
    }
}