package com.example.restcontroller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.cloud.contract.wiremock.restdocs.SpringCloudContractRestDocs;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;

@WebFluxTest(ExampleController.class)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@AutoConfigureRestDocs
public class ExampleControllerTests {

    @Autowired
    private WebTestClient client;
//
//    @BeforeEach
//    void setUp(ApplicationContext ctx, RestDocumentationContextProvider restDocumentation) {
//        this.client = WebTestClient.bindToApplicationContext(ctx).configureClient()
//                .filter(documentationConfiguration(restDocumentation))
//                .build();
//    }

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
                    document("square",
                            SpringCloudContractRestDocs.dslContract()).accept(res);
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
                    document("root",
                            SpringCloudContractRestDocs.dslContract()).accept(res);
                });
    }

    @Test
    void shouldSumNumbers() {
        this.client
                .post()
                .uri("/compute/sum")
                .bodyValue(new Double[]{1.0, 2.0, 3.0})
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
                    document("sum",
                            SpringCloudContractRestDocs.dslContract()).accept(res);
                });
    }
}