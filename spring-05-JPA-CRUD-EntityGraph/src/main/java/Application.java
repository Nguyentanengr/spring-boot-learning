import entities.*;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import org.hibernate.graph.SubGraph;
import org.hibernate.jpa.HibernatePersistenceProvider;
import persistance.CustomPersistenceUnitInfo;

import java.util.*;
import java.util.stream.Collectors;


public class Application {

    public static void main(String[] args) {

        Map<String, String> props = new HashMap<>();
        props.put("hibernate.show_sql", "true");
//        props.put("hibernate.hbm2ddl.auto", "create");

        EntityManagerFactory emf = new HibernatePersistenceProvider().createContainerEntityManagerFactory(new CustomPersistenceUnitInfo(), props);

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            EntityGraph<?> graph = em.getEntityGraph("Author.EagerlyFetchBookShop");

            em.createQuery("SELECT a FROM Author a", Author.class)
                    .setHint("jakarta.persistence.loadgraph", graph)
                    .getResultList()
                    .forEach(a -> System.out.println(a.getBooksList().stream().map(Book::getBookShopsList).collect(Collectors.toList())));

            em.getTransaction().commit();
        } finally {
            em.close();
            emf.close();
        }

    }
}

/*

1. Using fetching lazy strategy


        em.getTransaction().begin();

        em.createQuery("SELECT a FROM Author a", Author.class)
                .getResultList()
                .forEach(a -> System.out.println(a.getBooksList()));

        em.getTransaction().commit();

        Mac dinh cua moi quan he ManyToMany trong class Author la LAZY fetching

        @ManyToMany
        private List<Book> booksList;

        Do do ket qua cua cau lenh select se xay ra van de n + 1 query. (Xuat hien qua nhieu lenh query)

        Hibernate: select a1_0.id,a1_0.name from Author a1_0
        Hibernate: select bl1_0.authorsList_id,bl1_1.id,bl1_1.title from Author_Book bl1_0 join Book bl1_1 on bl1_1.id=bl1_0.booksList_id where bl1_0.authorsList_id=?
        [Book{id=1, title='Spring Security in Action'}, Book{id=2, title='Spring Start Here'}, Book{id=3, title='Troubleshooting Java'}, Book{id=4, title='Spring Boot Up And Running'}, Book{id=5, title='Cloud Native Spring in Action'}, Book{id=6, title='JUnit in Action'}]
        Hibernate: select bl1_0.authorsList_id,bl1_1.id,bl1_1.title from Author_Book bl1_0 join Book bl1_1 on bl1_1.id=bl1_0.booksList_id where bl1_0.authorsList_id=?
        [Book{id=1, title='Spring Security in Action'}, Book{id=2, title='Spring Start Here'}, Book{id=3, title='Troubleshooting Java'}]
        Hibernate: select bl1_0.authorsList_id,bl1_1.id,bl1_1.title from Author_Book bl1_0 join Book bl1_1 on bl1_1.id=bl1_0.booksList_id where bl1_0.authorsList_id=?
        [Book{id=4, title='Spring Boot Up And Running'}, Book{id=5, title='Cloud Native Spring in Action'}, Book{id=6, title='JUnit in Action'}]

        Moi mot lan truy cap den booksList cua doi tuong Author duoc lay ra, fetching lazy se gui query de lay du lieu ve
        Do do xuat hien 3 cau truy van o ben duoi

2. Using fetching eager strategy

        Cung trong truong hop tren, neu thay doi strategy fetching eager nhu sau:

        @ManyToMany(fetch = FetchType.EAGER)
        private List<Book> booksList;

        Key qua cua cau lenh select nhu sau:

        Hibernate: select a1_0.id,a1_0.name from Author a1_0
        Hibernate: select bl1_0.authorsList_id,bl1_1.id,bl1_1.title from Author_Book bl1_0 join Book bl1_1 on bl1_1.id=bl1_0.booksList_id where bl1_0.authorsList_id=?
        Hibernate: select bl1_0.authorsList_id,bl1_1.id,bl1_1.title from Author_Book bl1_0 join Book bl1_1 on bl1_1.id=bl1_0.booksList_id where bl1_0.authorsList_id=?
        Hibernate: select bl1_0.authorsList_id,bl1_1.id,bl1_1.title from Author_Book bl1_0 join Book bl1_1 on bl1_1.id=bl1_0.booksList_id where bl1_0.authorsList_id=?
        [Book{id=1, title='Spring Security in Action'}, Book{id=2, title='Spring Start Here'}, Book{id=3, title='Troubleshooting Java'}, Book{id=4, title='Spring Boot Up And Running'}, Book{id=5, title='Cloud Native Spring in Action'}, Book{id=6, title='JUnit in Action'}]
        [Book{id=1, title='Spring Security in Action'}, Book{id=2, title='Spring Start Here'}, Book{id=3, title='Troubleshooting Java'}]
        [Book{id=4, title='Spring Boot Up And Running'}, Book{id=5, title='Cloud Native Spring in Action'}, Book{id=6, title='JUnit in Action'}]

        Khi thuc hien lenh select thuc the duoc truy cap,lenh select se de len lenh fetching eager va thuc thi
        fetching eager tiep tuc lay du lieu ve, do do cac lenh truy van se noi tiep nhau de lay du lieu ve.
        Tuong tu voi lazy thi cung xuat hien van de n + 1 query

        De giai quyet van de nay, nguoi ta su dung entity graph de thay doi fetching strategy, chi dinh cac attribute
        can duoc lay ve ngay tai thoi diem query runtime -> lam giam lenh query -> giam qua trinh truyen du lieu -> toi uu

3. Using Entity Graph API

        em.getTransaction().begin();

            EntityGraph<?> graph = em.createEntityGraph(Author.class);
            graph.addAttributeNodes("booksList"); // cau hinh graph

            em.createQuery("SELECT a FROM Author a", Author.class)
                    .setHint("jakarta.persistence.loadgraph", graph)
                    .getResultList()
                    .forEach(a -> System.out.println(a.getBooksList()));

        em.getTransaction().commit();

        Sau khi cau hinh graph, ket qua se nhu sau:

        Hibernate: select a1_0.id,bl1_0.authorsList_id,bl1_1.id,bl1_1.title,a1_0.name from Author a1_0 left join Author_Book bl1_0 on a1_0.id=bl1_0.authorsList_id left join Book bl1_1 on bl1_1.id=bl1_0.booksList_id
        [Book{id=1, title='Spring Security in Action'}, Book{id=2, title='Spring Start Here'}, Book{id=3, title='Troubleshooting Java'}, Book{id=4, title='Spring Boot Up And Running'}, Book{id=5, title='Cloud Native Spring in Action'}, Book{id=6, title='JUnit in Action'}]
        [Book{id=1, title='Spring Security in Action'}, Book{id=2, title='Spring Start Here'}, Book{id=3, title='Troubleshooting Java'}]
        [Book{id=4, title='Spring Boot Up And Running'}, Book{id=5, title='Cloud Native Spring in Action'}, Book{id=6, title='JUnit in Action'}]

        Tat ca cac thuc the lien quan hoac thuoc tinh deu duoc lay ve trong cung mot truy van

        Trong truong phuc tap hon, lay danh sach cua hang cua tung sach cua moi tac gia ngay trong cau lenh truy van

        em.getTransaction().begin();

            EntityGraph<?> graph = em.createEntityGraph(Author.class);
            Subgraph<?> bookSubgraph = graph.addSubgraph("booksList");
            bookSubgraph.addAttributeNodes("bookShopsList");

            em.createQuery("SELECT a FROM Author a", Author.class)
                    .setHint("jakarta.persistence.loadgraph", graph)
                    .getResultList()
                    .forEach(a -> System.out.println(a.getBooksList().stream().map(Book::getBookShopsList).collect(Collectors.toList())));

        em.getTransaction().commit();

        Ket qua duoc lay ve:

        Hibernate: select a1_0.id,bl1_0.authorsList_id,bl1_1.id,bsl1_0.booksList_id,bsl1_1.id,bsl1_1.name,bl1_1.title,a1_0.name from Author a1_0 left join Author_Book bl1_0 on a1_0.id=bl1_0.authorsList_id left join Book bl1_1 on bl1_1.id=bl1_0.booksList_id left join BookShop_Book bsl1_0 on bl1_1.id=bsl1_0.booksList_id left join BookShop bsl1_1 on bsl1_1.id=bsl1_0.bookShopsList_id
        [[BookShop{id=1, name='Manning'}, BookShop{id=3, name='Elephant'}], [BookShop{id=2, name='O'Reilly'}, BookShop{id=1, name='Manning'}], [BookShop{id=2, name='O'Reilly'}, BookShop{id=1, name='Manning'}], [BookShop{id=1, name='Manning'}, BookShop{id=3, name='Elephant'}], [BookShop{id=2, name='O'Reilly'}, BookShop{id=1, name='Manning'}], [BookShop{id=1, name='Manning'}, BookShop{id=3, name='Elephant'}]]
        [[BookShop{id=2, name='O'Reilly'}, BookShop{id=1, name='Manning'}], [BookShop{id=2, name='O'Reilly'}, BookShop{id=1, name='Manning'}], [BookShop{id=2, name='O'Reilly'}, BookShop{id=1, name='Manning'}]]
        [[BookShop{id=1, name='Manning'}, BookShop{id=3, name='Elephant'}], [BookShop{id=1, name='Manning'}, BookShop{id=3, name='Elephant'}], [BookShop{id=1, name='Manning'}, BookShop{id=3, name='Elephant'}]]

        Voi tac gia 1 co 6 sach, tuong ung voi 6 danh sach cua hang tren dong 1 cua ket qua tra ve.


5. Using Entity Graph Annotation

        Danh dau annotation tren thuc the chinh nhu sau

        @Entity
        @Data
        @NoArgsConstructor
        @NamedEntityGraph(
                name = "Author.EagerlyFetchBook",
                attributeNodes = @NamedAttributeNode("booksList")
        )
        public class Author {...}

        Lay graph thong qua getEntityGraph() va thuc thi lenh query, dung nhu sau:

        em.getTransaction().begin();

            EntityGraph<?> graph = em.getEntityGraph("Author.EagerlyFetchBook");

            em.createQuery("SELECT a FROM Author a", Author.class)
                    .setHint("jakarta.persistence.loadgraph", graph)
                    .getResultList()
                    .forEach(a -> System.out.println(a.getBooksList()));

        em.getTransaction().commit();

        Ket qua :

        Hibernate: select a1_0.id,bl1_0.authorsList_id,bl1_1.id,bl1_1.title,a1_0.name from Author a1_0 left join Author_Book bl1_0 on a1_0.id=bl1_0.authorsList_id left join Book bl1_1 on bl1_1.id=bl1_0.booksList_id
        [Book{id=6, title='JUnit in Action'}, Book{id=3, title='Troubleshooting Java'}, Book{id=1, title='Spring Security in Action'}, Book{id=4, title='Spring Boot Up And Running'}, Book{id=5, title='Cloud Native Spring in Action'}, Book{id=2, title='Spring Start Here'}]
        [Book{id=3, title='Troubleshooting Java'}, Book{id=1, title='Spring Security in Action'}, Book{id=2, title='Spring Start Here'}]
        [Book{id=6, title='JUnit in Action'}, Book{id=4, title='Spring Boot Up And Running'}, Book{id=5, title='Cloud Native Spring in Action'}]

        Viec su dung danh dau Graph Entity annotation (namedEntityGraph) tranh dinh nghia lai cac graph tuong tu trong cac cau lenh truy
        van giong nhau (nguyen tac tai su dung), tuong tu khi su dung namedQuery.


        Trong truong hop phuc tap hon, co the dinh nghia multi graph nhu sau

        @Entity
        @Data
        @NoArgsConstructor
        @NamedEntityGraphs({
                @NamedEntityGraph(
                        name = "Author.EagerlyFetchBook",
                        attributeNodes = @NamedAttributeNode("booksList")
                ),
                @NamedEntityGraph(
                        name = "Author.EagerlyFetchBookShop",
                        attributeNodes = @NamedAttributeNode(value = "booksList", subgraph = "book-subgraph"),
                        subgraphs = @NamedSubgraph(
                                name = "book-subgraph",
                                attributeNodes = @NamedAttributeNode("bookShopsList")
                        )
                )
        })
        public class Author {...}


        Lay graph thong qua getEntityGraph() va thuc thi lenh query, dung nhu sau:

        em.getTransaction().begin();

            EntityGraph<?> graph = em.getEntityGraph("Author.EagerlyFetchBookShop");

            em.createQuery("SELECT a FROM Author a", Author.class)
                    .setHint("jakarta.persistence.loadgraph", graph)
                    .getResultList()
                    .forEach(a -> System.out.println(a.getBooksList().stream().map(Book::getBookShopsList).collect(Collectors.toList())));

        em.getTransaction().commit();

        Ket qua:

        Hibernate: select a1_0.id,bl1_0.authorsList_id,bl1_1.id,bsl1_0.booksList_id,bsl1_1.id,bsl1_1.name,bl1_1.title,a1_0.name from Author a1_0 left join Author_Book bl1_0 on a1_0.id=bl1_0.authorsList_id left join Book bl1_1 on bl1_1.id=bl1_0.booksList_id left join BookShop_Book bsl1_0 on bl1_1.id=bsl1_0.booksList_id left join BookShop bsl1_1 on bsl1_1.id=bsl1_0.bookShopsList_id
        [[BookShop{id=1, name='Manning'}, BookShop{id=3, name='Elephant'}], [BookShop{id=2, name='O'Reilly'}, BookShop{id=1, name='Manning'}], [BookShop{id=2, name='O'Reilly'}, BookShop{id=1, name='Manning'}], [BookShop{id=1, name='Manning'}, BookShop{id=3, name='Elephant'}], [BookShop{id=2, name='O'Reilly'}, BookShop{id=1, name='Manning'}], [BookShop{id=1, name='Manning'}, BookShop{id=3, name='Elephant'}]]
        [[BookShop{id=2, name='O'Reilly'}, BookShop{id=1, name='Manning'}], [BookShop{id=2, name='O'Reilly'}, BookShop{id=1, name='Manning'}], [BookShop{id=2, name='O'Reilly'}, BookShop{id=1, name='Manning'}]]
        [[BookShop{id=1, name='Manning'}, BookShop{id=3, name='Elephant'}], [BookShop{id=1, name='Manning'}, BookShop{id=3, name='Elephant'}], [BookShop{id=1, name='Manning'}, BookShop{id=3, name='Elephant'}]]

        Tat ca thuc the/ thuoc tinh trong graph deu duoc lay ve ngay tai thoi diem thuc thi query.


 */