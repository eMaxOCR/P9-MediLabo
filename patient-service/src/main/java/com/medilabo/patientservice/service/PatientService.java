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
	 * @param Patient ID
	 * @return Patient
	 * */
	public Patient getById(Integer id){
		return patientRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Patient not found with id : " + id));

	}
	
	/**
	 * Get one patient information
	 * @param Patient's last name
	 * @return Patient
	 * */
	public Patient getByLastName(String lastname){
		return patientRepository.findByLastname(lastname);
	}
	
	/**
	 * Get one patient information
	 * @param Patient's last name
	 * @return Patient
	 * */
	public Patient getByName(String lastname){
		return patientRepository.findByLastname(lastname);
	}
	
	/**
	 * Get list of patients informations
	 * @return list of patient
	 * */
	public Iterable<Patient> getAllPatient(){
		return patientRepository.findAll();
	}
	
	/**
	 * Check if patient exist
	 * @return Patient
	 * */
	public Boolean existsById(Integer id){
		return patientRepository.existsById(id);
	}
	
	/**
	 * Create patient
	 * @return Patient
	 * */
	public Patient create(Patient patient) {
		return patientRepository.save(patient);
	}
	
	/**
	 * Update patient's informations
	 * @param Patient with new informations
	 * @return Patient
	 * */
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
	 * @param patient's ID
	 * */
	public void delete(Integer id) {
		if(!existsById(id)) {
			throw new ResourceNotFoundException("Patient not found with that id : " + id);
		}
		patientRepository.deleteById(id);
	}
}
