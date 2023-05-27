package guru.springframework.spring6reactive.bootstrap;

import guru.springframework.spring6reactive.domain.Beer;
import guru.springframework.spring6reactive.domain.Customer;
import guru.springframework.spring6reactive.repositories.BeerRepository;
import guru.springframework.spring6reactive.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Component
public class BootstrapData implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final BeerRepository beerRepository;

    @Override
    public void run(String... args) {
        loadBeers();
        loadCustomers();
        beerRepository.count().subscribe(count -> System.out.println("Beer is: " + count));
        customerRepository.count().subscribe(count -> System.out.println("Count is: " + count));
    }

    private void loadBeers() {
        beerRepository.count().subscribe(count -> {
            if (count == 0) {
                Beer beer1 = Beer.builder()
                    .beerName("Galaxy Cat")
                    .beerStyle("Pale Ale")
                    .upc("12356")
                    .price(new BigDecimal("12.99"))
                    .quantityOnHand(122)
                    .build();

                Beer beer2 = Beer.builder()
                    .beerName("Crank")
                    .beerStyle("Pale Ale")
                    .upc("12356222")
                    .price(new BigDecimal("11.99"))
                    .quantityOnHand(392)
                    .build();

                Beer beer3 = Beer.builder()
                    .beerName("Sunshine City")
                    .beerStyle("Ipa")
                    .upc("12356")
                    .price(new BigDecimal("13.99"))
                    .quantityOnHand(144)
                    .build();
                beerRepository.saveAll(List.of(beer1, beer2, beer3)).subscribe();
            }
        });
    }

    private void loadCustomers() {
        customerRepository.count().subscribe(count -> {
            if (count == 0) {
                Customer customer1 = Customer.builder()
                    .customerName("John Doe")
                    .email("john.doe@mail.com")
                    .build();

                Customer customer2 = Customer.builder()
                    .customerName("Jane Doe")
                    .email("janedoe@mail.com")
                    .build();

                Customer customer3 = Customer.builder()
                    .customerName("No Name")
                    .email("no_name@mail.com")
                    .build();
                customerRepository.saveAll(List.of(customer1, customer2, customer3)).subscribe();
            }
        });
    }
}
