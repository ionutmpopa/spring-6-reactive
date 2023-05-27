package guru.springframework.spring6reactive.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6reactive.config.DatabaseConfig;
import guru.springframework.spring6reactive.domain.Customer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import reactor.core.publisher.Mono;

@DataR2dbcTest
@Import(DatabaseConfig.class)
public class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void createJson() throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(getCustomer());
        System.out.println(json);
        Assertions.assertThat(json).isNotNull();
    }

    @Test
    void saveNewCustomer() {
        Mono<Customer> customerMono = customerRepository.save(getCustomer()).single();
        customerMono.subscribe(customer -> {
            Assertions.assertThat(customer).isNotNull();
            System.out.println(customer);
        });

    }

    public static Customer getCustomer() {
        return Customer.builder()
            .customerName("John Doe")
            .email("john@doe.com")
            .build();
    }
}
