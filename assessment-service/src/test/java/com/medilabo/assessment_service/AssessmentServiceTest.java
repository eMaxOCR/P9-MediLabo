package com.medilabo.assessment_service;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.medilabo.assessment_service.model.Gender;
import com.medilabo.assessment_service.model.Note;
import com.medilabo.assessment_service.model.Patient;
import com.medilabo.assessment_service.service.AssessmentService;

class AssessmentServiceTest {

    private AssessmentService assessmentService;

    @BeforeEach
    void setUp() {
        assessmentService = new AssessmentService();
    }

    @Test
    void testCalculateAge() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -23);
        Date birthDate = cal.getTime();

        int age = assessmentService.calculateAge(birthDate);

        assertThat(age).isEqualTo(23);
    }

    @Test
    void testCountTriggerOccurrences() {

        Note note1 = new Note();
        note1.setNote("Le patient a un poids élevé.");
        
        Note note2 = new Note();
        note2.setNote("Il faut surveiller son poids.");

        List<Note> notes = Arrays.asList(note1, note2);

        Integer count = assessmentService.countTriggerOccurrences(notes);

        assertThat(count).isEqualTo(2);
    }

    @Test
    void testDetermineRisk_EarlyOnset_Female_Under30() {

        Patient patient = new Patient();
        patient.setGenre(Gender.F);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -23);
        patient.setBirthdate(cal.getTime());

        String risk = assessmentService.determineRisk(patient, 7);

        assertThat(risk).isEqualTo("Early onset");
    }

    @Test
    void testDetermineRisk_InDanger_Male_Under30() {

        Patient patient = new Patient();
        patient.setGenre(Gender.M);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -25);
        patient.setBirthdate(cal.getTime());


        String risk = assessmentService.determineRisk(patient, 3);

        assertThat(risk).isEqualTo("In danger");
    }

    @Test
    void testDetermineRisk_Borderline_Over30() {

        Patient patient = new Patient();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -45);
        patient.setBirthdate(cal.getTime());


        String risk = assessmentService.determineRisk(patient, 2);

        assertThat(risk).isEqualTo("Borderline");
    }
}