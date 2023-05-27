package guru.springframework.spring6reactive.mapper;

import guru.springframework.spring6reactive.domain.Beer;
import guru.springframework.spring6reactive.model.BeerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BeerMapper {

    @Mapping(source = "quantity", target = "quantityOnHand")
    Beer beerDtoToBeer(BeerDTO beerDTO);

    @Mapping(source = "quantityOnHand", target = "quantity")
    BeerDTO beerToBeerDto(Beer beer);

}
