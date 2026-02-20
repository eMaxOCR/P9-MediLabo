package com.medilabo.assessment_service.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.medilabo.assessment_service.model.Note;
import com.medilabo.assessment_service.model.Triggers;

@Service
public class AssessmentService {
	
	/**
	 * 
	 * */
	public Integer countTriggerOccurrences(Integer patientId, List<Note> notes) {

	    Triggers triggersWrapper = new Triggers();
	    List<String> triggerList = triggersWrapper.getTriggers();
	    Integer triggerCounter = 0;

	    if (notes == null || notes.isEmpty() || triggerList.isEmpty()) {
	        return 0;
	    }

	    StringBuilder fullContent = new StringBuilder();
	    for (Note n : notes) {
	        if (n.getNote() != null) {
	            fullContent.append(" ").append(n.getNote().toLowerCase());
	        }
	        
	    }
	    String consolidatedText = fullContent.toString();

	    for (String trigger : triggerList) {
	        if (consolidatedText.contains(trigger.toLowerCase())) {
	            triggerCounter++;
	        }
	    }

	    return triggerCounter;
	}
	
}
