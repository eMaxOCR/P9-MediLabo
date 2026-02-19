package com.medilabo.medilabo_ui.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.medilabo.medilabo_ui.model.Patient;
import com.medilabo.medilabo_ui.model.Note;

@Controller
public class ControllerUi {

	// Créer une variable, Ajouter l'url et le port.

	@Autowired
	private WebClient webClient;

	/**
	 * Retreive all patient
	 * 
	 * @return the patient list view
	 */
	@GetMapping("/patient")
	public String listPatients(Model model) {
		List<Patient> patients = webClient.get().uri("http://localhost:8080/api/patient").retrieve()
				.bodyToFlux(Patient.class).collectList().block();

		model.addAttribute("patients", patients);
		return "patients";
	}

	/**
	 * Retreive one patient
	 * 
	 * @return the patient's information view
	 */
	@GetMapping("/patient/{id}")
	public String patient(@PathVariable("id") Integer id, Model model) {
		Patient patient = webClient.get().uri("http://localhost:8080/api/patient/{id}", id).retrieve()
				.bodyToMono(Patient.class).block();
		List<Note> notes = webClient.get().uri("http://localhost:8080/api/note/user/{id}", id).retrieve()
				.bodyToFlux(Note.class).collectList().block();

		model.addAttribute("isNew", false);
		model.addAttribute("patient", patient);
		model.addAttribute("notes", notes);
		return "patient";
	}

	/**
	 * Return creation view
	 * 
	 * @return new patient view
	 */
	@GetMapping("/patient/nouveau")
	public String newPatient(Model model) {
		model.addAttribute("patient", new Patient());
		model.addAttribute("isNew", true);
		return "patient";
	}

	/**
	 * Create one new patient
	 */
	@PostMapping("/patient")
	public String savePatient(@ModelAttribute("patient") Patient patient) {
		webClient.post().uri("http://localhost:8080/api/patient").bodyValue(patient).retrieve()
				.bodyToMono(Patient.class).block();

		return "redirect:/patient";
	}

	/**
	 * Update patient's informations
	 */
	@PostMapping("/patient/{id}")
	public String updatePatient(@PathVariable("id") Integer id, @ModelAttribute("patient") Patient patient) {

		patient.setId(id);

		webClient.put().uri("http://localhost:8080/api/patient/{id}", id).bodyValue(patient).retrieve()
				.bodyToMono(Patient.class).block();

		return "redirect:/patient";
	}

	/**
	 * Delete one patient
	 * @return the patient's information view
	 */
	@PostMapping("/patient/delete/{id}")
	public String patient(@PathVariable("id") Integer id) {
		webClient.delete().uri("http://localhost:8080/api/patient/{id}", id).retrieve().toBodilessEntity().block();

		return "redirect:/patient";
	}

	/** 
	 * Retreive one note
	 * @return note
	 */
	@GetMapping("/note/{id}")
	public String getNote(@PathVariable("id") String id, Model model) {
		Note note = webClient.get().uri("http://localhost:8080/api/note/{id}", id).retrieve().bodyToMono(Note.class)
				.block();

		model.addAttribute("isNew", false);
		model.addAttribute("note", note);
		return "note";
	}
	
	/**
	 * Show note view for new note
	 * */
	@GetMapping("/note/ajouter")
	public String addNote(@RequestParam("patientId") Integer patientId, Model model) {
	    Note note = new Note();
	    note.setPatientId(patientId); 
	    
	    model.addAttribute("note", note);
	    model.addAttribute("isNew", true);
	    return "note"; 
	}

	/**
	 * Create one new note
	 */
	@PostMapping("/note")
	public String saveNote(@ModelAttribute("note") Note note) {
	    webClient.post()
	        .uri("http://localhost:8080/api/note")
	        .bodyValue(note)
	        .retrieve()
	        .bodyToMono(Note.class)
	        .block();

	    return "redirect:/patient/" + note.getPatientId();
	}
	 
//	/**
//	 * Update note
//	 */
//	@PostMapping("/note/{id}")
//	public String updateNote(@PathVariable("id") String id, @ModelAttribute("note") Note note) {
//	    
//	    note.setId(id);
//
//	    webClient.put()
//	            .uri("http://localhost:8080/api/note/{id}", id) 
//	            .bodyValue(note)
//	            .retrieve()
//	            .bodyToMono(Note.class)
//	            .block();
//
//	    return "redirect:/patient/" + note.getPatientId();
//	}
 
	/**
	 * Delete one note
	 * @return the patient detail view
	 */
	@PostMapping("/note/delete/{id}")
	public String deleteNote(@PathVariable("id") String id, @RequestParam("patientId") Integer patientId) {
		webClient.delete().uri("http://localhost:8080/api/note/{id}", id).retrieve().toBodilessEntity().block();

		return "redirect:/patient/" + patientId;
	}

}
