package entities;


import jakarta.persistence.Entity;
import lombok.Data;
import lombok.Setter;

@Data
@Entity
public class Book extends Product{

    private String author;

}
