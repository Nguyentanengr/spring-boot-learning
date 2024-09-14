package com.example.demoApplication.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "instructor_detail")
public class InstructorDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "instructor_detail_id")
    private Long id;

    @Column(name = "instructor_detail_link_profile")
    private String linkProfile;

    @OneToOne(mappedBy = "instructorDetail")
    private Instructor instructor;

    @Override
    public String toString() {
        return "InstructorDetail{" +
                "id='" + id + '\'' +
                ", linkProfile='" + linkProfile + '\'' +
                '}';
    }
}
