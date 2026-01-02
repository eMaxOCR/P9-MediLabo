package com.medilabo.patientservice.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.medilabo.patientservice.model.Patient;
import com.medilabo.patientservice.service.PatientService;

@RequestMapping("/patient")
@RestController
public class Controller {

	@Autowired
	private PatientService patientService;
	
	/**
	 * Get one patient information
	 * */
	@GetMapping("/{id}")
	public ResponseEntity<Patient> getOnePatient(@PathVariable("id") Integer id){
		if (patientService.existsById(id)) {
			return ResponseEntity.ok(patientService.getById(id).get());
		}else {
			return ResponseEntity.notFound().build();
		}
		
	}
	
	/**
	 * Get list of patients informations
	 * */
	@GetMapping("/")
	public ResponseEntity<Iterable<Patient>> getAllPatients(){
		return ResponseEntity.ok(patientService.getAllPatient());
	}
	
	/**
	 * Create patient
	 * */
	@PostMapping()
	public ResponseEntity<Patient> addPatient(@RequestBody Patient patient){
		
		Patient newPatient = patientService.create(patient);
		URI patient1 = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{name}/{lastname}/{birthdate}/{gender}/{address}/{phoneNumber}")
				.buildAndExpand(newPatient.getName(),newPatient.getLastname(),newPatient.getBirthdate(),newPatient.getGenre(),newPatient.getAddress(),newPatient.getPhoneNumber())
                .toUri(); //Sent URI to header.
		return ResponseEntity.created(patient1).body(newPatient);
	}
	
	/**
	 * Update one patient information
	 * */
	
	/**
	 * Delete one patient information
	 * @param Patient's id
	 * */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePatient(@PathVariable("id") Integer id){
		if (patientService.existsById(id)) {
			patientService.delete(id);
			return ResponseEntity.noContent().build();
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
}
