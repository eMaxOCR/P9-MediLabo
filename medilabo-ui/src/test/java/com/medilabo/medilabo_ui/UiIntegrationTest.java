package com.medilabo.medilabo_ui;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import com.medilabo.medilabo_ui.model.Patient;
import com.medilabo.medilabo_ui.proxy.AssessmentProxy;
import com.medilabo.medilabo_ui.proxy.NoteProxy;
import com.medilabo.medilabo_ui.proxy.PatientProxy;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientProxy patientProxy;

    @MockBean
    private NoteProxy noteProxy;

    @MockBean
    private AssessmentProxy assessmentProxy;

    @Test
    @WithMockUser(username = "testuser", roles = "USER") 
    public void getPatientListTest() throws Exception {
    	
        when(patientProxy.getAllPatients()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/patient"))
               .andExpect(status().isOk())
               .andExpect(view().name("patients"))
               .andExpect(model().attributeExists("patients")); 
    }

    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    public void postNewPatientTest() throws Exception {
        Patient newPatient = new Patient();
        newPatient.setName("Max");
        newPatient.setLastname("Dupont");

        when(patientProxy.savePatient(org.mockito.ArgumentMatchers.any(Patient.class))).thenReturn(newPatient);

        mockMvc.perform(post("/patient")
               .flashAttr("patient", newPatient)) 
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/patient")); 
    }
}