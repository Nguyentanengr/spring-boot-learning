package dto;

import entities.Enrollment;
import entities.Student;

public record EnrolledStudent (
        Student student,
        Enrollment enrollment
){
}
