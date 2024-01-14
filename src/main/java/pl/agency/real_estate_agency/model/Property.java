package pl.agency.real_estate_agency.model;

import jakarta.persistence.*;
import lombok.Data;
import pl.agency.real_estate_agency.enums.Rooms;

import java.util.List;

@Entity
@Data
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int floor;
    private int price;
    private boolean basement;
    private String image;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @ElementCollection(targetClass = Rooms.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "property_rooms", joinColumns = @JoinColumn(name = "property_id"))
    private List<Rooms> rooms;
}