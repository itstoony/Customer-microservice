package itstoony.com.github.customermicroservice.service;

import itstoony.com.github.customermicroservice.dto.RegisteringCustomerDTO;
import itstoony.com.github.customermicroservice.entity.Customer;
import itstoony.com.github.customermicroservice.entity.Users;
import itstoony.com.github.customermicroservice.exception.BusinessException;
import itstoony.com.github.customermicroservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UsersService usersService;

    public Customer register(RegisteringCustomerDTO dto) {
        if (existsByCpf(dto.cpf())) {
            throw new BusinessException("Passed CPF already exists in Database");
        }

        Customer customer = getByDTO(dto);
        customerRepository.save(customer);

        return customer;
    }

    public Customer getByDTO(RegisteringCustomerDTO dto) {
        Users users = usersService.register(dto.userDTO());

        return Customer.builder()
                .id(null)
                .name(dto.name())
                .cpf(dto.cpf())
                .users(users)
                .address(dto.address())
                .zipcode(dto.zipcode())
                .cellPhone(dto.cellPhone())
                .creationDate(LocalDate.now())
                .lastModified(LocalDate.now())
                .build();
    }

    private boolean existsByCpf(String cpf) {
        return customerRepository.existsByCpf(cpf);
    }

}
