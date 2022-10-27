package com.onrender.dawgchat.dawgchat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
