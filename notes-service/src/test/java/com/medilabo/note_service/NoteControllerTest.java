package com.medilabo.note_service;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medilabo.note_service.model.Note;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;


@SpringBootTest
@AutoConfigureMockMvc
public class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetNoteStatus() throws Exception {
        // Test de récupération d'une note (si l'ID existe ou via mock)
        mockMvc.perform(get("/note/user/1"))
               .andExpect(status().isOk());
    }

    @Test
    void testSaveNoteStatus() throws Exception {
        Note note = new Note();
        note.setPatientId(1);
        note.setNote("Test d'intégration");

        mockMvc.perform(post("/note")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(note)))
               .andExpect(status().isCreated());
    }

    @Test
    void testDeleteNoteStatus() throws Exception {
        // Supposons que l'ID existe ou que le service ne plante pas sur un ID inconnu
        mockMvc.perform(delete("/note/999"))
               .andExpect(status().isOk());
    }
}