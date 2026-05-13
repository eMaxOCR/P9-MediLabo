package com.medilabo.medilabo_ui.proxy;

import com.medilabo.medilabo_ui.configuration.FeignConfig;
import com.medilabo.medilabo_ui.model.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

//@FeignClient(name = "patient-service", url = "${gateway.url}/api/patient")
@FeignClient(name = "patient-service", url = "${gateway.url}/api/patient", configuration = FeignConfig.class)
public interface PatientProxy {

    @GetMapping
    List<Patient> getAllPatients();

    @GetMapping("/{id}")
    Patient getPatientById(@PathVariable("id") Integer id);

    @PostMapping
    Patient savePatient(@RequestBody Patient patient);

    @PutMapping("/{id}")
    Patient updatePatient(@PathVariable("id") Integer id, @RequestBody Patient patient);

    @DeleteMapping("/{id}")
    void deletePatient(@PathVariable("id") Integer id);
}