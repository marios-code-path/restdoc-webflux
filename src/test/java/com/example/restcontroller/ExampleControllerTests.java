package com.example.restcontroller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration;

@WebFluxTest(ExampleController.class)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class ExampleControllerTests {

    private WebTestClient client;

    @BeforeEach
    void setUp(ApplicationContext ctx, RestDocumentationContextProvider restDocumentation) {
        this.client = WebTestClient.bindToApplicationContext(ctx).configureClient()
                .filter(documentationConfiguration(restDocumentation))
                .build();

    }

    @Test
    void shouldGetOK() {
        this.client
                .get()
                .uri("/ok")
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk()
                .expectHeader()
                .contentTypeCompatibleWith(MediaType.TEXT_PLAIN)
                .expectBody()
                .consumeWith(res -> {
                    Assertions
                            .assertThat(new String(res.getResponseBodyContent()))
                            .isNotNull()
                            .isEqualTo("OK");
                    document("ok").accept(res);
                });
    }

    @Test
    void shouldGetHello() {
        this.client
                .get()
                .uri("/hello/test")
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk()
                .expectHeader()
                .contentTypeCompatibleWith(MediaType.TEXT_PLAIN)
                .expectBody()
                .consumeWith(res -> {
                    Assertions
                            .assertThat(new String(res.getResponseBodyContent()))
                            .isNotNull()
                            .isEqualTo("Hello test");
                    document("hello").accept(res);
                });
    }
}
