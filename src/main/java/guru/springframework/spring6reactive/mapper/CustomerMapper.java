package guru.springframework.spring6reactive.mapper;

import guru.springframework.spring6reactive.model.CustomerDTO;
import guru.springframework.spring6reactive.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CustomerMapper {

    @Mapping(source = "updatedDate", target = "lastModifiedDate")
    Customer customerDtoToCustomer(CustomerDTO customerDTO);

    @Mapping(source = "lastModifiedDate", target = "updatedDate")
    CustomerDTO customerToCustomerDto(Customer customer);

}
