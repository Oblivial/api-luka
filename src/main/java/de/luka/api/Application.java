package de.luka.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import de.luka.api.auth.user.UserController;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		System.out.println("Starting Application...");
		SpringApplication.run(Application.class, args);
	}
}
