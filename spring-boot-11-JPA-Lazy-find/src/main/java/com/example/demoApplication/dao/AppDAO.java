package com.example.demoApplication.dao;

import com.example.demoApplication.entities.Course;
import com.example.demoApplication.entities.Instructor;

import java.util.List;

public interface AppDAO {

    void saveInstructor(Instructor instructor);

    List<Course> findCoursesByInstructorId(Long theId);

    Instructor findInstructorByJoinFetch(Long theId);
}
