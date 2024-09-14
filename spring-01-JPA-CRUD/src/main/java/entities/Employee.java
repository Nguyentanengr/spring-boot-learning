package entities;

import entities.generators.UUIDGenerator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employee")
@Setter
public class Employee {

    @Id
    @GenericGenerator(name = "UUIDGenerator", type = UUIDGenerator.class)
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "employee_id", length = 500)
    private String employeeId;

    @Column(name = "employee_name")
    private String employeeName;

}
