package itstoony.com.github.customermicroservice.repository;

import itstoony.com.github.customermicroservice.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {

    boolean existsByEmail(String email);
}
