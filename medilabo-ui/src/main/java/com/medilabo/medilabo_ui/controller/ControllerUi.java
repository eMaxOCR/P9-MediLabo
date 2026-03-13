package com.medilabo.medilabo_ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.medilabo.medilabo_ui.model.Patient;
import com.medilabo.medilabo_ui.proxy.AssessmentProxy;
import com.medilabo.medilabo_ui.proxy.NoteProxy;
import com.medilabo.medilabo_ui.proxy.PatientProxy;
import com.medilabo.medilabo_ui.model.Note;

@Controller
public class ControllerUi {

    @Autowired
    private PatientProxy patientProxy; 
    
    @Autowired
    private NoteProxy noteProxy;

    @Autowired
    private AssessmentProxy assessmentProxy;

    /**
	 * Retreive all patient
	 * @return the patient list view
	 */
    @GetMapping("/patient")
    public String listPatients(Model model) {
        model.addAttribute("patients", patientProxy.getAllPatients());
        return "patients";
    }

	/**
	 * Retreive one patient
	 * @return the patient's information view
	 */
    @GetMapping("/patient/{id}")
    public String patient(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("isNew", false);
        model.addAttribute("patient", patientProxy.getPatientById(id));
        model.addAttribute("notes", noteProxy.getNotesByPatientId(id));
        model.addAttribute("assessment", assessmentProxy.getAssessmentByPatientId(id));
        return "patient";
    }

	/**
	 * Return creation view
	 * @return new patient view
	 */
    @GetMapping("/patient/nouveau")
    public String newPatient(Model model) {
        model.addAttribute("patient", new Patient());
        model.addAttribute("isNew", true);
        return "patient";
    }

	/**
	 * Create one new patient
	 */
    @PostMapping("/patient")
    public String savePatient(@ModelAttribute("patient") Patient patient) {
        patientProxy.savePatient(patient);
        return "redirect:/patient";
    }

	/**
	 * Update patient's informations
	 */
    @PostMapping("/patient/{id}")
    public String updatePatient(@PathVariable("id") Integer id, @ModelAttribute("patient") Patient patient) {
        patientProxy.updatePatient(id, patient);
        return "redirect:/patient";
    }

	/**
	 * Delete one patient
	 * @return the patient's information view
	 */
    @PostMapping("/patient/delete/{id}")
    public String deletePatient(@PathVariable("id") Integer id) {
        patientProxy.deletePatient(id);
        return "redirect:/patient";
    }

	/** 
	 * Retreive one note
	 * @return note
	 */
    @GetMapping("/note/ajouter")
    public String addNote(@RequestParam("patientId") Integer patientId, Model model) {
        Note note = new Note();
        note.setPatientId(patientId); 
        model.addAttribute("note", note);
        model.addAttribute("isNew", true);
        return "note"; 
    }

	/**
	 * Show note view for new note
	 * */
    @GetMapping("/note/edit/{id}")
    public String getNote(@PathVariable("id") String id, Model model) {
        if (id == null || id.isEmpty() || id.equals("null")) {
            return "redirect:/patient";
        }
        
        Note note = noteProxy.getNoteById(id);
        model.addAttribute("isNew", false);
        model.addAttribute("note", note);
        return "note";
    }

	/**
	 * Create one new note
	 */
    @PostMapping("/note")
    public String saveNote(@ModelAttribute("note") Note note) {
        // LA SÉCURITÉ QUI MANQUAIT :
        // Si l'ID est un texte vide (venant du formulaire), on le remet à null
        if (note.getId() != null && note.getId().isEmpty()) {
            note.setId(null);
        }

        noteProxy.saveNote(note);
        return "redirect:/patient/" + note.getPatientId();
    }

	/**
	 * Delete one note
	 * @return the patient detail view
	 */
    @PostMapping("/note/delete/{id}")
    public String deleteNote(@PathVariable("id") String id, @RequestParam("patientId") Integer patientId) {
        noteProxy.deleteNote(id);
        return "redirect:/patient/" + patientId;
    }
}