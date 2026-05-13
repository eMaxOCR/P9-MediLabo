package com.medilabo.gatewayservice;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class GatewayIntegrationTest {

    private static final String USERNAME = "test-user";
    private static final String PASSWORD = "test-pass";
    private static WireMockServer wireMockServer;

    @Autowired
    private WebTestClient webTestClient;
    
    @TestConfiguration
    static class TestSecurityConfig {

        @Bean
        @Primary
        public MapReactiveUserDetailsService testUserDetailsService() {
            return new MapReactiveUserDetailsService(
                    User.withUsername(USERNAME)
                            .password("{noop}" + PASSWORD)
                            .roles("SYSTEM")
                            .build()
            );
        }
    }

    @BeforeAll
    static void startWireMock() {
        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(8089));
        wireMockServer.start();
        wireMockServer.stubFor(get(urlEqualTo("/patient/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\"}")));
    }

    @AfterAll
    static void stopWireMock() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }

    @Test
    void givenNoCredentialsTest() {
        webTestClient.get()
                .uri("/api/patient/1")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void givenWrongCredentialsTest() {
        webTestClient.get()
                .uri("/api/patient/1")
                .headers(h -> h.setBasicAuth("bad-user", "bad-pass"))
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void givenValidCredentialsTest() {
        webTestClient.get()
                .uri("/api/patient/1")
                .headers(h -> h.setBasicAuth(USERNAME, PASSWORD))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.firstName").isEqualTo("John");
    }
}