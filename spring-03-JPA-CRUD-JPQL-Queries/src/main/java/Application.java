import dto.CountedEnrollmentForStudent;
import dto.EnrolledStudent;
import entities.*;
import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.w3c.dom.ls.LSOutput;
import persistance.CustomPersistenceUnitInfo;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
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

//            1. Join

//            String jpql = """
//                    SELECT s, e FROM Student s INNER JOIN s.enrollments e
//                    """;

//
//            String jpql = """
//                    SELECT s, e FROM Student s, Enrollment e WHERE s.id = e.student.id
//                    """;

//            String jpql = """
//                    SELECT s, e FROM Student s, Enrollment e WHERE s = e.student
//                    """;

//            String jpql = """
//                    SELECT NEW dto.EnrolledStudent(s, e) FROM Student s, Enrollment e WHERE s = e.student
//                    """;

//            2. select long nhau

//            String jpql = """
//                    SELECT s FROM Student s WHERE (SELECT count(e) FROM Enrollment e WHERE e.student.id = s.id) > 1
//                    """;
            String jpql = """     
                    SELECT NEW dto.CountedEnrollmentForStudent(s, (SELECT count(e) FROM Enrollment e WHERE e.student.id = s.id)) FROM Student s
                    """;

            TypedQuery<CountedEnrollmentForStudent> q = em.createQuery(jpql, CountedEnrollmentForStudent.class);

            q.getResultList().forEach(c -> System.out.println(c.student() + " " + c.count()));

//          TypedQuery<EnrolledStudent> q = em.createQuery(jpql, EnrolledStudent.class);
//
//            q.getResultList().forEach(objects -> System.out.println(objects.student() + " " + objects.enrollment()));

//            TypedQuery<Object[]> q = em.createQuery(jpql, Object[].class);
//
//            q.getResultList().forEach(objects -> System.out.println(objects[0] + " " + objects[1]));


            em.getTransaction().commit();

        } finally {
            em.close();
            emf.close();
        }

    }
}

/*
    1. Mapped super class

    2. Single Table with Discriminator Type (default strategy inheritance)

    3. Joined table (lower performance when retrieving entities)

    4. Table per class


    1. Cac cach dung truy van join, left join, right join, select long nhau

    2. Eager fetching tao cau truy van du thua

    3. su dung record (dto) de tra ve gia tri select

 */
