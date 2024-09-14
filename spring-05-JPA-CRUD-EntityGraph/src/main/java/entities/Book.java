package entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    @ManyToMany(mappedBy = "booksList")
    private Set<Author> authorsList;

    @ManyToMany(mappedBy = "booksList")
    private Set<BookShop> bookShopsList;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && Objects.equals(title, book.title) && Objects.equals(authorsList, book.authorsList) && Objects.equals(bookShopsList, book.bookShopsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
