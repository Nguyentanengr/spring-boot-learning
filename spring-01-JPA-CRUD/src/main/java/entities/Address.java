package entities;

import jakarta.persistence.*;

@Entity
public class Address {

//    Using a Foreign Key

//    @Id
//    @Column(name = "address_id")
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;

//    Using a Shared Primary Key

    @Id
    @Column(name = "supplier_id")
    private Long id;

    @Column(name = "address_street")
    private String street;

    @Column(name = "address_city")
    private String city;

//    Using a Foreign Key
//    @OneToOne(mappedBy = "address")
//    private Supplier supplier;

//    Using a Shared Primary Key

    @MapsId
    @OneToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;


}
