package com.onrender.dawgchat.dawgchat;

public class Constants {

    //Database credentials are set as environment variables on the hosting website
    public final static String DB_URL = System.getenv("INTERNAL_DB_URL");
    public final static String DB_USER = System.getenv("DB_USER");
    public final static String DB_PASSWORD = System.getenv("DB_PASSWORD");
}
