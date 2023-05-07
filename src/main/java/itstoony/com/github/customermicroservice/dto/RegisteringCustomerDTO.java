package itstoony.com.github.customermicroservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record RegisteringCustomerDTO(
        @NotEmpty(message = "Name must not be empty")
        String name,
        @NotEmpty(message = "CPF must not be empty")
        @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$",
                message = "CPF must be in Brazilian pattern. ex.: 123.456.789-10")
        String cpf,
        @NotNull(message = "User must not be null")
        @Valid
        RegisteringUserDTO userDTO,
        @NotEmpty(message = "Address must not be null")
        @Pattern(regexp = "^[a-zA-ZÀ-ÿ]+([\\s]+[a-zA-ZÀ-ÿ]+)*,\\s*\\d+$",
                message = "Address must be in Brazilian pattern. ex.: 'Rua Example, 123' ")
        String address,
        @NotEmpty(message = "Zipcode must not be empty")
        @Pattern(regexp = "^\\d{5}-\\d{3}$",
                message = "Zipcode must be in Brazilian pattern. ex.: 12345-678")
        String zipcode,
        @NotEmpty(message = "Phone must not be empty")
        @Pattern(regexp = "^\\d{2}\\s{0,1}9\\d{4}-\\d{4}$",
                message = "Phone must follow Brazilian pattern. Ex.: '11 91234-5678'")
         String cellPhone

) {
}
