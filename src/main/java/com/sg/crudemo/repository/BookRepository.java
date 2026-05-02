package com.sg.crudemo.repository;

import com.sg.crudemo.entity.Book;
import com.sg.crudemo.model.BookAuthorJoinRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(
            """
                    SELECT new com.sg.crudemo.model.BookAuthorJoinRow(
                        b.id, b.title, b.publicationYear, b.genre, b.pages, a.id, a.name, a.nationality
                    )
                    FROM Book b
                    INNER JOIN b.author a
                    ORDER BY b.id
                    """
    )
    List<BookAuthorJoinRow> findBooksWithAuthorsInnerJoined();
}
