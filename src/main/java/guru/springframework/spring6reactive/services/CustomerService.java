package guru.springframework.spring6reactive.services;

import guru.springframework.spring6reactive.model.CustomerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {

    Flux<CustomerDTO> listCustomers();

    Mono<CustomerDTO>  getCustomerById(Integer beerId);

    Mono<CustomerDTO> createNewCustomer(CustomerDTO customerDTO);

    Mono<CustomerDTO> updateCustomer(Integer beerId, CustomerDTO customerDTO);

    Mono<CustomerDTO> patchCustomer(Integer beerId, CustomerDTO customerDTO);

    Mono<Void> deleteById(Integer beerId);
}
