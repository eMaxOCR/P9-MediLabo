package com.medilabo.assessment_service.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.medilabo.assessment_service.model.Note;
import com.medilabo.assessment_service.service.AssessmentService;
import com.medilabo.assessment_service.model.Patient;
import com.medilabo.assessment_service.proxy.NoteProxy;
import com.medilabo.assessment_service.proxy.PatientProxy;

@RequestMapping("/api/assessment")
@RestController
public class Controller {

    @Autowired
    private AssessmentService assessmentService;
    
    @Autowired
    private PatientProxy patientProxy;

    @Autowired
    private NoteProxy noteProxy;
     
    /**
     * Get assessment for the patient.
     * @return Assessment result string
     */
    @GetMapping("/{id}")
    public String getOnePatient(@PathVariable("id") Integer id){
        
        Patient patient = patientProxy.getPatientById(id);
        List<Note> notes = noteProxy.getNotesByPatientId(id);
        
        return assessmentService.assessment(patient, notes);
    }
}