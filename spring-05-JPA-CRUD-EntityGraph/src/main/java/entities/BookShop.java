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
public class BookShop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToMany
    private Set<Book> booksList;

    @Override
    public String toString() {
        return "BookShop{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookShop bookShop = (BookShop) o;
        return id == bookShop.id && Objects.equals(name, bookShop.name) && Objects.equals(booksList, bookShop.booksList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
