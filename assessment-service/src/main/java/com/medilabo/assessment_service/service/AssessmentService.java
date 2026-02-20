package com.medilabo.assessment_service.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssessmentService {

	@Autowired
	//private PatientRepository patientRepository;
	
	/**
	 * Get patient's note
	 * @param Patient ID
	 * @return List of notes
	 * */
	public List<String>getPatientNotes(Integer id){
		//Call gateway for patient's notes.
		return null;

	}
	
	public Integer occurrenceAssessment(List<String>triggers, Integer patientId){
		return null;
	}
	
	//Intéroger Note-service, donner liste des termes, le patient
	// -> Renvoyer nombre d'occurence.
	
	//Calculer les risques ici.
	
}
