package com.onrender.dawgchat.dawgchat.controllers;

import com.onrender.dawgchat.dawgchat.utils.DatabaseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

@CrossOrigin
@RestController
public class Admin {

    @PostMapping("/createCourse")
    public Map<HttpStatus, String> createCourse(@RequestBody Map<String, String> course) throws SQLException {
        String courseNumber = course.get("course_number");
        String courseName = course.get("course_name");

        Statement statement = DatabaseHandler.getConnection().createStatement();

        String query = "SELECT course_number FROM courses WHERE course_number='%s'";
        String formattedQuery = String.format(query, courseNumber);
        ResultSet rs = statement.executeQuery(formattedQuery);
        if(rs.isBeforeFirst()){
            return Map.of(HttpStatus.BAD_REQUEST, "Course Already Exists");
        }

        String update = "INSERT INTO courses (course_number, course_name) VALUES ('%s', '%s')";
        String formattedUpdate = String.format(update, courseNumber, courseName);

        statement.executeUpdate(formattedUpdate);

        return Map.of(HttpStatus.OK, "Course Created");
    }

    @PostMapping("/deleteCourse")
    public Map<HttpStatus, String> removeCourse(@RequestBody Map<String, String> course) throws SQLException {
        String courseNumber = course.get("course_number");

        Statement statement = DatabaseHandler.getConnection().createStatement();

        String update = "DELETE FROM courses WHERE course_number='%s'";
        String formattedUpdate = String.format(update, courseNumber);

        statement.executeUpdate(formattedUpdate);

        return Map.of(HttpStatus.OK, "Course Deleted");
    }
}
