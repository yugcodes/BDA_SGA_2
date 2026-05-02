package com.sg.crudemo.repository;

import com.sg.crudemo.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT COUNT(b) FROM Book b INNER JOIN b.author a WHERE a.id = :authorId")
    long countTitlesLinkedViaInnerJoin(@Param("authorId") Long authorId);
}
