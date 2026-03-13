package com.medilabo.assessment_service.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.medilabo.assessment_service.model.Patient;

@FeignClient(name = "patient-service", url = "http://localhost:8080/api/patient")
public interface PatientProxy {
    @GetMapping("/{id}")
    Patient getPatientById(@PathVariable("id") Integer id);
}