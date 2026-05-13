package com.medilabo.patientservice; 

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import java.text.SimpleDateFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import com.medilabo.patientservice.model.Gender;
import com.medilabo.patientservice.model.Patient;
import com.medilabo.patientservice.proxy.NoteProxy;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class PatientIntegrationTest {

    @LocalServerPort
    private int port;

    @Value("${spring.security.user.name}")
    private String username;

    @Value("${spring.security.user.password}")
    private String password;

    @MockBean
    private NoteProxy noteProxy;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void createAndRetrievePatientTest() throws Exception {
        
        Patient newPatient = new Patient();
        newPatient.setName("Max");
        newPatient.setLastname("Dupont");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        newPatient.setBirthdate(sdf.parse("1990-01-01"));
        newPatient.setGenre(Gender.M);
        newPatient.setAddress("Rue de la casquette");
        newPatient.setPhoneNumber("0123456789");

        Integer createdPatientId = given()
            .auth().basic(username, password)
            .contentType(ContentType.JSON)
            .body(newPatient)
        .when()
            .post("/api/patient")
        .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("name", equalTo("Max"))
            .extract().path("id");

        given()
            .auth().basic(username, password)
        .when()
            .get("/api/patient/" + createdPatientId)
        .then()
            .statusCode(200)
            .body("lastname", equalTo("Dupont"))
            .body("address", equalTo("Rue de la casquette"))
            .body("genre", equalTo("M"));
    }
}