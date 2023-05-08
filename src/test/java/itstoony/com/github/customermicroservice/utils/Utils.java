package itstoony.com.github.customermicroservice.utils;

import itstoony.com.github.customermicroservice.dto.RegisteringCustomerDTO;
import itstoony.com.github.customermicroservice.dto.RegisteringUserDTO;
import itstoony.com.github.customermicroservice.entity.Customer;
import itstoony.com.github.customermicroservice.entity.Users;

import java.time.LocalDate;

public class Utils {

    public static RegisteringUserDTO createRegisteringUserDTO() {
        return new RegisteringUserDTO("fulano-de-tal@gmail.com", "Fulano@123456");
    }

    public static RegisteringCustomerDTO createRegisteringCustomerDTO() {
        return new RegisteringCustomerDTO(
                "Fulano de tal",
                "123.456.789-10",
                createRegisteringUserDTO(),
                "Rua Example, 123",
                "45032-000",
                "21 91234-5678"
        );
    }

    public static Users createUsers() {
        return Users.builder()
                .id(1L)
                .email("fulano-de-tal@gmail.com")
                .password("Fulano@123456")
                .creationDate(LocalDate.now())
                .lastModified(LocalDate.now())
                .customer(null)
                .build();
    }

    public static Customer createCustomer() {
        return Customer.builder()
                .id(1L)
                .name("Fulano de tal")
                .cpf("123.456.789-10")
                .users(createUsers())
                .address("Rua Example, 123")
                .zipcode("45032-000")
                .cellPhone("(21) 1234-5678")
                .creationDate(LocalDate.now())
                .lastModified(LocalDate.now())
                .build();
    }

}
