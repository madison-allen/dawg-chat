package com.onrender.dawgchat.dawgchat;

import com.onrender.dawgchat.dawgchat.controllers.RemoveCourse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(Lifecycle.PER_CLASS)
public class RemoveCourseTests {

    RemoveCourse removeCourse;
    @BeforeAll
    public void setup(){
        removeCourse = new RemoveCourse();
    }

    @Test
    public void verifyCourseEnrollmentTest(){
        assertTrue(removeCourse.verifyCourseEnrollment("CS435", List.of("CS412", "CS490", "CS435")));
        assertTrue(removeCourse.verifyCourseEnrollment("CS435", List.of("CS435")));
        assertFalse(removeCourse.verifyCourseEnrollment("CS435", List.of("CS412", "CS490")));
        assertFalse(removeCourse.verifyCourseEnrollment("CS435", new ArrayList<>()));
        assertFalse(removeCourse.verifyCourseEnrollment("CS435", List.of("CS490")));
    }

    @Test
    public void verifyStudentInCourseTest(){
        assertTrue(removeCourse.verifyStudentInCourse("siu850000001", List.of("siu850000001", "siu850000002", "siu850000003")));
        assertTrue(removeCourse.verifyStudentInCourse("siu850000001", List.of("siu850000001")));
        assertFalse(removeCourse.verifyStudentInCourse("siu850000001", List.of("siu850000002", "siu850000003")));
        assertFalse(removeCourse.verifyStudentInCourse("siu850000001", new ArrayList<>()));
        assertFalse(removeCourse.verifyStudentInCourse("siu850000001", List.of("siu850000002")));
    }
}
