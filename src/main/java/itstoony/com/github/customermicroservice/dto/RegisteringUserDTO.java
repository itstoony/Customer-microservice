package itstoony.com.github.customermicroservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record RegisteringUserDTO(
        @Email(message = "Must be in Email pattern. Ex.: email@email.com")
        @NotEmpty(message = "Email must not be empty")
        String email,
        @NotEmpty(message = "Password must not be empty")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
                message = "The password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character.")
        String password
) {
}
