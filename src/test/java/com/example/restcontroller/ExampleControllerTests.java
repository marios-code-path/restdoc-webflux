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
import reactor.core.publisher.Mono;

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
    void shouldSquareNumber() {
        this.client
                .get()
                .uri("/compute/square/3.0")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    Assertions
                            .assertThat(new String(res.getResponseBodyContent()))
                            .isNotNull()
                            .isEqualTo("9.0");
                    document("square").accept(res);
                });
    }

    @Test
    void shouldRootOfNumber() {
        this.client
                .get()
                .uri("/compute/root/9.0")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    Assertions
                            .assertThat(new String(res.getResponseBodyContent()))
                            .isNotNull()
                            .isEqualTo("3.0");
                    document("root").accept(res);
                });
    }

    @Test
    void shouldSumNumbers() {
        this.client
                .post()
                .uri("/compute/sum")
                .bodyValue(new Double[]{1.0,2.0,3.0})
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    Assertions
                            .assertThat(new String(res.getResponseBodyContent()))
                            .isNotNull()
                            .isEqualTo("6.0");
                    document("sum").accept(res);
                });
    }
}
