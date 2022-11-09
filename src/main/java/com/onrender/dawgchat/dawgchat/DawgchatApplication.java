package com.onrender.dawgchat.dawgchat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@CrossOrigin
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@RestController
public class DawgchatApplication {

	public static void main(String[] args) {
		SpringApplication.run(DawgchatApplication.class, args);
	}

	//endpoint to check if the service is running
	@GetMapping("/healthcheck")
	public Map<HttpStatus, String> healthcheck(){
		return Map.of(HttpStatus.OK, "Program is Running");
	}
}