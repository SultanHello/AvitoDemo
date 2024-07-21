package com.example.AvitoDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.AvitoDemo"})
public class AvitoDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AvitoDemoApplication.class, args);
	}

}
