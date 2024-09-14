package entities;


import jakarta.persistence.Entity;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
public class Customer extends Person {

    private String address;
}
