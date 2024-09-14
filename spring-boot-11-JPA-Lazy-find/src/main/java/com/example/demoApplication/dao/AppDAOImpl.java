package com.example.demoApplication.dao;

import com.example.demoApplication.entities.Course;
import com.example.demoApplication.entities.Instructor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AppDAOImpl implements AppDAO{

    private EntityManager entityManager;

    @Autowired
    public AppDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void saveInstructor(Instructor instructor) {
        entityManager.persist(instructor);
    }

    @Override
    public List<Course> findCoursesByInstructorId(Long theId) {

        TypedQuery<Course> query = entityManager.createQuery("SELECT c FROM Course c WHERE c.instructor.id = :id", Course.class);

        query.setParameter("id", theId);

        return query.getResultList();
    }

    @Override
    public Instructor findInstructorByJoinFetch(Long theId) {

        TypedQuery<Instructor> query = entityManager.createQuery("SELECT i FROM Instructor i JOIN i.courses WHERE i.id = :id", Instructor.class);

        query.setParameter("id", theId);

        return query.getSingleResult();
    }
}
