package pl.agency.real_estate_agency.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //gpt mówi że thymeleaf wymaga te pola jako publiczne, ale chyba to nieprawda
    private Long id;

    private String city;
    private String street;
    private int number;

//    @OneToOne
//    private Property property;

}
