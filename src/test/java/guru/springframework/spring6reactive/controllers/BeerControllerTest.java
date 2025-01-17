package guru.springframework.spring6reactive.controllers;

import guru.springframework.spring6reactive.domain.Beer;
import guru.springframework.spring6reactive.model.BeerDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static guru.springframework.spring6reactive.repositories.BeerRepositoryTest.getBeer;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOAuth2Login;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureWebTestClient
class BeerControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
    }

    @Test
    @Order(1)
    void listBeers() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .get()
            .uri(BeerController.BEER_PATH)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().valueEquals("Content-Type", "application/json")
            .expectBody().jsonPath("$.size()").isEqualTo(3);
    }

    @Test
    @Order(2)
    void getBeerById() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .get()
            .uri(BeerController.BEER_PATH + BeerController.BEER_PATH_ID, 1)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().valueEquals("Content-Type", "application/json")
            .expectBody(BeerDTO.class);
    }

    @Test
    void getBeerById_notFound() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .get()
            .uri(BeerController.BEER_PATH + BeerController.BEER_PATH_ID, 999)
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    @Order(3)
    void createNewBeer() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .post()
            .uri(BeerController.BEER_PATH)
            .body(Mono.just(getBeer()), BeerDTO.class)
            .header("Content-Type", "application/json")
            .exchange()
            .expectStatus().isOk()
            .expectBody(BeerDTO.class);
    }

    @Test
    void createNewBeer_badRequest() {
        Beer beer = getBeer();
        beer.setBeerName("");
        webTestClient
            .mutateWith(mockOAuth2Login())
            .post()
            .uri(BeerController.BEER_PATH)
            .body(Mono.just(beer), BeerDTO.class)
            .header("Content-Type", "application/json")
            .exchange()
            .expectStatus().isBadRequest();
    }

    @Test
    @Order(4)
    void updateBeer() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .put()
            .uri(BeerController.BEER_PATH + BeerController.BEER_PATH_ID, 1)
            .body(Mono.just(getBeer()), BeerDTO.class)
            .header("Content-Type", "application/json")
            .exchange()
            .expectStatus().isOk()
            .expectBody(BeerDTO.class);
    }

    @Test
    void updateBeer_notFound() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .put()
            .uri(BeerController.BEER_PATH + BeerController.BEER_PATH_ID, 999)
            .body(Mono.just(getBeer()), BeerDTO.class)
            .header("Content-Type", "application/json")
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    @Order(5)
    void patchBeer() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .patch()
            .uri(BeerController.BEER_PATH + BeerController.BEER_PATH_ID, 2)
            .body(Mono.just(getBeer()), BeerDTO.class)
            .header("Content-Type", "application/json")
            .exchange()
            .expectStatus().isOk()
            .expectBody(BeerDTO.class);
    }

    @Test
    void patchBeer_notFound() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .patch()
            .uri(BeerController.BEER_PATH + BeerController.BEER_PATH_ID, 999)
            .body(Mono.just(getBeer()), BeerDTO.class)
            .header("Content-Type", "application/json")
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    @Order(6)
    void deleteById() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .delete()
            .uri(BeerController.BEER_PATH + BeerController.BEER_PATH_ID, 2)
            .exchange()
            .expectStatus().isNoContent();
    }

    @Test
    void deleteById_notFound() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .delete()
            .uri(BeerController.BEER_PATH + BeerController.BEER_PATH_ID, 999)
            .exchange()
            .expectStatus().isNotFound();
    }
}
