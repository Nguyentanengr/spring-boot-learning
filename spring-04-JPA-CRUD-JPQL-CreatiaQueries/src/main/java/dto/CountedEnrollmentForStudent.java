package dto;

import entities.Student;

public record CountedEnrollmentForStudent (
        Student student,
        Long count
){
}
