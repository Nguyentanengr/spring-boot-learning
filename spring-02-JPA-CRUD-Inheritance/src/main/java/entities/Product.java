package entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Setter;


@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Product {

    @Id
    protected int id;

}
