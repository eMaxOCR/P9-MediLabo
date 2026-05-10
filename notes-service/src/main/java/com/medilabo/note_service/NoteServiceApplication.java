package com.medilabo.note_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class NoteServiceApplication{

	@Autowired


	public static void main(String[] args) {

		SpringApplication.run(NoteServiceApplication.class, args);

	}


}