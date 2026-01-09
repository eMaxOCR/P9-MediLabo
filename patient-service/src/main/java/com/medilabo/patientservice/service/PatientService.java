package com.medilabo.patientservice.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.medilabo.patientservice.configuration.ResourceNotFoundException;
import com.medilabo.patientservice.model.Patient;
import com.medilabo.patientservice.repository.PatientRepository;

@Service
public class PatientService {

	@Autowired
	private PatientRepository patientRepository;
	
	/**
	 * Get one patient information
	 * @return Patient
	 * */
	public Patient getById(Integer id){
		return patientRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Patient not found with id : " + id));

	}
	
	/**
	 * Get one patient information
	 * @return Patient
	 * */
	public Patient getByLastName(String lastname){
		return patientRepository.findByLastname(lastname);
	}
	
	/**
	 * Get one patient information
	 * @return Patient
	 * */
	public Patient getByName(String lastname){
		return patientRepository.findByLastname(lastname);
	}
	
	/**
	 * Get list of patients informations
	 * */
	public Iterable<Patient> getAllPatient(){
		return patientRepository.findAll();
	}
	
	/**
	 * Check if patient exist
	 * */
	public Boolean existsById(Integer id){
		return patientRepository.existsById(id);
	}
	
	/**
	 * Create patient
	 * */
	public Patient create(Patient patient) {
		return patientRepository.save(patient);
	}
	
	public Patient update(Integer id, Patient newInformations) {
		
		Optional<Patient> optionalPatient = patientRepository.findById(id);
		
		if (optionalPatient.isPresent()) {
	        Patient existingPatient = optionalPatient.get();
	        BeanUtils.copyProperties(newInformations, existingPatient, "id");
	        return patientRepository.save(existingPatient);
	    } else{
	    	throw new ResourceNotFoundException("Patient not found with id: " + id);
	    }
	}
	
	
	/**
	 * Delete one patient information
	 * */
	public void delete(Integer id) {
		if(!existsById(id)) {
			throw new ResourceNotFoundException("Patient not found with that id : " + id);
		}
		patientRepository.deleteById(id);
	}
}
