import entities.*;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import org.hibernate.jpa.HibernatePersistenceProvider;
import persistance.CustomPersistenceUnitInfo;

import java.util.*;


public class Application {

    public static void main(String[] args) {

        Map<String, String> props = new HashMap<>();
        props.put("hibernate.show_sql", "true");
//        props.put("hibernate.hbm2ddl.auto", "create");

        EntityManagerFactory emf = new HibernatePersistenceProvider().createContainerEntityManagerFactory(new CustomPersistenceUnitInfo(), props);

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // SELECT s, (SELECT count(e) FROM Enrollment e JOIN Student s ON e.id IN s.enrollments) cnt
            // FROM Student s WHERE cnt > 1

            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<Student> criteriaQuery = builder.createQuery(Student.class);
            Root<Student> studentRoot = criteriaQuery.from(Student.class);

            Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
            Root<Student> subStudentRoot = subquery.correlate(studentRoot);
            Join<Student, Enrollment> joinEnrollment = subStudentRoot.join("enrollments");

            subquery.select(builder.count(joinEnrollment));
            criteriaQuery.select(studentRoot).where(builder.greaterThan(subquery, 1L));

            TypedQuery<Student> query = em.createQuery(criteriaQuery);
            query.getResultList().forEach(System.out::println);


            em.getTransaction().commit();

        } finally {
            em.close();
            emf.close();
        }

    }
}

/*

1. Khoi tao query goc

    em.getTransaction().begin();

        TypedQuery<Student> query = em.createQuery(criteriaQuery);

        query.getResultList().forEach(System.out::println);

    em.getTransaction().commit();


2. Khoi tao criteria query

    em.getTransaction().begin();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Student> criteriaQuery = builder.createQuery(Student.class);
        Root<Student> studentRoot = criteriaQuery.from(Student.class);

        //    criteria query chua cau lenh query
        //    root chua custom cau lenh
        criteriaQuery.select(studentRoot.get("name"));
    em.getTransaction().commit();


3. fully criteria query

    em.getTransaction().begin();

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<String> criteriaQuery = builder.createQuery(String.class);
        Root<Student> studentRoot = criteriaQuery.from(Student.class);

        criteriaQuery.select(studentRoot.get("id"))
                .where(builder.between(studentRoot.get("id"), 1, 10))
                .orderBy(builder.desc(studentRoot.get("id")));

        TypedQuery<String> query = em.createQuery(criteriaQuery);
        query.getResultList().forEach(System.out::println);

    em.getTransaction().commit();

4. join criteria query

    em.getTransaction().begin();

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> criteriaQuery = builder.createTupleQuery();
        Root<Enrollment> enrollmentRoot = criteriaQuery.from(Enrollment.class);

        Join<Student, Enrollment> joinStudent = enrollmentRoot.join("student", JoinType.INNER);

        Join<Course, Enrollment> joinCourse = enrollmentRoot.join("course", JoinType.INNER);

        criteriaQuery.multiselect(enrollmentRoot, joinStudent, joinCourse); // SELECT b, a FROM Book b INNER JOIN Author a

        TypedQuery<Tuple> query = em.createQuery(criteriaQuery);

        query.getResultList().forEach(tuple -> System.out.println(tuple.get(0) + " " + tuple.get(1) + " " + tuple.get(2)));

    em.getTransaction().commit();

5. criteria subquery


    em.getTransaction().begin();

        // SELECT s, (SELECT count(e) FROM Enrollment e JOIN Student s ON e.id IN s.enrollments) cnt
        // FROM Student s WHERE cnt > 1

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Student> criteriaQuery = builder.createQuery(Student.class);
        Root<Student> studentRoot = criteriaQuery.from(Student.class);

        Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
        Root<Student> subStudentRoot = subquery.correlate(studentRoot);
        Join<Student, Enrollment> joinEnrollment = subStudentRoot.join("enrollments");

        subquery.select(builder.count(joinEnrollment));
        criteriaQuery.select(studentRoot).where(builder.greaterThan(subquery, 1L));

        TypedQuery<Student> query = em.createQuery(criteriaQuery);
        query.getResultList().forEach(System.out::println);

    em.getTransaction().commit();









 */
