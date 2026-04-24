package com.medilabo.patientservice;

import com.medilabo.patientservice.controller.Controller;
import com.medilabo.patientservice.model.Patient;
import com.medilabo.patientservice.service.PatientService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(Controller.class)
@AutoConfigureMockMvc(addFilters = false)
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @Autowired
    private ObjectMapper objectMapper;

    private Patient patient;

    @BeforeEach
    void setUp() {
        patient = new Patient();
        patient.setId(1);
        patient.setName("John");
        patient.setLastname("Doe");
        patient.setAddress("123 Main St");
        patient.setPhoneNumber("0600000000");
    }

    @Test
    void getOnePatientTest() throws Exception {
        when(patientService.getById(1)).thenReturn(patient);

        mockMvc.perform(get("/patient/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.lastname").value("Doe"));

        verify(patientService, times(1)).getById(1);
    }

    @Test
    void getAllPatientsTest() throws Exception {
        when(patientService.getAllPatient()).thenReturn(List.of(patient));

        mockMvc.perform(get("/patient"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("John"));

        verify(patientService, times(1)).getAllPatient();
    }

    @Test
    void addPatientTest() throws Exception {
        when(patientService.create(any(Patient.class))).thenReturn(patient);

        mockMvc.perform(post("/patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.lastname").value("Doe"));

        verify(patientService, times(1)).create(any(Patient.class));
    }

    @Test
    void updatePatientTest() throws Exception {
        patient.setName("Jane");
        when(patientService.update(eq(1), any(Patient.class))).thenReturn(patient);

        mockMvc.perform(put("/patient/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jane"));

        verify(patientService, times(1)).update(eq(1), any(Patient.class));
    }

    @Test
    void deletePatientTest() throws Exception {
        doNothing().when(patientService).delete(1);

        mockMvc.perform(delete("/patient/1"))
                .andExpect(status().isOk());

        verify(patientService, times(1)).delete(1);
    }
}