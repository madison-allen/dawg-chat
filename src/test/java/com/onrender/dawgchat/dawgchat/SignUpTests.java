package com.onrender.dawgchat.dawgchat;

import com.onrender.dawgchat.dawgchat.controllers.SignUp;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SignUpTests {

    SignUp signUp;
    @BeforeAll
    public void setup(){
        signUp = new SignUp();
    }

    @Test
    public void verifyEmailTest(){
        assertTrue(signUp.validateEmail("test@siu.edu"));
        assertFalse(signUp.validateEmail("test@t.com"));
        assertFalse(signUp.validateEmail("@siu.edu"));
    }

    @Test
    public void verifyStudentId(){
        assertTrue(signUp.validateId("siu850000001"));
        assertFalse(signUp.validateId("siu85123"));
        assertFalse(signUp.validateId("siu85123456789"));
        assertFalse(signUp.validateId("123456789012"));
    }
}
