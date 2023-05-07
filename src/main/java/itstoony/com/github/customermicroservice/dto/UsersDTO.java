package itstoony.com.github.customermicroservice.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersDTO {

    private Long id;
    private String email;
    private LocalDate creationDate;
    private LocalDate lastModified;

}
