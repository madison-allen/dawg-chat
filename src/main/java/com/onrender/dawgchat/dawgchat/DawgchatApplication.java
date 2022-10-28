package com.onrender.dawgchat.dawgchat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.sql.*;

@SpringBootApplication
@RestController
public class DawgchatApplication {

	public static void main(String[] args) {
		SpringApplication.run(DawgchatApplication.class, args);
	}

	@GetMapping("/")
	public String healthcheck(){
		return "200";
	}

	@GetMapping("/data")
	public String getData(){
		String url = System.getenv("INTERNAL_DB_URL");
		String user = System.getenv("DB_USER");
		String password = System.getenv("DB_PASSWORD");

		String output = "first name:";
		try(Connection conn = DriverManager.getConnection(url, user, password);
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
