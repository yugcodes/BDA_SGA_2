package com.sg.crudemo.service;

import com.sg.crudemo.entity.Author;
import com.sg.crudemo.entity.Book;
import com.sg.crudemo.model.BookAuthorJoinRow;
import com.sg.crudemo.model.BookPayload;
import com.sg.crudemo.repository.AuthorRepository;
import com.sg.crudemo.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository books;

    @Mock
    private AuthorRepository authors;

    @InjectMocks
    private BookService service;

    @Test
    void joinedListingMirrorsRepositoryInnerJoinProjection() {
        BookAuthorJoinRow row =
                new BookAuthorJoinRow(1L, "Case", 2000, "Fiction", 300, 2L, "Voice", "Land");
        when(books.findBooksWithAuthorsInnerJoined()).thenReturn(List.of(row));

        Assertions.assertEquals(List.of(row), service.listBooksJoinedWithAuthors());
    }

    @Test
    void registerBookAssignsAuthorReferenceBeforePersistence() {
        BookPayload payload = new BookPayload();
        payload.setTitle("Nova");
        payload.setAuthorId(8L);
        Author voice = new Author();
        voice.setId(8L);
        when(authors.findById(8L)).thenReturn(Optional.of(voice));
        when(books.save(any(Book.class))).thenAnswer(invocation -> {
            Book argument = invocation.getArgument(0);
            argument.setId(90L);
            return argument;
        });

        Assertions.assertEquals(90L, service.registerBook(payload));

        ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
        verify(books).save(captor.capture());
        Assertions.assertSame(voice, captor.getValue().getAuthor());
        Assertions.assertEquals("Nova", captor.getValue().getTitle());
    }

    @Test
    void registerBookWithoutAuthorPropagatesSemanticEntityFailure() {
        BookPayload payload = new BookPayload();
        payload.setTitle("Nova");
        payload.setAuthorId(999L);
        when(authors.findById(999L)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.registerBook(payload));
    }

    @Test
    void saveBookChangesRewiresAssociationAndFields() {
        BookPayload payload = new BookPayload();
        payload.setId(4L);
        payload.setAuthorId(2L);
        payload.setTitle("Changed");
        payload.setGenre("Essay");
        Author link = new Author();
        link.setId(2L);
        Book managed = new Book();
        managed.setId(4L);
        managed.setTitle("Old");
        managed.setAuthor(link);
        when(books.findById(4L)).thenReturn(Optional.of(managed));
        when(authors.findById(2L)).thenReturn(Optional.of(link));

        service.saveBookChanges(payload);

        verify(books).save(managed);
        Assertions.assertEquals("Changed", managed.getTitle());
        Assertions.assertEquals("Essay", managed.getGenre());
        Assertions.assertSame(link, managed.getAuthor());
    }

    @Test
    void saveBookChangesMissingEditionPropagatesSemanticEntityFailure() {
        BookPayload payload = new BookPayload();
        payload.setId(55L);
        payload.setAuthorId(1L);
        when(books.findById(55L)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.saveBookChanges(payload));
    }

    @Test
    void deleteBookPurgesEditionWhenAnchored() {
        when(books.existsById(5L)).thenReturn(true);

        service.deleteBook(5L);

        verify(books).deleteById(5L);
    }

    @Test
    void deleteBookMissingPropagatesSemanticEntityFailure() {
        when(books.existsById(5L)).thenReturn(false);

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.deleteBook(5L));
        verify(books, never()).deleteById(anyLong());
    }
}
