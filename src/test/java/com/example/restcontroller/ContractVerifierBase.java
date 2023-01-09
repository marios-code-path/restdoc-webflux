package com.example.restcontroller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "server.port=0")
public abstract class ContractVerifierBase {

    @LocalServerPort
    int port;

    // TODO
    // HIT A WALL - where rest-assured-web-tet-client doesnt appear when using
    // restdocs-web-test-client
    // Apparently there is a limitation here, and I should do this a different way
    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost:" + this.port;
    }
}