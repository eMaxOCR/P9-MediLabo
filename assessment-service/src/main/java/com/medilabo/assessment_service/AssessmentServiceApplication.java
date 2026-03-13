package com.medilabo.assessment_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AssessmentServiceApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(AssessmentServiceApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception{
		
		System.out.println("Serveur Online !");
		
	}

}
