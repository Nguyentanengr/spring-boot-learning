package com.springdata.repositories;

import com.springdata.entities.Author;
import org.hibernate.annotations.QueryCacheLayout;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends Repository<Author, Long> {

    @Query("""
            SELECT a FROM Author a WHERE a.id = :id
            """)
    Optional<Author> findAuthorById(Long id);

    @Query("SElECT a FROM Author a")
    List<Author> findAllAuthors();
}
