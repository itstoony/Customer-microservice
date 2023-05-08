package itstoony.com.github.customermicroservice.repository;

import itstoony.com.github.customermicroservice.entity.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static itstoony.com.github.customermicroservice.utils.Utils.createCustomer;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class CustomerRepositoryTest {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    CustomerRepository customerRepository;


    @Test
    @DisplayName("Should find a customer by users email")
    void findByUsersEmailTest() {
        // scenery
        String email = "Fulano@email.com";

        Customer customer = createCustomer();
        customer.getUsers().setEmail(email);

        usersRepository.save(customer.getUsers());
        customerRepository.save(customer);

        // execution
        Optional<Customer> found = customerRepository.findByUsersEmail(email);

        // validation
        assertThat(found).isPresent();
        assertThat(found.get().getUsers().getEmail()).isEqualTo(email);

    }

    @Test
    @DisplayName("Should return an empty Optional when trying to Customer by invalid email")
    void findByUsersInvalidEmailTest() {
        // scenery
        String email = "Fulano@email.com";

        // execution
        Optional<Customer> found = customerRepository.findByUsersEmail(email);

        // validation
        assertThat(found).isNotPresent();

    }
}
