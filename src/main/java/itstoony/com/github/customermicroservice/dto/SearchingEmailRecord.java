package itstoony.com.github.customermicroservice.dto;

import jakarta.validation.constraints.Email;

public record SearchingEmailRecord(
        @Email
        String email
) {
}
