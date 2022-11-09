package com.onrender.dawgchat.dawgchat.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordManager {

    public static String encryptPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    //returns true if the plain text password matches the encrypted password
    public static boolean checkPassword(String regularPassword, String encryptedPassword){
        return BCrypt.checkpw(regularPassword, encryptedPassword);
    }
}