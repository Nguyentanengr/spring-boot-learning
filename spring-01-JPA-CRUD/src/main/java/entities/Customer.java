package entities;

import entities.keys.CustomerKey;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
public class Customer {

    @EmbeddedId
    private CustomerKey customerId;

    @Column(name = "customer_country")
    private String customerCountry;
}