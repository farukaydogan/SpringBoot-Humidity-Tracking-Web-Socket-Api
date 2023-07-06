package com.humidty.arge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.TimeZone;

@SpringBootApplication
@EnableScheduling
public class ArgeApplication {
	public static void main(String[] args) {
		System.out.println("Setting the timezone"+ TimeZone.getTimeZone("GMT+3:00").getID());
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+3:00"));
		SpringApplication.run(ArgeApplication.class, args);
	}
}
