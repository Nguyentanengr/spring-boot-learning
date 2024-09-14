package entities.keys;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProductKey implements Serializable {

    private String productCode;
    private int productNumber;
}
