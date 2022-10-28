package com.onrender.dawgchat.dawgchat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.sql.*;
import static com.onrender.dawgchat.dawgchat.Constants.*;

@SpringBootApplication
@RestController
public class DawgchatApplication {

	public static void main(String[] args) {
		SpringApplication.run(DawgchatApplication.class, args);
	}

	//endpoint to check if the service is running
	@GetMapping("/healthcheck")
	public String healthcheck(){
		return "200";
	}

	//example of how to query database using constants
	@GetMapping("/data")
	public String getData(){
		String output = "";
		try(Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM students");) {
			while (rs.next()) {
				output += rs.getString("first_name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return output;
	}
}
