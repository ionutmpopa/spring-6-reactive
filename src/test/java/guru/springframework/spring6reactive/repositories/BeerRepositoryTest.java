package guru.springframework.spring6reactive.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6reactive.config.DatabaseConfig;
import guru.springframework.spring6reactive.domain.Beer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@DataR2dbcTest
@Import(DatabaseConfig.class)
public class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void createJson() throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(getBeer());
        System.out.println(json);
        Assertions.assertThat(json).isNotNull();
    }

    @Test
    void saveNewBeer() {
        Mono<Beer> beerDTOMono = beerRepository.save(getBeer()).single();
        beerDTOMono.subscribe(beer -> {
            Assertions.assertThat(beer).isNotNull();
            System.out.println(beer);
        });
    }

    public static Beer getBeer() {
        return Beer.builder()
            .beerName("Ursus")
            .beerStyle("Pilsner")
            .price(BigDecimal.TEN)
            .quantityOnHand(123)
            .upc("21321245")
            .build();
    }
}
