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
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToMany
    private Set<Book> booksList;

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return id == author.id && Objects.equals(name, author.name) && Objects.equals(booksList, author.booksList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
