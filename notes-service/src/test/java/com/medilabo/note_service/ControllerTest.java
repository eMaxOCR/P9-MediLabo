package com.medilabo.note_service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medilabo.note_service.controller.controller;
import com.medilabo.note_service.model.Note;
import com.medilabo.note_service.service.NoteService;

@ExtendWith(MockitoExtension.class)
public class ControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private NoteService noteService;

    @InjectMocks
    private controller controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getNote() throws Exception {
        Note note = new Note();
        note.setId("note123");
        note.setPatientId(1);
        when(noteService.findById("note123")).thenReturn(note);

        mockMvc.perform(get("/note/note123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("note123"));
    }

    @Test
    void getAllPatientNote() throws Exception {
        Note note = new Note();
        note.setId("note123");
        note.setPatientId(1);
        when(noteService.getAllPatientNote(1)).thenReturn(List.of(note));

        mockMvc.perform(get("/note/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void save() throws Exception {
        Note note = new Note();
        note.setId("note123");
        note.setPatientId(1);
        when(noteService.save(any(Note.class))).thenReturn(note);

        mockMvc.perform(post("/note")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(note)))
                .andExpect(status().isCreated());
    }

    @Test
    void deleteNote() throws Exception {
        doNothing().when(noteService).delete("note123");

        mockMvc.perform(delete("/note/note123"))
                .andExpect(status().isOk());
    }
}