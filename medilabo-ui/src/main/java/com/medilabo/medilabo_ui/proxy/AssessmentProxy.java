package com.medilabo.medilabo_ui.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "assessment-service", url = "${gateway.url}/api/assessment")
public interface AssessmentProxy {
    @GetMapping("/{id}")
    String getAssessmentByPatientId(@PathVariable("id") Integer id);
}