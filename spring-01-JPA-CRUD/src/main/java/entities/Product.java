package entities;

import entities.keys.ProductKey;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@IdClass(ProductKey.class)
public class Product {

    @Id
    @Column(name = "product_code")
    private String productCode;

    @Id
    @Column(name = "product_number")
    private int productNumber;

    @Column(name = "product_name")
    private String productName;
}
