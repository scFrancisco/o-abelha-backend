package com.example.oabelha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class OabelhaApplication {

	public static void main(String[] args) {
		SpringApplication.run(OabelhaApplication.class, args);
	}

}
