package com.medilabo.patientservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
	public Optional<Patient> getById(Integer id){
		return patientRepository.findById(id);
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
	
	/**
	 * Delete one patient information
	 * */
	public void delete(Integer id) {
		patientRepository.deleteById(id);
	}
}
