package com.medilabo.patientservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.medilabo.patientservice.configuration.ResourceNotFoundException;
import com.medilabo.patientservice.model.Gender;
import com.medilabo.patientservice.model.Patient;
import com.medilabo.patientservice.repository.PatientRepository;
import com.medilabo.patientservice.service.PatientService;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    private Patient testPatient;

    @BeforeEach
    void setUp() {
    	Gender gender = null;
    	
        testPatient = new Patient();
        testPatient.setId(1);
        testPatient.setName("Jean");
        testPatient.setLastname("Dupont");
        testPatient.setGenre(gender.M);
    }

    @Test
    void getByIdTest() {
        when(patientRepository.findById(1)).thenReturn(Optional.of(testPatient));

        Patient result = patientService.getById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(patientRepository, times(1)).findById(1);
    }

    @Test
    void getByLastNameTest() {
        when(patientRepository.findByLastname("Dupont")).thenReturn(testPatient);

        Patient result = patientService.getByLastName("Dupont");

        assertNotNull(result);
        assertEquals("Dupont", result.getLastname());
        verify(patientRepository, times(1)).findByLastname("Dupont");
    }

    @Test
    void getByNameTest() {
        when(patientRepository.findByLastname("Dupont")).thenReturn(testPatient);

        Patient result = patientService.getByName("Dupont");

        assertNotNull(result);
        assertEquals("Dupont", result.getLastname());
        verify(patientRepository, times(1)).findByLastname("Dupont");
    }

    @Test
    void getAllPatientTest() {
        List<Patient> patients = Arrays.asList(testPatient, new Patient());
        when(patientRepository.findAll()).thenReturn(patients);

        Iterable<Patient> result = patientService.getAllPatient();

        assertNotNull(result);
        assertEquals(2, ((List<Patient>) result).size());
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    void existsByIdTest() {
        when(patientRepository.existsById(1)).thenReturn(true);

        Boolean result = patientService.existsById(1);

        assertTrue(result);
        verify(patientRepository, times(1)).existsById(1);
    }

    @Test
    void createTest() {
        when(patientRepository.save(any(Patient.class))).thenReturn(testPatient);

        Patient result = patientService.create(testPatient);

        assertNotNull(result);
        assertEquals("Jean", result.getName());
        verify(patientRepository, times(1)).save(testPatient);
    }

    @Test
    void updateTest() {
        Patient newInfo = new Patient();
        newInfo.setName("Jacques");
        newInfo.setLastname("Martin");

        when(patientRepository.findById(1)).thenReturn(Optional.of(testPatient));
        when(patientRepository.save(any(Patient.class))).thenReturn(testPatient);

        Patient result = patientService.update(1, newInfo);

        assertNotNull(result);
        assertEquals("Jacques", testPatient.getName());
        assertEquals("Martin", testPatient.getLastname());
        verify(patientRepository, times(1)).findById(1);
        verify(patientRepository, times(1)).save(testPatient);
    }

    @Test
    void updateExceptionTest() {
        Patient newInfo = new Patient();
        when(patientRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> patientService.update(99, newInfo));
        verify(patientRepository, times(1)).findById(99);
    }

    @Test
    void deleteTest() {
        when(patientRepository.existsById(1)).thenReturn(true);
        doNothing().when(patientRepository).deleteById(1);

        patientService.delete(1);

        verify(patientRepository, times(1)).existsById(1);
        verify(patientRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteExceptionTest() {
        when(patientRepository.existsById(99)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> patientService.delete(99));
        verify(patientRepository, times(1)).existsById(99);
    }
}