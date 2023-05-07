package itstoony.com.github.customermicroservice.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDTO {

    private Long id;
    private String name;
    private String cpf;
    private UsersDTO users;
    private String address;
    private String zipcode;
    private String cellPhone;
    private LocalDate creationDate;
    private LocalDate lastModified;

}
