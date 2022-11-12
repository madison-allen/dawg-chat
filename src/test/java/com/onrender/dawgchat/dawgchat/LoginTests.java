package com.onrender.dawgchat.dawgchat;

import com.onrender.dawgchat.dawgchat.controllers.Login;
import com.onrender.dawgchat.dawgchat.utils.PasswordManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(Lifecycle.PER_CLASS)
public class LoginTests {

    Login login;

    @BeforeAll
    public void setup(){
        login = new Login();
    }

    @Test
    public void verifyPasswordTest(){
        String password = "password";
        String encryptedPassword = PasswordManager.encryptPassword(password);
        assertTrue(login.verifyPassword(password, encryptedPassword));

        password = "password12";
        encryptedPassword = PasswordManager.encryptPassword(password);
        assertTrue(login.verifyPassword(password, encryptedPassword));

        password = "password12!";
        encryptedPassword = PasswordManager.encryptPassword(password);
        assertTrue(login.verifyPassword(password, encryptedPassword));

        password = "password!";
        encryptedPassword = PasswordManager.encryptPassword(password);
        assertTrue(login.verifyPassword(password, encryptedPassword));
    }
}
