package com.medilabo.assessment_service;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.io.IOException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@SpringBootTest(properties = {
	    "service.patient.url=http://localhost:8080", 
	    "service.note.url=http://localhost:8080"
	})
@AutoConfigureMockMvc
public class ControllerTest {
    private static MockWebServer mockBackEnd;
    
    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start(8080);
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @Test
    @WithMockUser(username = "system-user", roles = {"SYSTEM"})
    void testStatusOk() throws Exception {

        mockBackEnd.enqueue(new MockResponse()
            .setBody("{\"id\":1, \"genre\":\"M\"}")
            .addHeader("Content-Type", "application/json"));
        
        mockBackEnd.enqueue(new MockResponse()
            .setBody("[]")
            .addHeader("Content-Type", "application/json"));

        mockMvc.perform(get("/assessment/1"))
               .andExpect(status().isOk());
    }
}