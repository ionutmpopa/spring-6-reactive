package guru.springframework.spring6reactive.services;

import guru.springframework.spring6reactive.mapper.CustomerMapper;
import guru.springframework.spring6reactive.model.CustomerDTO;
import guru.springframework.spring6reactive.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public Flux<CustomerDTO> listCustomers() {
        return customerRepository.findAll().map(customerMapper::customerToCustomerDto);
    }

    @Override
    public Mono<CustomerDTO> getCustomerById(Integer customerId) {
        return customerRepository.findById(customerId).map(customerMapper::customerToCustomerDto);
    }

    @Override
    public Mono<CustomerDTO> createNewCustomer(CustomerDTO customerDTO) {
        return customerRepository.save(customerMapper.customerDtoToCustomer(customerDTO))
            .map(customerMapper::customerToCustomerDto);
    }

    @Override
    public Mono<CustomerDTO> updateCustomer(Integer customerId, CustomerDTO customerDTO) {
        return customerRepository.findById(customerId)
            .map(customerMapper::customerToCustomerDto)
            .flatMap(customerDTO1 -> {
                customerDTO1.setCustomerName(customerDTO.getCustomerName());
                customerDTO1.setEmail(customerDTO.getEmail());
                return customerRepository.save(customerMapper.customerDtoToCustomer(customerDTO1))
                    .map(customerMapper::customerToCustomerDto);
            });
    }

    @Override
    public Mono<CustomerDTO> patchCustomer(Integer customerId, CustomerDTO customerDTO) {
        return customerRepository.findById(customerId)
            .map(customerMapper::customerToCustomerDto)
            .flatMap(customerDTO1 -> {
                if (StringUtils.hasText(customerDTO.getCustomerName())) {
                    customerDTO1.setCustomerName(customerDTO.getCustomerName());
                }
                if (StringUtils.hasText(customerDTO.getEmail())) {
                    customerDTO1.setEmail(customerDTO.getEmail());
                }
                return customerRepository.save(customerMapper.customerDtoToCustomer(customerDTO1))
                    .map(customerMapper::customerToCustomerDto);
            });
    }

    @Override
    public Mono<Void> deleteById(Integer customerId) {
        return customerRepository.deleteById(customerId);
    }
}
