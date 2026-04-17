package com.medilabo.note_service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.medilabo.note_service.model.Note;
import com.medilabo.note_service.repository.NoteRepository;
import com.medilabo.note_service.service.NoteService;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    @Test
    void testFindById_Success() {

        Note note = new Note();
        note.setId("1");
        note.setNote("Test note");
        when(noteRepository.findById("1")).thenReturn(Optional.of(note));

        Note result = noteService.findById("1");

        assertThat(result).isNotNull();
        assertThat(result.getNote()).isEqualTo("Test note");
    }

    @Test
    void testSave() {
        Note note = new Note();
        note.setNote("New note");
        when(noteRepository.save(any(Note.class))).thenReturn(note);

        Note savedNote = noteService.save(note);

        assertThat(savedNote.getNote()).isEqualTo("New note");
        verify(noteRepository).save(note);
    }

    @Test
    void testDelete() {
        Note note = new Note();
        note.setId("1");
        when(noteRepository.findById("1")).thenReturn(Optional.of(note));

        noteService.delete("1");

        verify(noteRepository).deleteById("1");
    }
    
    @Test
    void testGetAllPatientNote() {

        Note note = new Note();
        note.setPatientId(1);
        when(noteRepository.findByPatientId(1)).thenReturn(List.of(note));

        Iterable<Note> result = noteService.getAllPatientNote(1);

        assertThat(result).hasSize(1);
        verify(noteRepository).findByPatientId(1);
    }

    @Test
    void testUpdate_Success() {

        Note existingNote = new Note();
        existingNote.setId("abc");
        existingNote.setNote("Old Note");

        Note newContent = new Note();
        newContent.setNote("New Content");

        when(noteRepository.findById("abc")).thenReturn(Optional.of(existingNote));
        when(noteRepository.save(any(Note.class))).thenAnswer(i -> i.getArguments()[0]);

        Note updated = noteService.update("abc", newContent);

        assertThat(updated.getNote()).isEqualTo("New Content");
        assertThat(updated.getId()).isEqualTo("abc"); 
    }
}