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
        webTestClient.get()
            .uri(BeerController.BEER_PATH)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().valueEquals("Content-Type", "application/json")
            .expectBody().jsonPath("$.size()").isEqualTo(3);
    }

    @Test
    @Order(2)
    void getBeerById() {
        webTestClient.get()
            .uri(BeerController.BEER_PATH + BeerController.BEER_PATH_ID, 1)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().valueEquals("Content-Type", "application/json")
            .expectBody(BeerDTO.class);
    }

    @Test
    @Order(3)
    void createNewBeer() {
        webTestClient.post()
            .uri(BeerController.BEER_PATH)
            .body(Mono.just(getBeer()), BeerDTO.class)
            .header("Content-Type", "application/json")
            .exchange()
            .expectStatus().isOk()
            .expectBody(BeerDTO.class);
    }

    @Test
    @Order(3)
    void createNewBeer_badRequest() {
        Beer beer = getBeer();
        beer.setBeerName("");
        webTestClient.post()
            .uri(BeerController.BEER_PATH)
            .body(Mono.just(beer), BeerDTO.class)
            .header("Content-Type", "application/json")
            .exchange()
            .expectStatus().isBadRequest();
    }

    @Test
    @Order(4)
    void updateBeer() {
        webTestClient.put()
            .uri(BeerController.BEER_PATH + BeerController.BEER_PATH_ID, 1)
            .body(Mono.just(getBeer()), BeerDTO.class)
            .header("Content-Type", "application/json")
            .exchange()
            .expectStatus().isOk()
            .expectBody(BeerDTO.class);
    }

    @Test
    @Order(5)
    void patchBeer() {
        webTestClient.patch()
            .uri(BeerController.BEER_PATH + BeerController.BEER_PATH_ID, 2)
            .body(Mono.just(getBeer()), BeerDTO.class)
            .header("Content-Type", "application/json")
            .exchange()
            .expectStatus().isOk()
            .expectBody(BeerDTO.class);
    }

    @Test
    @Order(998)
    void deleteById() {
        webTestClient.delete()
            .uri(BeerController.BEER_PATH + BeerController.BEER_PATH_ID, 2)
            .exchange()
            .expectStatus().isNoContent();
    }

    @Test
    @Order(999)
    void deleteById_notFound() {
        webTestClient.delete()
            .uri(BeerController.BEER_PATH + BeerController.BEER_PATH_ID, 666)
            .exchange()
            .expectStatus().isNotFound();
    }
}
