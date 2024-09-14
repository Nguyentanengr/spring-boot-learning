import entities.*;
import entities.keys.CourseRatingKey;
import entities.keys.CustomerKey;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.hibernate.jpa.HibernatePersistenceProvider;
import persistence.CustomPersistenceUnitInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class Application {
    public static void main(String[] args) {

        Map <String, String> props = new HashMap<>();
        props.put("hibernate.show_sql", "true");
        props.put("hibernate.hbm2ddl.auto", "create");

        EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new CustomPersistenceUnitInfo(), props);

        EntityManager em = emf.createEntityManager();

        try {

            em.getTransaction().begin();

            Student student1 = new Student();
            student1.setName("Tan Nguyen");

            Student student2 = new Student();
            student2.setName("Thi Thuong");

            Course course1 = new Course();
            course1.setName("English");

            Course course2 = new Course();
            course2.setName("Math");

            CourseRating courseRating1 = new CourseRating(new CourseRatingKey(student1.getId(), course1.getId()), student1, course1, 10);
            CourseRating courseRating2 = new CourseRating(new CourseRatingKey(student1.getId(), course2.getId()), student1, course2, 9);
            CourseRating courseRating3 = new CourseRating(new CourseRatingKey(student2.getId(), course1.getId()), student2, course1, 10);

            student1.setRatings(List.of(courseRating1, courseRating2));
            student2.setRatings(List.of(courseRating3));

            course1.setRatings(List.of(courseRating1, courseRating3));
            course2.setRatings(List.of(courseRating2));

            em.persist(student1);
            em.persist(student2);

            em.persist(course1);
            em.persist(course2);

            em.persist(courseRating1);
            em.persist(courseRating2);
            em.persist(courseRating3);

            em.getTransaction().commit();
        } finally {
            em.close();
            emf.close();
        }
    }
}

/*

1. How to config Hibernate using xml file and config CustomPersistenceUnitInfo class

2. Entity method
    em.persist()        -> Thêm/Cập nhật (nếu entity đã được quản lí bởi transaction trước khi persist) một entity vào context
                           Truy vấn insert/update sẽ được thực hiện khi transaction commit

    em.find()           -> Tìm kiếm bằng khóa chính ngay thời điểm câu lệnh. Trả về entity từ cơ sở dữ liệu và được quản lí bởi
                           transaction, mọi thay đổi trên entity sẽ được mapping và thay đổi trong cơ sở dữ liệu bằng truy vấn update.
                           khi transaction được commit

    em.remove()         -> Nếu entity được quản lí bởi transaction. Thực hiện truy vấn select để lấy entity ngay tại
                           câu lệnh remove(), Thực hiện truy vấn delete bảng ghi trong cơ sở dữ liệu khi commit.

    em.merge()          -> Đánh dấu entity Mới nếu chưa được quản lí bởi transaction. Thực hiện lệnh select ngay tại câu
                           lênh merge(). Nếu entity cũ -> thực hiện lệnh update, nếu entity mới -> Thực hiện update / insert
                           vào database.

    em.refresh()        -> Hoàn tác giá trị của entity khi commit
    em.detach()         -> gỡ bỏ sự quản lí bởi transaction / context, thay đổi không ảnh hưởng đến database.
    em.reference()      -> chỉ thực hiện truy vấn select khi sử dụng đối tượng (lazy)

3. Hibernate properties
    props.put("hibernate.show_sql", "true") : true/false, hien thi cau lenh sql len console khi chung duoc thuc thi

    props.put("hibernate.hbm2ddl.auto", "none") : Hibernate tu dong quan li viec tao va thay doi scheme (cac bang) cua csdl
        none : Khong thuc hien thay doi gi len csdl
        validate : Kiem tra schema co khop voi cac entity da duoc khai bao hay khong, khong thuc hien thay doi gi len csdl
        update : Thuc hien cap nhat schema hien tai de phu hop voi cac entity moi hoac da duoc thay doi
        create : Thuc hien xoa toan bo bang cu (neu co) va tao lai tu dau cac bang do moi khi ung dung duoc chay (reset = 0 du lieu)
        create : Thuc hien tuong tu create, kem theo xoa cac bang khi session Hibernate ket thuc

4. @Entity, @Table, @Column, @GeneratedValue, @Id.

    @Entity(name = "entity_name")
    @Table(name = "table_name")
    @Column(name = "column_name")
    @GeneratedValue(strategy = GenerationType.IDENTITY)


5. Primary Key

    - Sinh ngau nhien key bang @GeneratedValue(strategy = GeneratedValue.UUID)

    - Sinh ngau nhien key custom bang UUIDGenerator class

    - Ma hoa key voi sign() method (ma hoa bang cach tao cap public key va private key bang thuat toan "RSA",
      sign private key voi UUID (dang byte) bang thuat toan "SHA256withRSA")

    - Composite primary key bang cach su dung @IdClass, @Embeddable (implement Serialize)


6. One-to-One Relationship

    - Danh dau @OneToOne va @JoinColumn nhu sau:

    public class Person {
        @Id
        @Column(name = "person_id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column(name = "person_name")
        private String name;

        @OneToOne
        @JoinColumn(name = "passport_id")
        private Passport passport;
    }

    - Voi Passport neu thiet lap @OneToOne den Person thi kha nang Passport se sinh ra cot person_id
      Neu thiet lap mappedby, Passport khong sinh ra cot person_id nhung van dam bao lien ket voi Person

      public class Passport {

        @Id
        @Column(name = "passport_id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column(name = "passport_number")
        private Long number;

        @OneToOne(mappedBy = "passport")
        private Person person;
    }

    - Luu y khi dung toString o hai doi tuong, de dan den StackOverFlow do cac doi tuong goi toString() cua nhau


7. One-To-Many Relationship





















//            TypedQuery<Person> q = em.createQuery("SELECT p FROM Person p WHERE p.passPort.number = :number", Person.class);
//            q.setParameter("number", 100);
                TypedQuery<Person> q = em.createNamedQuery("SELECT p FROM Person p WHERE p.passport.number = :number", Person.class);
                q.setParameter("number", 300030002000L);
 */