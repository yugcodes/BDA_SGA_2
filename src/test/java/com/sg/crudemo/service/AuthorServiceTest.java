package com.sg.crudemo.service;

import com.sg.crudemo.entity.Author;
import com.sg.crudemo.model.AuthorPayload;
import com.sg.crudemo.repository.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository authors;

    @InjectMocks
    private AuthorService service;

    @Test
    void registerAuthorPersistsShellAndReturnsIdentifier() {
        AuthorPayload payload = new AuthorPayload();
        payload.setName("Sample");
        when(authors.save(any(Author.class))).thenAnswer(invocation -> {
            Author argument = invocation.getArgument(0);
            argument.setId(44L);
            return argument;
        });

        Assertions.assertEquals(44L, service.registerAuthor(payload));
        verify(authors).save(any(Author.class));
    }

    @Test
    void saveAuthorChangesMutatesManagedEntity() {
        AuthorPayload payload = new AuthorPayload();
        payload.setId(3L);
        payload.setName("Revised");
        payload.setBiography("Line");
        payload.setNationality("Land");
        Author managed = new Author();
        managed.setId(3L);
        managed.setName("Prior");
        when(authors.findById(3L)).thenReturn(Optional.of(managed));

        service.saveAuthorChanges(payload);

        verify(authors).save(managed);
        Assertions.assertEquals("Revised", managed.getName());
        Assertions.assertEquals("Line", managed.getBiography());
        Assertions.assertEquals("Land", managed.getNationality());
    }

    @Test
    void findAuthorMapsDatabaseShapeIntoPayload() {
        Author entity = new Author();
        entity.setId(12L);
        entity.setName("Witness");
        entity.setBiography("Short");
        entity.setNationality("Remote");
        when(authors.findById(12L)).thenReturn(Optional.of(entity));

        Optional<AuthorPayload> result = service.findAuthor(12L);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("Witness", result.get().getName());
        Assertions.assertEquals("Short", result.get().getBiography());
    }

    @Test
    void saveMissingAuthorPropagatesSemanticEntityFailure() {
        AuthorPayload payload = new AuthorPayload();
        payload.setId(404L);
        when(authors.findById(404L)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.saveAuthorChanges(payload));
    }

    @Test
    void deleteAuthorDelegatesRemovalWhenPresent() {
        when(authors.existsById(7L)).thenReturn(true);

        service.deleteAuthor(7L);

        verify(authors).deleteById(7L);
    }

    @Test
    void deleteMissingAuthorPropagatesSemanticEntityFailure() {
        when(authors.existsById(404L)).thenReturn(false);

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.deleteAuthor(404L));
        verify(authors, never()).deleteById(anyLong());
    }
}
