package com.onrender.dawgchat.dawgchat;

import com.onrender.dawgchat.dawgchat.controllers.Courses;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AddCourseTests {

    Courses addCourse;

    @BeforeAll
    public void setup(){
        addCourse = new Courses();
    }

    @Test
    public void verifyCourseNumberTest(){
        assertTrue(addCourse.verifyCourseNumber("CS435"));
        assertFalse(addCourse.verifyCourseNumber("435"));
        assertFalse(addCourse.verifyCourseNumber("CS43"));
    }

    @Test
    public void verifyNonEnrolledCourseTest(){
        assertFalse(addCourse.verifyNonEnrolledCourse("CS435", List.of("CS412", "CS490", "CS435")));
        assertFalse(addCourse.verifyNonEnrolledCourse("CS435", List.of("CS435")));
        assertTrue(addCourse.verifyNonEnrolledCourse("CS435", List.of("CS412", "CS490")));
        assertTrue(addCourse.verifyNonEnrolledCourse("CS435", new ArrayList<>()));
        assertTrue(addCourse.verifyNonEnrolledCourse("CS435", List.of("CS490")));
    }
}
