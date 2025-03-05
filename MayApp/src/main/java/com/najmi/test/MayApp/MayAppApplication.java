package com.najmi.test.MayApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MayAppApplication {

	public static void main(String[] args) {
		System.out.println("Application started");
		SpringApplication.run(MayAppApplication.class, args);
		System.out.println("Application ended");
	}

}
