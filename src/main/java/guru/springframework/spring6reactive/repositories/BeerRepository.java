package guru.springframework.spring6reactive.repositories;

import guru.springframework.spring6reactive.domain.Beer;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface BeerRepository extends R2dbcRepository<Beer, Integer> {

}
