package com.medilabo.assessment_service.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.medilabo.assessment_service.model.Note;
import com.medilabo.assessment_service.service.AssessmentService;
import org.springframework.web.reactive.function.client.WebClient;

@RequestMapping("/assessment/")
@RestController
public class Controller {

	final String URL = "http://localhost:8080/";
	
	@Autowired
	private AssessmentService patientService;
	
	@Autowired
	private WebClient webClient;
	 
	/**
	 * Get assessment for the patient.
	 * @return Assessment
	 * */
	@GetMapping("/{id}")
	public Integer getOnePatient(@PathVariable("id") Integer id){
		
		List<Note> notes = webClient.get().uri(URL + "api/note/user/{id}", id).retrieve()
				.bodyToFlux(Note.class).collectList().block();
		
		Integer counter = patientService.countTriggerOccurrences(id, notes);
		
		return counter;
	}
	}