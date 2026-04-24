package com.medilabo.patientservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "note-service", url = "${note-service.url:http://localhost:8083}")
public interface NoteProxy {

    @DeleteMapping("/note/patient/{patientId}")
    void deleteAllNotesByPatientId(@PathVariable("patientId") Integer patientId);
}