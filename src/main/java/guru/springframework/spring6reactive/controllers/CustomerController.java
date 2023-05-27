package guru.springframework.spring6reactive.controllers;

import guru.springframework.spring6reactive.model.CustomerDTO;
import guru.springframework.spring6reactive.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RequestMapping(CustomerController.CUSTOMER_PATH)
@RestController
public class CustomerController {

    public static final String CUSTOMER_PATH = "/api/v2/customer";
    public static final String CUSTOMER_PATH_ID = "/{customerId}";

    private final CustomerService customerService;

    @GetMapping
    Flux<CustomerDTO> listCustomers() {
        return customerService.listCustomers();
    }

    @GetMapping(value = CUSTOMER_PATH_ID)
    Mono<CustomerDTO> getCustomerById(@PathVariable("customerId") Integer customerId) {
        return customerService.getCustomerById(customerId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Mono<ResponseEntity<CustomerDTO>> createNewCustomer(@Validated @RequestBody CustomerDTO customerDTO) {
        return customerService.createNewCustomer(customerDTO)
            .map(customerDTO1 -> {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.add("Location", "http://localhost:8080/" + CUSTOMER_PATH + "/" + customerDTO1.getId());
                return ResponseEntity.ok().headers(httpHeaders).body(customerDTO1);
            });
    }

    @PutMapping(value = CUSTOMER_PATH_ID, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Mono<ResponseEntity<CustomerDTO>> updateCustomer(@PathVariable("customerId") Integer customerId, @Validated @RequestBody CustomerDTO customerDTO) {
        return customerService.updateCustomer(customerId, customerDTO)
            .map(customerDTO1 -> {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.add("Location", "http://localhost:8080/" + CUSTOMER_PATH + "/" + customerDTO1.getId());
                return ResponseEntity.ok().headers(httpHeaders).body(customerDTO1);
            });
    }

    @PatchMapping(value = CUSTOMER_PATH_ID, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Mono<ResponseEntity<CustomerDTO>> patchCustomer(@PathVariable("customerId") Integer customerId, @RequestBody CustomerDTO customerDTO) {
        return customerService.patchCustomer(customerId, customerDTO)
            .map(customerDTO1 -> {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.add("Location", "http://localhost:8080/" + CUSTOMER_PATH + "/" + customerDTO1.getId());
                return ResponseEntity.ok().headers(httpHeaders).body(customerDTO1);
            });
    }

    @DeleteMapping(CUSTOMER_PATH_ID)
    Mono<ResponseEntity<Void>> deleteById(@PathVariable("customerId") Integer customerId) {
        return customerService.deleteById(customerId).then(Mono.fromCallable(() -> ResponseEntity.noContent().build()));
    }
}
