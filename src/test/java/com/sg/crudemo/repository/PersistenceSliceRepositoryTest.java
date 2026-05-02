package com.sg.crudemo.repository;

import com.sg.crudemo.entity.Author;
import com.sg.crudemo.entity.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@EntityScan(basePackageClasses = {Author.class, Book.class})
@EnableJpaRepositories(basePackageClasses = BookRepository.class)
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.sql.init.mode=never",
        "spring.jpa.defer-datasource-initialization=false"
})
@Transactional
class PersistenceSliceRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void innerJoinQueryHydratesSyntheticCatalogRows() {
        Author muse = persistAuthorFixture("Synth Author", "X");
        persistBookFixture("Echo", muse);
        persistBookFixture("Nova", muse);

        Assertions.assertEquals(2, bookRepository.findBooksWithAuthorsInnerJoined().size());
        Assertions.assertEquals(2L, authorRepository.countTitlesLinkedViaInnerJoin(muse.getId()));
        Assertions.assertTrue(
                bookRepository.findBooksWithAuthorsInnerJoined().stream().anyMatch(r -> "Synth Author".equals(r.getAuthorName())));
    }

    @Test
    void deletingAuthorCascadeRemovesTheirBooks() {
        Author anchor = persistAuthorFixture("Cascade Muse", "Z");
        persistBookFixture("Alpha", anchor);
        persistBookFixture("Beta", anchor);
        Assertions.assertEquals(2, bookRepository.count());

        authorRepository.deleteById(anchor.getId());
        authorRepository.flush();

        Assertions.assertTrue(authorRepository.findById(anchor.getId()).isEmpty());
        Assertions.assertEquals(0, bookRepository.count());
    }

    private Author persistAuthorFixture(String name, String nation) {
        Author author = new Author();
        author.setName(name);
        author.setNationality(nation);
        return authorRepository.save(author);
    }

    private void persistBookFixture(String title, Author author) {
        Book book = new Book();
        book.setTitle(title);
        book.setPublicationYear(1999);
        book.setGenre("Speculative");
        book.setPages(200);
        book.setAuthor(author);
        bookRepository.save(book);
    }
}
