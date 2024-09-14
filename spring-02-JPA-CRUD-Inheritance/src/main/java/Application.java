import entities.*;
import jakarta.persistence.EntityManager;
import org.hibernate.jpa.HibernatePersistenceProvider;
import jakarta.persistence.EntityManagerFactory;
import persistance.CustomPersistenceUnitInfo;

import java.util.HashMap;
import java.util.Map;


public class Application {

    public static void main(String[] args) {

        Map<String, String> props = new HashMap<>();
        props.put("hibernate.show_sql", "true");
        props.put("hibernate.hbm2ddl.auto", "create");

        EntityManagerFactory emf = new HibernatePersistenceProvider().createContainerEntityManagerFactory(new CustomPersistenceUnitInfo(), props);

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Employee employee = Employee.builder().id(1).name("John Doe").company("FPT company").build();

            Customer customer = Customer.builder().id(2).name("John Edo").address("23 Road HienThanh").build();

            em.persist(employee);
            em.persist(customer);

            em.createQuery("SELECT p FROM Person p", Person.class).getResultList().forEach(System.out::println);

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





 */
