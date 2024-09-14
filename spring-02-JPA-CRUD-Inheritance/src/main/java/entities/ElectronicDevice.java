package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@Data
@Entity
public class ElectronicDevice extends Product {

    private int voltage;

}
