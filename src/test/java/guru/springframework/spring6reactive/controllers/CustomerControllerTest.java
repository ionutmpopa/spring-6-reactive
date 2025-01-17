package guru.springframework.spring6reactive.controllers;

import guru.springframework.spring6reactive.domain.Customer;
import guru.springframework.spring6reactive.model.CustomerDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static guru.springframework.spring6reactive.repositories.CustomerRepositoryTest.getCustomer;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOAuth2Login;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureWebTestClient
class CustomerControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
    }

    @Test
    @Order(1)
    void listCustomers() {
        webTestClient
            .mutateWith(mockOAuth2Login()).get()
            .uri(CustomerController.CUSTOMER_PATH)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().valueEquals("Content-Type", "application/json")
            .expectBody().jsonPath("$.size()").isEqualTo(3);
    }

    @Test
    @Order(2)
    void getCustomerById() {
        webTestClient
            .mutateWith(mockOAuth2Login()).get()
            .uri(CustomerController.CUSTOMER_PATH + CustomerController.CUSTOMER_PATH_ID, 1)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().valueEquals("Content-Type", "application/json")
            .expectBody(CustomerDTO.class);
    }

    @Test
    void getCustomerById_notFound() {
        webTestClient
            .mutateWith(mockOAuth2Login()).get()
            .uri(CustomerController.CUSTOMER_PATH + CustomerController.CUSTOMER_PATH_ID, 999)
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    @Order(3)
    void createNewCustomer() {
        webTestClient
            .mutateWith(mockOAuth2Login()).post()
            .uri(CustomerController.CUSTOMER_PATH)
            .body(Mono.just(getCustomer()), CustomerDTO.class)
            .header("Content-Type", "application/json")
            .exchange()
            .expectStatus().isOk()
            .expectBody(CustomerDTO.class);
    }

    @Test
    void createNewCustomer_badRequest() {
        Customer customer = getCustomer();
        customer.setCustomerName("");
        webTestClient
            .mutateWith(mockOAuth2Login()).post()
            .uri(BeerController.BEER_PATH)
            .body(Mono.just(customer), CustomerDTO.class)
            .header("Content-Type", "application/json")
            .exchange()
            .expectStatus().isBadRequest();
    }

    @Test
    @Order(4)
    void updateCustomer() {
        webTestClient
            .mutateWith(mockOAuth2Login()).put()
            .uri(CustomerController.CUSTOMER_PATH + CustomerController.CUSTOMER_PATH_ID, 1)
            .body(Mono.just(getCustomer()), CustomerDTO.class)
            .header("Content-Type", "application/json")
            .exchange()
            .expectStatus().isOk()
            .expectBody(CustomerDTO.class);
    }

    @Test
    void updateCustomer_notFound() {
        webTestClient
            .mutateWith(mockOAuth2Login()).put()
            .uri(CustomerController.CUSTOMER_PATH + CustomerController.CUSTOMER_PATH_ID, 999)
            .body(Mono.just(getCustomer()), CustomerDTO.class)
            .header("Content-Type", "application/json")
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    @Order(5)
    void patchCustomer() {
        webTestClient
            .mutateWith(mockOAuth2Login()).patch()
            .uri(CustomerController.CUSTOMER_PATH + CustomerController.CUSTOMER_PATH_ID, 2)
            .body(Mono.just(getCustomer()), CustomerDTO.class)
            .header("Content-Type", "application/json")
            .exchange()
            .expectStatus().isOk()
            .expectBody(CustomerDTO.class);
    }

    @Test
    @Order(5)
    void patchCustomer_notFound() {
        webTestClient
            .mutateWith(mockOAuth2Login()).patch()
            .uri(CustomerController.CUSTOMER_PATH + CustomerController.CUSTOMER_PATH_ID, 999)
            .body(Mono.just(getCustomer()), CustomerDTO.class)
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
            .uri(CustomerController.CUSTOMER_PATH + CustomerController.CUSTOMER_PATH_ID, 2)
            .exchange()
            .expectStatus().isNoContent();
    }

    @Test
    void deleteById_notFound() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .delete()
            .uri(CustomerController.CUSTOMER_PATH + CustomerController.CUSTOMER_PATH_ID, 999)
            .exchange()
            .expectStatus().isNotFound();
    }
}
