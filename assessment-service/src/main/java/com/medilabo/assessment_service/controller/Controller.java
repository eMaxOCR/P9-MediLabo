package com.medilabo.assessment_service.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medilabo.assessment_service.service.AssessmentService;

@RequestMapping("/assessment/")
@RestController
public class Controller {

	@Autowired
	private AssessmentService patientService;
	
	/**
	 * Get assessment for the patient.
	 * @return Assessment
	 * */
	@GetMapping("/{id}")
	public String getOnePatient(@PathVariable("id") Integer id){
		return null;
	}
	}