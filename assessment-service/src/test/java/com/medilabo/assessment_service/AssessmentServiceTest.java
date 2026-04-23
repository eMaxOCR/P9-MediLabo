package com.medilabo.assessment_service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.medilabo.assessment_service.model.Gender;
import com.medilabo.assessment_service.model.LevelRisk;
import com.medilabo.assessment_service.model.Note;
import com.medilabo.assessment_service.model.Patient;
import com.medilabo.assessment_service.service.AssessmentService;

class AssessmentServiceTest {

    private AssessmentService assessmentService;

    @BeforeEach
    void setUp() {
        assessmentService = new AssessmentService();
    }
    
	private Date generateBirthdate(int targerAge) {
        LocalDate dateNaissance = LocalDate.now().minusYears(targerAge);
        return Date.from(dateNaissance.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    @Test
    void calculateAgeTest() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -23);
        Date birthDate = cal.getTime();

        int age = assessmentService.calculateAge(birthDate);

        assertThat(age).isEqualTo(23);
    }

    @Test
    void countTriggerOccurrencesTest() {

        Note note1 = new Note();
        note1.setNote("Le patient a un poids élevé.");
        
        Note note2 = new Note();
        note2.setNote("Il faut surveiller son poids.");

        List<Note> notes = Arrays.asList(note1, note2);

        Integer count = assessmentService.countTriggerOccurrences(notes);

        assertThat(count).isEqualTo(2);
    }
    
    @ParameterizedTest(name = "Âge: {0}, Genre: {1}, Déclencheurs: {2} = Attendu: {3}")
    @CsvSource({
        "35, M, 8, EARLY_ONSET",
        "35, F, 9, EARLY_ONSET",
        "25, M, 5, EARLY_ONSET",
        "25, M, 6, EARLY_ONSET",
        "25, F, 7, EARLY_ONSET",
        "25, F, 8, EARLY_ONSET",

        "35, M, 6, IN_DANGER",
        "35, F, 7, IN_DANGER",
        "25, M, 3, IN_DANGER",
        "25, M, 4, IN_DANGER",
        "25, F, 4, IN_DANGER",
        "25, F, 6, IN_DANGER",
        
        "35, M, 2, BORDERLINE",
        "35, F, 5, BORDERLINE",

        "35, M, 1, NONE",
        "35, F, 0, NONE",
        "25, M, 2, NONE",
        "25, F, 3, NONE",

        "30, M, 9, NONE",
        "30, F, 9, NONE"
    })
    void determineRiskTest(int age, Gender genre, int triggerCount, String expectedRiskEnum) {
        
        Patient patient = new Patient();
        patient.setGenre(genre);
        patient.setBirthdate(generateBirthdate(age));

        String resultatAttendu = expectedRiskEnum.equals("NONE") ? "None" : LevelRisk.valueOf(expectedRiskEnum).getLibelle();
        
        String resultatObtenu = assessmentService.determineRisk(patient, triggerCount);

        assertEquals(resultatAttendu, resultatObtenu);
    }
    
    @Test
    void assessmentTest() {

        AssessmentService service = spy(new AssessmentService());
        Patient patient = new Patient();
        List<Note> notes = new ArrayList<>();


        doReturn(3).when(service).countTriggerOccurrences(notes);
        doReturn("In Danger").when(service).determineRisk(patient, 3);


        String resultat = service.assessment(patient, notes);


        assertEquals("In Danger", resultat);

        verify(service).countTriggerOccurrences(notes);
        verify(service).determineRisk(patient, 3);
    }

    
}