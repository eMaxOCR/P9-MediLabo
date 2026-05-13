package com.medilabo.assessment_service;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import java.io.IOException;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class AssessmentIntegrationTest {

    static MockWebServer mockPatientServer;
    static MockWebServer mockNoteServer;

    @Autowired
    private TestRestTemplate restTemplate;

    @DynamicPropertySource
    static void configureFeign(DynamicPropertyRegistry registry) {
        registry.add("service.patient.url", () -> mockPatientServer.url("/").toString());
        registry.add("service.note.url",    () -> mockNoteServer.url("/").toString());
    }

    @BeforeAll
    static void startServers() throws IOException {
        mockPatientServer = new MockWebServer();
        mockNoteServer    = new MockWebServer();
        mockPatientServer.start();
        mockNoteServer.start();
    }

    @AfterAll
    static void stopServers() throws IOException {
        mockPatientServer.shutdown();
        mockNoteServer.shutdown();
    }

    @Test
    void noCredentialsTest() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("", "")
                .getForEntity("/assessment/1", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void wrongCredentialsTest() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("bad-user", "bad-pass")
                .getForEntity("/assessment/1", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void noneRiskTest() {
        mockPatientServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json")
                .setBody("""
                        {"id":1,"name":"Max","lastname":"Dupont",
                         "birthdate":"1970-01-01T00:00:00.000+00:00",
                         "genre":"M","address":"1 rue test","phoneNumber":"0600000000"}
                        """));

        mockNoteServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json")
                .setBody("[]"));

        ResponseEntity<String> response = restTemplate
                .withBasicAuth("test-user", "test-pass")
                .getForEntity("/api/assessment/1", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("None");
    }

    @Test
    void borderlineRiskTest() {
        mockPatientServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json")
                .setBody("""
                        {"id":2,"name":"Max","lastname":"Dupont",
                         "birthdate":"1975-06-15T00:00:00.000+00:00",
                         "genre":"M","address":"2 rue test","phoneNumber":"0600000001"}
                        """));

        mockNoteServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json")
                .setBody("""
                        [{"id":"n1","patientId":2,"note":"Taille anormale."},
                         {"id":"n2","patientId":2,"note":"Problème de Poids."}]
                        """));

        ResponseEntity<String> response = restTemplate
                .withBasicAuth("test-user", "test-pass")
                .getForEntity("/api/assessment/2", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Borderline");
    }
}