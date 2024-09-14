package entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

// Notice that this class no longer has an @Entity annotation
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@SuperBuilder
public class Person {

    @Id
    private long id;

    private String name;
}
