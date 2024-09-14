package entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Passport {

    @Id
    @Column(name = "passport_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "passport_number")
    private Long number;

    @OneToOne(mappedBy = "passport")
    private Person person;

    @Override
    public String toString() {
        return "Passport{" +
                "id=" + id +
                ", number=" + number +
                ", personId=" + (person != null ? person.getId() : null) +
                '}';
    }
}
