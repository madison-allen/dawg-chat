package com.onrender.dawgchat.dawgchat;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordManager {

    public static String encryptPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String regularPassword, String encryptedPassword){
        return BCrypt.checkpw(regularPassword, encryptedPassword);
    }
}
