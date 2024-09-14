package com.example.demo.dao;

import com.example.demo.entities.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentDAOImpl implements StudentDAO {

    private EntityManager em;

    @Autowired
    public StudentDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public void save(Student theStudent) {
        em.persist(theStudent);
    }

    @Override
    public Student findById(Long id) {
        return em.find(Student.class, id);
    }

    @Override
    public List<Student> findAll() {

        TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s ORDER BY lastName", Student.class);

        return query.getResultList();
    }

    @Override
    public List<Student> findByLastName(String lastName) {

        TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s WHERE LOWER(s.lastName) LIKE LOWER(:lastName)", Student.class);

        query.setParameter("lastName", lastName);

        return query.getResultList();


    }

    @Override
    @Transactional
    public void update(Student student) {

        em.merge(student);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        Student student = em.find(Student.class, id);

        em.remove(student);

    }
}
