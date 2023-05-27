package guru.springframework.spring6reactive.services;

import guru.springframework.spring6reactive.mapper.BeerMapper;
import guru.springframework.spring6reactive.model.BeerDTO;
import guru.springframework.spring6reactive.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public Flux<BeerDTO> listBeers() {
        return beerRepository.findAll().map(beerMapper::beerToBeerDto);
    }

    @Override
    public Mono<BeerDTO> getBeerById(Integer beerId) {
        return beerRepository.findById(beerId).map(beerMapper::beerToBeerDto);
    }

    @Override
    public Mono<BeerDTO> createNewBeer(BeerDTO beerDTO) {
        return beerRepository.save(beerMapper.beerDtoToBeer(beerDTO)).map(beerMapper::beerToBeerDto);
    }

    @Override
    public Mono<BeerDTO> updateBeer(Integer beerId, BeerDTO beerDTO) {

        return beerRepository.findById(beerId)
            .map(beerMapper::beerToBeerDto)
            .flatMap(beerDTO1 -> {
                beerDTO1.setBeerName(beerDTO.getBeerName());
                beerDTO1.setBeerStyle(beerDTO.getBeerStyle());
                beerDTO1.setUpc(beerDTO.getUpc());
                beerDTO1.setQuantity(beerDTO.getQuantity());
                beerDTO1.setPrice(beerDTO.getPrice());
                return beerRepository.save(beerMapper.beerDtoToBeer(beerDTO1)).map(beerMapper::beerToBeerDto);
            });
    }

    @Override
    public Mono<BeerDTO> patchBeer(Integer beerId, BeerDTO beerDTO) {

        return beerRepository.findById(beerId)
            .map(beerMapper::beerToBeerDto)
            .flatMap(beerDTO1 -> {
                if (StringUtils.hasText(beerDTO.getBeerName())) {
                    beerDTO1.setBeerName(beerDTO.getBeerName());
                }
                if (StringUtils.hasText(beerDTO.getBeerStyle())) {
                    beerDTO1.setBeerStyle(beerDTO.getBeerStyle());
                }
                if (StringUtils.hasText(beerDTO.getUpc())) {
                    beerDTO1.setUpc(beerDTO.getUpc());
                }
                if (beerDTO.getQuantity() != null) {
                    beerDTO1.setQuantity(beerDTO.getQuantity());
                }
                if (beerDTO.getPrice() != null) {
                    beerDTO1.setPrice(beerDTO.getPrice());
                }
                return beerRepository.save(beerMapper.beerDtoToBeer(beerDTO1)).map(beerMapper::beerToBeerDto);
            });
    }

    @Override
    public Mono<Void> deleteById(Integer beerId) {
        return beerRepository.deleteById(beerId);
    }
}
