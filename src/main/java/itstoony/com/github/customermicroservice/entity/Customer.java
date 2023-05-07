package itstoony.com.github.customermicroservice.entity;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    private Long id;
    private String name;
    private String cpf;
    private Users users;
    private String address;
    private String zipcode;
    private String cellPhone;
    private LocalDate creationDate;
    private LocalDate lastModified;

}
