package com.medilabo.assessment_service.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.medilabo.assessment_service.model.Note;
import com.medilabo.assessment_service.service.AssessmentService;
import com.medilabo.assessment_service.model.Patient;

import org.springframework.web.reactive.function.client.WebClient;

@RequestMapping("/assessment/")
@RestController
public class Controller {

	final String URL = "http://localhost:8080/";
	
	@Autowired
	private AssessmentService assessmentService;
	
	@Autowired
	private WebClient webClient;
	 
	/**
	 * Get assessment for the patient.
	 * @return Assessment
	 * */
	@GetMapping("/{id}")
	public String getOnePatient(@PathVariable("id") Integer id){
		
		//Take patient.
		Patient patient = webClient.get().uri(URL + "/api/patient/{id}", id).retrieve()
				.bodyToMono(Patient.class).block();
		
		//Take notes from patient.
		List<Note> notes = webClient.get().uri(URL + "api/note/user/{id}", id).retrieve()
				.bodyToFlux(Note.class).collectList().block();
		
		String assessmentResult = assessmentService.assessment(patient, notes);
		
		return assessmentResult;
	}
	}