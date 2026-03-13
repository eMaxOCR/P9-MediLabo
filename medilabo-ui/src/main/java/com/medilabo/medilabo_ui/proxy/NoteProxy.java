package com.medilabo.medilabo_ui.proxy;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.medilabo.medilabo_ui.model.Note;

@FeignClient(name = "note-service", url = "http://localhost:8080/api/note")
public interface NoteProxy {
    @GetMapping("/user/{patientId}")
    List<Note> getNotesByPatientId(@PathVariable("patientId") Integer patientId);

    @GetMapping("/{id}")
    Note getNoteById(@PathVariable("id") String id);

    @PostMapping
    Note saveNote(@RequestBody Note note);

    @DeleteMapping("/{id}")
    void deleteNote(@PathVariable("id") String id);
}