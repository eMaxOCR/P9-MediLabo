package com.medilabo.assessment_service.proxy;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.medilabo.assessment_service.model.Note;

@FeignClient(name = "note-service", url = "${service.note.url}")
public interface NoteProxy {
    @GetMapping("note/user/{patientId}")
    List<Note> getNotesByPatientId(@PathVariable("patientId") Integer patientId);
}


