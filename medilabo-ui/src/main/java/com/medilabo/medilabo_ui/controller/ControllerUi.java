package com.medilabo.medilabo_ui.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.client.WebClient;
import com.medilabo.medilabo_ui.model.Patient;

@Controller
public class ControllerUi {
	  
	@Autowired
    private WebClient webClient;
	 
	/**
	 * Retreive all patient
	 * @return the patient list view
	 * */
	@GetMapping("/patient")
	public String listPatients(Model model) {
	    List<Patient> patients = webClient.get()
	        .uri("http://localhost:8080/api/patient")
	        .retrieve()
	        .bodyToFlux(Patient.class) 
	        .collectList()
	        .block();

	    model.addAttribute("patients", patients); 
	    return "patients";
	}
	
	/**
	 * Retreive one patient
	 * @return the patient's information view
	 * */
	@GetMapping("/patient/{id}")
	public String patient(@PathVariable("id") Integer id, Model model) {
	    Patient patient = webClient.get()
		        .uri("http://localhost:8080/api/patient/{id}",id)
		        .retrieve()
		        .bodyToMono(Patient.class) 
		        .block();

	    model.addAttribute("isNew", false); 
	    model.addAttribute("patient", patient); 
	    return "patient";
	}
	
	/**
	 * Return creation view
	 * @return new patient view
	 * */
	@GetMapping("/patient/nouveau")
	public String newPatient(Model model) {
	    model.addAttribute("patient", new Patient());
	    model.addAttribute("isNew", true); 
	    return "patient";
	}
	 
	/**
	 * Create one new patient
	 * */
	@PostMapping("/patient")
	public String savePatient(@ModelAttribute("patient") Patient patient) {
	    webClient.post()
	    	.uri("http://localhost:8080/api/patient")
	    	.bodyValue(patient)
	    	.retrieve()
	    	.bodyToMono(Patient.class)
	    	.block();

	    return "redirect:/patient";
	}
	
	/**
	 * Update patient's informations
	 * */
	@PostMapping("/patient/{id}")
	public String updatePatient(@PathVariable("id") Integer id, @ModelAttribute("patient") Patient patient) {
		
	    patient.setId(id);

	    webClient.put()
	        .uri("http://localhost:8080/api/patient/{id}", id)
	        .bodyValue(patient)
	        .retrieve()
	        .bodyToMono(Patient.class)
	        .block();

	    return "redirect:/patient";
	}
	 
	/**
	 * Delete one patient
	 * @return the patient's information view
	 * */
	@PostMapping("/patient/delete/{id}")
	public String patient(@PathVariable("id") Integer id) {
	    webClient.delete()
		        .uri("http://localhost:8080/api/patient/{id}",id)
		        .retrieve()
		        .toBodilessEntity()
		        .block();

	    return "redirect:/patient";
	}

}
