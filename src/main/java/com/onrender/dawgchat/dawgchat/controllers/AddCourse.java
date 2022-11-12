package com.onrender.dawgchat.dawgchat.controllers;

import java.util.List;

public class AddCourse {

    /*
     * This class was only made for the purpose of having something to test
     * for assignment 3. It does not provide any functionality besides that.
     */

    public AddCourse(){

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
}
