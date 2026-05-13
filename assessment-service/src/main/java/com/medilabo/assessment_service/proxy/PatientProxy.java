package com.medilabo.assessment_service.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.medilabo.assessment_service.configuration.FeignConfig;
import com.medilabo.assessment_service.model.Patient;

//@FeignClient(name = "patient-service", url = "${service.patient.url}")
@FeignClient(name = "patient-service", url = "${service.patient.url}", configuration = FeignConfig.class)
public interface PatientProxy {
    @GetMapping("/api/patient/{id}")
    Patient getPatientById(@PathVariable("id") Integer id);
}