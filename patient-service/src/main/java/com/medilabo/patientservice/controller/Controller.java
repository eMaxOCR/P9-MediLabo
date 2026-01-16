package com.medilabo.patientservice.controller;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	 * @return patient
	 * */
	@GetMapping("/{id}")
	public Patient getOnePatient(@PathVariable("id") Integer id){
		return patientService.getById(id);
		
	}
	
	/**
	 * Get list of patients informations
	 * @return list of patients.
	 * */
	@GetMapping()
	public Iterable<Patient> getAllPatients(){
		return patientService.getAllPatient();
	}
	
	/**
	 * Create patient
	 * @param Patient 
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
	 * Update patient
	 * @param Id, Patient (with new informations)
	 * */
	@PutMapping("/{id}")
	public ResponseEntity<Patient> updatePatient(@PathVariable Integer id, @RequestBody Patient patientDetails) {
	    Patient updated = patientService.update(id, patientDetails);
	    return ResponseEntity.ok(updated);
	}
	
	/**
	 * Delete one patient information
	 * @param Patient's id
	 * */
	@DeleteMapping("/{id}")
	public void deletePatient(@PathVariable("id") Integer id){
		patientService.delete(id);
	}
	
}
