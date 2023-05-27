package guru.springframework.spring6reactive.controllers;

import guru.springframework.spring6reactive.model.BeerDTO;
import guru.springframework.spring6reactive.services.BeerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static guru.springframework.spring6reactive.controllers.BeerController.BEER_PATH;

@RequiredArgsConstructor
@RequestMapping(BEER_PATH)
@RestController
public class BeerController {

    public static final String BEER_PATH = "/api/v2/beer";
    public static final String BEER_PATH_ID = "/{beerId}";
    public static final String URL = "http://localhost:8080/";
    public static final String LOCATION = "Location";

    private final BeerService beerService;

    @GetMapping
    Flux<BeerDTO> listBeers() {
        return beerService.listBeers();
    }

    @GetMapping(value = BEER_PATH_ID)
    Mono<BeerDTO> getBeerById(@PathVariable("beerId") Integer beerId) {
        return beerService.getBeerById(beerId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Mono<ResponseEntity<BeerDTO>> createNewBeer(@Validated @RequestBody BeerDTO beerDTO) {
        return beerService.createNewBeer(beerDTO)
            .map(beerDTO1 -> {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.add(LOCATION, URL + BEER_PATH + "/" + beerDTO1.getId());
                return ResponseEntity.ok().headers(httpHeaders).body(beerDTO1);
            });
    }

    @PutMapping(value = BEER_PATH_ID, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Mono<ResponseEntity<BeerDTO>> updateBeer(@PathVariable("beerId") Integer beerId, @Validated @RequestBody BeerDTO beerDTO) {
        return beerService.updateBeer(beerId, beerDTO)
            .map(beerDTO1 -> {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.add(LOCATION, URL + BEER_PATH + "/" + beerDTO1.getId());
                return ResponseEntity.ok().headers(httpHeaders).body(beerDTO1);
            });
    }

    @PatchMapping(value = BEER_PATH_ID, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Mono<ResponseEntity<BeerDTO>> patchBeer(@PathVariable("beerId") Integer beerId, @RequestBody BeerDTO beerDTO) {
        return beerService.patchBeer(beerId, beerDTO)
            .map(beerDTO1 -> {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.add(LOCATION, URL + BEER_PATH + "/" + beerDTO1.getId());
                return ResponseEntity.ok().headers(httpHeaders).body(beerDTO1);
            });
    }

    @DeleteMapping(BEER_PATH_ID)
    Mono<ResponseEntity<Void>> deleteById(@PathVariable("beerId") Integer beerId) {
        return beerService.deleteById(beerId).then(Mono.fromCallable(() -> ResponseEntity.noContent().build()));
    }
}
