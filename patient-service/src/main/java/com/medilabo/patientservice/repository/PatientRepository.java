package com.medilabo.patientservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.medilabo.patientservice.model.Patient;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Integer>{
	/**
	 * Searching Patient by it's "lastname".
	 * @param user lastname.
	 * */
	public Patient findByLastname(String lastname);
	
	/**
	 * Searching Patient by it's "name".
	 * @param user name.
	 * */
	public Patient findByName(String name);
	
}
