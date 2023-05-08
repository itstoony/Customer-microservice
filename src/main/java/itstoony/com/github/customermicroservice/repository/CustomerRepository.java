package itstoony.com.github.customermicroservice.repository;

import itstoony.com.github.customermicroservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsByCpf(String cpf);

    @Query("select c from Customer c where c.users.email = :email")
    Optional<Customer> findByUsersEmail(@Param("email") String email);

}
