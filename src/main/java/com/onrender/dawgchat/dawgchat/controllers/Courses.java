package com.onrender.dawgchat.dawgchat.controllers;

import com.onrender.dawgchat.dawgchat.utils.DatabaseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class Courses {

    @PostMapping("/enrolledCourses")
    public List<String> getEnrolledCourses(@RequestBody Map<String, String> credentials) throws SQLException {
        Statement statement = createStatement();

        //TODO Add error checking to make sure correct credentials are passed

        String getEncryptedPasswordQuery = "SELECT enrolled_course FROM students WHERE email = '%s'";
        String formattedQuery = String.format(getEncryptedPasswordQuery,
                credentials.get("email"));

        ResultSet rs = statement.executeQuery(formattedQuery);

        //if there is no results (no enrolled classes), return an empty list
        if(!rs.isBeforeFirst()){
            return new ArrayList<>();
        }

        /*
         * The result set is moved to the first result (which should be the only result)
         * and the enrolled courses list is retrieved. There was a
         * problem where the courses array was null even after checking if
         * the result set was empty, so that is why the if statement was added.
         */
        rs.next();
        Array coursesArray = rs.getArray("enrolled_course");
        if(coursesArray == null){
            return new ArrayList<>();
        }
        String[] courses = (String[]) coursesArray.getArray();

        return Arrays.asList(courses);
    }

    @GetMapping("/offeredCourses")
    public List<String> getOfferedCourses() throws SQLException {
        Statement statement = createStatement();

        String getCoursesQuery = "SELECT course_number FROM courses";

        ResultSet rs = statement.executeQuery(getCoursesQuery);

        List<String> courseList = new ArrayList<>();

        while(rs.next()){
            courseList.add(rs.getString("course_number"));
        }

        return courseList;
    }

    @PostMapping("/addCourse")
    public Map<HttpStatus, String> addCourse(@RequestBody Map<String, String> credentials) throws SQLException {
        Statement statement = createStatement();

        //TODO Add error checking to make sure correct credentials are passed
        String email = credentials.get("email");
        String course_number = credentials.get("course_number");

        //The course is added to the courses list in the student database
        String studentCourseUpdate = "UPDATE students SET enrolled_course = array_append(enrolled_course, '%s') WHERE email = '%s'";
        String formattedUpdate = String.format(studentCourseUpdate,
                course_number,
                email);

        statement.executeUpdate(formattedUpdate);

        //The student id is retrieved to use in the courses database
        String studentIdQuery = "SELECT student_id FROM students WHERE email = '%s'";
        String formattedIdQuery = String.format(studentIdQuery,
                credentials.get("email"));
        ResultSet rs = statement.executeQuery(formattedIdQuery);
        rs.next();
        String student_id = rs.getString("student_id");

        //The student id is placed in the student list in the courses database
        String courseStudentListUpdate = "UPDATE courses SET student_list = array_append(student_list, '%s') WHERE course_number = '%s'";
        String formattedStudentListUpdate = String.format(courseStudentListUpdate,
                student_id,
                course_number);
        statement.executeUpdate(formattedStudentListUpdate);

        /*
         * There probably needs to be some error checking in here,
         * but for right now it just returns OK unless a SQLException is thrown
         */
        return Map.of(HttpStatus.OK, "Course Added");
    }

    @PostMapping("/removeCourse")
    public Map<HttpStatus, String> removeCourse(@RequestBody Map<String, String> credentials) throws SQLException {
        Statement statement = createStatement();

        //TODO Add error checking to make sure correct credentials are passed
        String course_number = credentials.get("course_number");
        String email = credentials.get("email");

        //Course is removed from the courses list in the student database
        String removeCourseUpdate = "UPDATE students SET enrolled_course = array_remove(enrolled_course, '%s') WHERE email = '%s'";
        String formattedRemoveUpdate = String.format(removeCourseUpdate,
                course_number,
                email);

        statement.executeUpdate(formattedRemoveUpdate);

        //The student id is retrieved to use in the courses database
        String studentIdQuery = "SELECT student_id FROM students WHERE email = '%s'";
        String formattedIdQuery = String.format(studentIdQuery,
                email);
        ResultSet rs = statement.executeQuery(formattedIdQuery);
        rs.next();
        String student_id = rs.getString("student_id");

        //The student id is removed from the student list in the courses database
        String removeStudentIdUpdate = "UPDATE courses SET student_list = array_remove(student_list, '%s') WHERE course_number = '%s'";
        String formattedRemoveIdUpdate = String.format(removeStudentIdUpdate,
                student_id,
                course_number);

        statement.executeUpdate(formattedRemoveIdUpdate);

        /*
         * There probably needs to be some error checking in here,
         * but for right now it just returns OK unless a SQLException is thrown
         */
        return Map.of(HttpStatus.OK, "Course Removed");
    }

    public boolean verifyCourseNumber(String id){
        if(!id.startsWith("CS")){
            return false;
        }
        if(id.length() != 5){
            return false;
        }
        return true;
    }

    public boolean verifyNonEnrolledCourse(String course, List<String> courseList){
        if(courseList.contains(course)){
            return false;
        }
        return true;
    }

    public boolean verifyStudentInCourse(String id, List<String> ids){
        if(!ids.contains(id)){
            return false;
        }
        return true;
    }

    private Statement createStatement() throws SQLException {
        return DatabaseHandler.getConnection().createStatement();
    }
}