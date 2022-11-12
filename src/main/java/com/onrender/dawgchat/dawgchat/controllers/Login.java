package com.onrender.dawgchat.dawgchat.controllers;

import com.onrender.dawgchat.dawgchat.utils.PasswordManager;

public class Login {

    /*
     * This class was only made for the purpose of having something to test
     * for assignment 3. It does not provide any functionality besides that.
     */

    public Login(){

    }

    public boolean verifyPassword(String password, String encryptedPassword){

        return PasswordManager.checkPassword(password, encryptedPassword);
    }
}
