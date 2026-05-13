package com.medilabo.patientservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.medilabo.patientservice.configuration.FeignConfig;


@FeignClient(name = "note-service", url = "${note-service.url}", configuration = FeignConfig.class)
public interface NoteProxy {

    @DeleteMapping("/api/note/patient/{patientId}")
    void deleteAllNotesByPatientId(@PathVariable("patientId") Integer patientId);
}