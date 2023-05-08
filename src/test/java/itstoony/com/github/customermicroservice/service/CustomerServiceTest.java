package itstoony.com.github.customermicroservice.service;

import itstoony.com.github.customermicroservice.dto.RegisteringCustomerDTO;
import itstoony.com.github.customermicroservice.entity.Customer;
import itstoony.com.github.customermicroservice.exception.BusinessException;
import itstoony.com.github.customermicroservice.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static itstoony.com.github.customermicroservice.utils.Utils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class CustomerServiceTest {


    CustomerService customerService;

    @MockBean
    CustomerRepository customerRepository;

    @MockBean
    UsersService usersService;

    @BeforeEach
    void setUp() {
        this.customerService = new CustomerService(customerRepository, usersService);
    }

    @Test
    @DisplayName("Should save a customer using DTO")
    void registerTest() {
        // scenery
        RegisteringCustomerDTO dto = createRegisteringCustomerDTO();

        when(customerRepository.existsByCpf(any(String.class))).thenReturn(false);
        given(usersService.register(dto.userDTO())).willReturn(createUsers());

        // execution
        Customer savedCustomer = customerService.register(dto);

        // validation
        assertThat(savedCustomer.getName()).isEqualTo(dto.name());
        assertThat(savedCustomer.getCpf()).isEqualTo(dto.cpf());
        assertThat(savedCustomer.getUsers().getEmail()).isEqualTo(dto.userDTO().email());
        assertThat(savedCustomer.getUsers().getPassword()).isEqualTo(dto.userDTO().password());
        assertThat(savedCustomer.getUsers().getCreationDate()).isNotNull();
        assertThat(savedCustomer.getUsers().getLastModified()).isNotNull();
        assertThat(savedCustomer.getAddress()).isEqualTo(dto.address());
        assertThat(savedCustomer.getZipcode()).isEqualTo(dto.zipcode());
        assertThat(savedCustomer.getCellPhone()).isEqualTo(dto.cellPhone());
        assertThat(savedCustomer.getCreationDate()).isNotNull();
        assertThat(savedCustomer.getLastModified()).isNotNull();

        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    @DisplayName("Should throw BusinessException when passed CPF already exists in database")
    void registerAlreadySavedCpfTest() {
        // scenery
        String message = "Passed CPF already exists in Database";
        RegisteringCustomerDTO dto = createRegisteringCustomerDTO();

        when(customerRepository.existsByCpf(any(String.class))).thenReturn(true);

        // execution
        Throwable exception = catchThrowable(() -> customerService.register(dto));

        // validation
        assertThat(exception)
                .isInstanceOf(BusinessException.class)
                .hasMessage(message);

        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    @DisplayName("Should find a Customer by ID")
    void findByIDTest() {
        // scenery
        long id = 1L;
        Customer customer = createCustomer();

        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

        // execution
        Optional<Customer> found = customerService.findById(id);

        // validation
        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(id);

    }

    @Test
    @DisplayName("Should return an empty Optional when trying to find Customer by invalid ID")
    void findByInvalidIDTest(){
        // scenery
        long id = 1L;

        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        // execution
        Optional<Customer> found = customerService.findById(id);

        // validation
        assertThat(found).isEmpty();

    }


    @Test
    @DisplayName("Should find a Customer by email")
    void findByEmailTest() {
        // scenery
        String email = "Fulano@email.com";

        Customer customer = createCustomer();
        customer.getUsers().setEmail(email);

        when(customerRepository.findByUsersEmail(email)).thenReturn(Optional.of(customer));

        // execution
        Optional<Customer> found = customerService.findByEmail(email);

        // validation
        assertThat(found).isPresent();
        assertThat(found.get().getUsers().getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("Should return empty when trying to find Customer by an invalid Email")
    void findByInvalidEmailTest() {
        // scenery
        String email = "Fulano@email.com";

        when(customerRepository.findByUsersEmail(email)).thenReturn(Optional.empty());

        // execution
        Optional<Customer> found = customerService.findByEmail(email);

        // validation
        assertThat(found).isEmpty();

    }

}
