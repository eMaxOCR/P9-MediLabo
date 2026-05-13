package com.medilabo.note_service;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import com.medilabo.note_service.model.Note;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "de.flapdoodle.mongodb.embedded.version=6.0.5")
public class NoteIntegrationTest {

    @LocalServerPort
    private int port;

    @Value("${spring.security.user.name}")
    private String username;

    @Value("${spring.security.user.password}")
    private String password;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void createAndRetrieveNoteTest() {
        
        Note newNote = new Note();
        newNote.setPatientId(1);
        newNote.setNote("Note du medecin");

        given()
            .auth().basic(username, password)
            .contentType(ContentType.JSON)
            .body(newNote)
        .when()
            .post("/api/note")
        .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("patientId", equalTo(1))
            .body("note", equalTo("Note du medecin"));

        given()
            .auth().basic(username, password)
        .when()
            .get("/api/note/user/1")
        .then()
            .statusCode(200)
            .body("[0].patientId", equalTo(1))
            .body("[0].note", equalTo("Note du medecin"));
    }
}