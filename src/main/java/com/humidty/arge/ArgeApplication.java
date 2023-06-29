package com.humidty.arge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ArgeApplication {
	public static void main(String[] args) {
		SpringApplication.run(ArgeApplication.class, args);
	}
}
