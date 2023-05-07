package itstoony.com.github.customermicroservice.service;

import itstoony.com.github.customermicroservice.dto.RegisteringUserDTO;
import itstoony.com.github.customermicroservice.entity.Users;
import itstoony.com.github.customermicroservice.exception.BusinessException;
import itstoony.com.github.customermicroservice.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;

    public Users register(RegisteringUserDTO dto) {
        if (existsByEmail(dto.email())) {
            throw new BusinessException("Passed email already exists in database");
        }

        Users users = getByDTO(dto);
        usersRepository.save(users);

        return users;
    }

    private Users getByDTO(RegisteringUserDTO dto) {
        return Users.builder()
                .id(null)
                .email(dto.email())
                .password(dto.password())
                .creationDate(LocalDate.now())
                .lastModified(LocalDate.now())
                .build();
    }

    private boolean existsByEmail(String email) {
        return usersRepository.existsByEmail(email);
    }

}
