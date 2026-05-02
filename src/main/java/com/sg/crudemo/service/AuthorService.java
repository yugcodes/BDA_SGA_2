package com.sg.crudemo.service;

import com.sg.crudemo.entity.Author;
import com.sg.crudemo.model.AuthorPayload;
import com.sg.crudemo.repository.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authors;

    public AuthorService(AuthorRepository authors) {
        this.authors = authors;
    }

    @Transactional(readOnly = true)
    public List<AuthorPayload> listAuthors() {
        return authors.findAll().stream().map(this::map).toList();
    }

    @Transactional(readOnly = true)
    public Optional<AuthorPayload> findAuthor(long id) {
        return authors.findById(id).map(this::map);
    }

    @Transactional
    public long registerAuthor(AuthorPayload payload) {
        Author entity = applyNewPayload(payload);
        return authors.save(entity).getId();
    }

    @Transactional
    public void saveAuthorChanges(AuthorPayload payload) {
        Author entity = authors.findById(payload.getId())
                .orElseThrow(() -> new EntityNotFoundException("No author owns that surrogate key."));
        applyMutation(entity, payload);
        authors.save(entity);
    }

    @Transactional
    public void deleteAuthor(long id) {
        if (!authors.existsById(id)) {
            throw new EntityNotFoundException("No author owns that surrogate key.");
        }
        authors.deleteById(id);
    }

    private Author applyNewPayload(AuthorPayload payload) {
        Author author = new Author();
        applyMutation(author, payload);
        return author;
    }

    private void applyMutation(Author author, AuthorPayload payload) {
        author.setName(payload.getName());
        author.setBiography(payload.getBiography());
        author.setNationality(payload.getNationality());
    }

    private AuthorPayload map(Author author) {
        AuthorPayload payload = new AuthorPayload();
        payload.setId(author.getId());
        payload.setName(author.getName());
        payload.setBiography(author.getBiography());
        payload.setNationality(author.getNationality());
        return payload;
    }
}
