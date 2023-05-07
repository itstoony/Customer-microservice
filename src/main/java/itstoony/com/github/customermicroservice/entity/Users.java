package itstoony.com.github.customermicroservice.entity;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {

    private Long id;
    private String email;
    private String password;
    private LocalDate creationDate;
    private LocalDate lastModified;

}
