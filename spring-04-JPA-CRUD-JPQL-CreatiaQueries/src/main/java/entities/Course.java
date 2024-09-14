package entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Course {

    @Id
    private long id;

    private String title;

    @OneToMany(mappedBy = "course")
    private List<Enrollment> enrollments;

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
