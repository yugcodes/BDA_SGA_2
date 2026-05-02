package com.sg.crudemo.service;

import com.sg.crudemo.entity.Author;
import com.sg.crudemo.entity.Book;
import com.sg.crudemo.model.BookAuthorJoinRow;
import com.sg.crudemo.model.BookPayload;
import com.sg.crudemo.repository.AuthorRepository;
import com.sg.crudemo.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository books;
    private final AuthorRepository authors;

    public BookService(BookRepository books, AuthorRepository authors) {
        this.books = books;
        this.authors = authors;
    }

    @Transactional(readOnly = true)
    public List<BookAuthorJoinRow> listBooksJoinedWithAuthors() {
        return books.findBooksWithAuthorsInnerJoined();
    }

    @Transactional(readOnly = true)
    public Optional<BookPayload> findBook(long id) {
        return books.findById(id).map(this::map);
    }

    @Transactional
    public long registerBook(BookPayload payload) {
        Author linked = authors.findById(payload.getAuthorId())
                .orElseThrow(() -> new EntityNotFoundException("The linked author anchor is unavailable."));
        Book draft = assembleBook(payload, linked);
        return books.save(draft).getId();
    }

    @Transactional
    public void saveBookChanges(BookPayload payload) {
        Book persisted = books.findById(payload.getId())
                .orElseThrow(() -> new EntityNotFoundException("Unable to hydrate the requested manuscript."));
        Author linked = authors.findById(payload.getAuthorId())
                .orElseThrow(() -> new EntityNotFoundException("The linked author anchor is unavailable."));
        fillFromPayload(persisted, payload);
        persisted.setAuthor(linked);
        books.save(persisted);
    }

    @Transactional
    public void deleteBook(long id) {
        if (!books.existsById(id)) {
            throw new EntityNotFoundException("Unable to hydrate the requested manuscript.");
        }
        books.deleteById(id);
    }

    private Book assembleBook(BookPayload payload, Author author) {
        Book book = new Book();
        fillFromPayload(book, payload);
        book.setAuthor(author);
        return book;
    }

    private void fillFromPayload(Book book, BookPayload payload) {
        book.setTitle(payload.getTitle());
        book.setPublicationYear(payload.getPublicationYear());
        book.setGenre(payload.getGenre());
        book.setPages(payload.getPages());
    }

    private BookPayload map(Book book) {
        BookPayload payload = new BookPayload();
        payload.setId(book.getId());
        payload.setTitle(book.getTitle());
        payload.setPublicationYear(book.getPublicationYear());
        payload.setGenre(book.getGenre());
        payload.setPages(book.getPages());
        payload.setAuthorId(book.getAuthor().getId());
        return payload;
    }
}
