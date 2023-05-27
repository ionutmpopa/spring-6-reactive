package guru.springframework.spring6reactive.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6reactive.config.DatabaseConfig;
import guru.springframework.spring6reactive.domain.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;

@DataR2dbcTest
@Import(DatabaseConfig.class)
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void createJson() throws JsonProcessingException {
        System.out.println(objectMapper.writeValueAsString(getCustomer()));
    }

    @Test
    void saveNewCustomer() {
        customerRepository.save(getCustomer()).subscribe(customer -> System.out.println(customer.toString()));
    }

    Customer getCustomer() {
        return Customer.builder()
            .customerName("John Doe")
            .email("john@doe.com")
            .build();
    }
}
