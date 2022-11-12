package com.onrender.dawgchat.dawgchat.controllers;

import java.util.List;

public class RemoveCourse {

    /*
     * This class was only made for the purpose of having something to test
     * for assignment 3. It does not provide any functionality besides that.
     */

    public RemoveCourse(){

    }

    public boolean verifyCourseEnrollment(String course, List<String> courseList){
        if(!courseList.contains(course)){
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
}
