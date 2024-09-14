package com.example.demo.dao;

import com.example.demo.entities.Student;

import java.util.List;

public interface StudentDAO {

    void save(Student theStudent);

    Student findById(Long id);

    List<Student> findAll();

    List<Student> findByLastName(String lastName);

    void update(Student student);

    void delete(Long id);

}
