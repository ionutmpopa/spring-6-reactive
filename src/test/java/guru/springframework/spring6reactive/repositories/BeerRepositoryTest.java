package guru.springframework.spring6reactive.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6reactive.config.DatabaseConfig;
import guru.springframework.spring6reactive.domain.Beer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;

@DataR2dbcTest
@Import(DatabaseConfig.class)
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void createJson() throws JsonProcessingException {
        System.out.println(objectMapper.writeValueAsString(getBeer()));
    }

    @Test
    void saveNewBeer() {
        beerRepository.save(getBeer()).subscribe(beer -> System.out.println(beer.toString()));
    }

    Beer getBeer() {
        return Beer.builder()
            .beerName("Ursus")
            .beerStyle("Pilsner")
            .price(BigDecimal.TEN)
            .quantityOnHand(123)
            .upc("21321245")
            .build();
    }
}
