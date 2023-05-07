package itstoony.com.github.customermicroservice.repository;

import itstoony.com.github.customermicroservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsByCpf(String cpf);

}
