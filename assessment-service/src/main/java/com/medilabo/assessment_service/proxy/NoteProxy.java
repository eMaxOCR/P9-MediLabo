package com.medilabo.assessment_service.proxy;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.medilabo.assessment_service.model.Note;
import com.medilabo.assessment_service.configuration.FeignConfig;

@FeignClient(name = "note-service", url = "${service.note.url}", configuration = FeignConfig.class)
public interface NoteProxy {
    
    @GetMapping("/api/note/user/{patientId}")
    List<Note> getNotesByPatientId(@PathVariable("patientId") Integer patientId);
    
}