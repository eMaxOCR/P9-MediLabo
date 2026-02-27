package com.medilabo.assessment_service.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import com.medilabo.assessment_service.model.Gender;
import com.medilabo.assessment_service.model.Note;
import com.medilabo.assessment_service.model.Patient;
import com.medilabo.assessment_service.model.Triggers;

@Service
public class AssessmentService {

	/**
	 * From list of notes, count occurrence from trigger words.
	 * 
	 * @param List of Notes
	 * @return Number of occurrence
	 */
	public Integer countTriggerOccurrences(List<Note> notes) {

		Triggers triggersWrapper = new Triggers();
	    List<String> triggerList = triggersWrapper.getTriggers();
	    int triggerCounter = 0;

	    if (notes == null || notes.isEmpty()) {
	        return 0;
	    }

	    for (Note n : notes) {
	        if (n.getNote() != null) {
	            String noteContent = n.getNote().toLowerCase();
	            
	            for (String trigger : triggerList) {
	                if (noteContent.contains(trigger.toLowerCase())) {
	                    triggerCounter++;
	                }
	            }
	        }
	    }
	    System.out.println(triggerCounter);
	    return triggerCounter;
	}
	
	/**
	 * Calculate age with birthdate
	 * @param Date
	 * @Return Age
	 * */
	public int calculateAge(Date birthDate) {
	    if (birthDate == null) {
	        return 0;
	    }
	    
	    LocalDate localBirthDate = birthDate.toInstant()
	                                         .atZone(ZoneId.systemDefault())
	                                         .toLocalDate();
                          
	    return Period.between(localBirthDate, LocalDate.now()).getYears();
	}

	/**
	 * Determine risk for patient
	 * @param Patient, triggerCount
	 * @return Risk term
	 * */
	public String determineRisk(Patient patient, Integer triggerCount) {

		Integer age = calculateAge(patient.getBirthdate());
		Gender genre = patient.getGenre();
		
		//Early onset
		if (age > 30 && triggerCount >= 8) {
		    return "Early onset";
		}
		if (genre == Gender.M && age < 30 && triggerCount >= 5) {
		    return "Early onset";
		}
		if (genre == Gender.F && age < 30 && triggerCount >= 7) {
		    return "Early onset";
		}
		
		//In Danger
		if (age > 30 && triggerCount >= 6) {
		    return "In Danger";
		}
		if(genre == Gender.M && age < 30 && triggerCount >= 3 ) {
			return "In danger";
		}
		if (genre == Gender.F && age < 30 && triggerCount >= 4) {
		    return "In Danger";
		}
		
		//Borderline
		if(age > 30 && triggerCount >= 2 && triggerCount<=5 ) {
			return "Borderline";
		}
		
		//Default
		return "None";

	}
	
	/**
	 * Assessment risk of diabate
	 * @param patient, list of notes
	 * @return Assessment result
	 * */
	public String assessment(Patient patient, List<Note> notes) {
		String result = determineRisk(patient, countTriggerOccurrences(notes));
		return result;
	}

}