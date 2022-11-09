package com.onrender.dawgchat.dawgchat;

import com.onrender.dawgchat.dawgchat.utils.PasswordManager;

import java.util.Map;

public class User {
    private String student_id, first_name, last_name, email, password;
    private String[] courses;

    public User(String student_id, String first_name, String last_name, String email, String password){
        this.student_id = student_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
    }

    public User(Map<String, String> credentials){
        this.student_id = credentials.get("student_id");
        this.first_name = credentials.get("first_name");
        this.last_name = credentials.get("last_name");
        this.email = credentials.get("email");
        this.password = PasswordManager.encryptPassword(credentials.get("password"));
    }

    public String getId() {
        return student_id;
    }

    public void setId(String student_id) {
        this.student_id = student_id;
    }

    public String getFirst() {
        return first_name;
    }

    public void setFirst(String first_name) {
        this.first_name = first_name;
    }

    public String getLast() {
        return last_name;
    }

    public void setLast(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String[] getCourses() {
        return courses;
    }

    public void setCourses(String[] courses) {
        this.courses = courses;
    }
}