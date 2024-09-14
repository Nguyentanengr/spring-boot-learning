package entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
public class Student {

    @Id
    private long id;

    private String name;

    @OneToMany(mappedBy = "student")
    private List<Enrollment> enrollments;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", enrollments=" + enrollments.size() +
                '}';
    }
}
