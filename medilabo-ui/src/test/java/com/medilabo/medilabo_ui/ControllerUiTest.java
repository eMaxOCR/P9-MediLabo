package com.medilabo.medilabo_ui;

import com.medilabo.medilabo_ui.controller.ControllerUi;
import com.medilabo.medilabo_ui.model.Gender;
import com.medilabo.medilabo_ui.model.Note;
import com.medilabo.medilabo_ui.model.Patient;
import com.medilabo.medilabo_ui.proxy.AssessmentProxy;
import com.medilabo.medilabo_ui.proxy.NoteProxy;
import com.medilabo.medilabo_ui.proxy.PatientProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Date;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ControllerUi.class)
@AutoConfigureMockMvc(addFilters = false)
class ControllerUiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientProxy patientProxy;

    @MockBean
    private NoteProxy noteProxy;

    @MockBean
    private AssessmentProxy assessmentProxy;

    private Patient patient;
    private Note note;

    @BeforeEach
    void setUp() {
        patient = new Patient();
        patient.setId(1);
        patient.setName("John");
        patient.setLastname("Doe");
        patient.setBirthdate(new Date());
        patient.setGenre(Gender.M);
        patient.setAddress("123 Main St");
        patient.setPhoneNumber("123-456-7890");

        note = new Note();
        note.setId("note-001");
        note.setPatientId(1);
        note.setNote("Patient en bonne santé.");
    }


    @Test
    void listPatientsTest() throws Exception {
        when(patientProxy.getAllPatients()).thenReturn(List.of(patient));

        mockMvc.perform(get("/patient"))
                .andExpect(status().isOk())
                .andExpect(view().name("patients"))
                .andExpect(model().attributeExists("patients"));

        verify(patientProxy, times(1)).getAllPatients();
    }

    @Test
    void patientTest() throws Exception {
        when(patientProxy.getPatientById(1)).thenReturn(patient);
        when(noteProxy.getNotesByPatientId(1)).thenReturn(List.of(note));
        when(assessmentProxy.getAssessmentByPatientId(1)).thenReturn("None");

        mockMvc.perform(get("/patient/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("patient"))
                .andExpect(model().attributeExists("patient"))
                .andExpect(model().attributeExists("notes"))
                .andExpect(model().attributeExists("assessment"))
                .andExpect(model().attribute("isNew", false));

        verify(patientProxy).getPatientById(1);
        verify(noteProxy).getNotesByPatientId(1);
        verify(assessmentProxy).getAssessmentByPatientId(1);
    }

    @Test
    void newPatientTest() throws Exception {
        mockMvc.perform(get("/patient/nouveau"))
                .andExpect(status().isOk())
                .andExpect(view().name("patient"))
                .andExpect(model().attributeExists("patient"))
                .andExpect(model().attribute("isNew", true));
    }

    @Test
    void savePatientTest() throws Exception {
        when(patientProxy.savePatient(any(Patient.class))).thenReturn(patient);

        mockMvc.perform(post("/patient")
                        .flashAttr("patient", patient))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patient"));

        verify(patientProxy, times(1)).savePatient(any(Patient.class));
    }

    @Test
    void updatePatientTest() throws Exception {
        when(patientProxy.updatePatient(eq(1), any(Patient.class))).thenReturn(patient);

        mockMvc.perform(post("/patient/1")
                        .flashAttr("patient", patient))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patient"));

        verify(patientProxy, times(1)).updatePatient(eq(1), any(Patient.class));
    }

    @Test
    void deletePatientTest() throws Exception {
        doNothing().when(patientProxy).deletePatient(1);

        mockMvc.perform(post("/patient/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patient"));

        verify(patientProxy, times(1)).deletePatient(1);
    }

    @Test
    void addNoteTest() throws Exception {
        mockMvc.perform(get("/note/ajouter").param("patientId", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("note"))
                .andExpect(model().attribute("isNew", true))
                .andExpect(model().attributeExists("note"));
    }

    @Test
    void getNoteTest() throws Exception {
        when(noteProxy.getNoteById("note-001")).thenReturn(note);

        mockMvc.perform(get("/note/edit/note-001"))
                .andExpect(status().isOk())
                .andExpect(view().name("note"))
                .andExpect(model().attribute("isNew", false))
                .andExpect(model().attributeExists("note"));
    }

    @Test
    void getNoteFailedTest() throws Exception {
        mockMvc.perform(get("/note/edit/null"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patient"));
    }

    @Test
    void saveNoteTest() throws Exception {
        when(noteProxy.saveNote(any(Note.class))).thenReturn(note);

        mockMvc.perform(post("/note")
                        .flashAttr("note", note))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patient/1"));

        verify(noteProxy, times(1)).saveNote(any(Note.class));
    }

    @Test
    void deleteNoteTest() throws Exception {
        doNothing().when(noteProxy).deleteNote("note-001");

        mockMvc.perform(post("/note/delete/note-001").param("patientId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patient/1"));

        verify(noteProxy, times(1)).deleteNote("note-001");
    }

//    @ParameterizedTest(name = "Patient {0} → diagnostic attendu : {1}")
//    @CsvSource({
//        "1, None",
//        "2, Borderline",
//        "3, In Danger",
//        "4, Early onset"
//    })
//    void getPatient_assessmentDiagnostic_parametrized(Integer patientId, String expectedAssessment) throws Exception {
//        when(patientProxy.getPatientById(patientId)).thenReturn(patient);
//        when(noteProxy.getNotesByPatientId(patientId)).thenReturn(List.of(note));
//        when(assessmentProxy.getAssessmentByPatientId(patientId)).thenReturn(expectedAssessment);
//
//        mockMvc.perform(get("/patient/{id}", patientId))
//                .andExpect(status().isOk())
//                .andExpect(view().name("patient"))
//                .andExpect(model().attribute("assessment", expectedAssessment));
//
//        verify(assessmentProxy).getAssessmentByPatientId(patientId);
//    }
}
