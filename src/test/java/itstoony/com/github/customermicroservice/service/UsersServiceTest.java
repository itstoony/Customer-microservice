package itstoony.com.github.customermicroservice.service;

import itstoony.com.github.customermicroservice.dto.RegisteringUserDTO;
import itstoony.com.github.customermicroservice.entity.Users;
import itstoony.com.github.customermicroservice.exception.BusinessException;
import itstoony.com.github.customermicroservice.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static itstoony.com.github.customermicroservice.utils.Utils.createRegisteringUserDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class UsersServiceTest {

    UsersService usersService;

    @MockBean
    UsersRepository usersRepository;

    @BeforeEach
    void setUp() {
        this.usersService = new UsersService(usersRepository);
    }

    @Test
    @DisplayName("Should register an user by DTO")
    void registerTest() {
        // scenery
        RegisteringUserDTO dto = createRegisteringUserDTO();

        when(usersRepository.existsByEmail(anyString())).thenReturn(false);

        // execution
        Users savedUser = usersService.register(dto);

        // validation
        assertThat(savedUser.getEmail()).isEqualTo(dto.email());
        assertThat(savedUser.getPassword()).isEqualTo(dto.password());

        verify(usersRepository, times(1)).save(any(Users.class));
    }

    @Test
    @DisplayName("Should throw a BusinessException when passed email already exists in database")
    void registerAlreadySavedEmailTest() {
        // scenery
        String message = "Passed email already exists in database";
        RegisteringUserDTO dto = createRegisteringUserDTO();

        when(usersRepository.existsByEmail(anyString())).thenReturn(true);

        // execution
        Throwable exception = catchThrowable(() -> usersService.register(dto));

        // validation
        assertThat(exception)
                .hasMessage(message)
                .isInstanceOf(BusinessException.class);

        verify(usersRepository, never()).save(any(Users.class));
    }

}
