package itstoony.com.github.customermicroservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "cpf", unique = true)
    private String cpf;

    @Column(name = "address")
    private String address;

    @Column(name = "zipcode")
    private String zipcode;

    @Column(name = "cellphone")
    private String cellPhone;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "last_modified")
    private LocalDate lastModified;

    @OneToOne
    @JoinColumn(name = "users_id")
    private Users users;

}
