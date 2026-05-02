package com.sg.crudemo.web;

import com.sg.crudemo.model.BookPayload;
import com.sg.crudemo.service.AuthorService;
import com.sg.crudemo.service.BookService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService books;
    private final AuthorService authors;

    public BookController(BookService books, AuthorService authors) {
        this.books = books;
        this.authors = authors;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("rows", books.listBooksJoinedWithAuthors());
        return "books-list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("book", new BookPayload());
        model.addAttribute("authors", authors.listAuthors());
        model.addAttribute("formMode", "create");
        return "book-form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("book") BookPayload book,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("authors", authors.listAuthors());
            model.addAttribute("formMode", "create");
            return "book-form";
        }
        books.registerBook(book);
        return "redirect:/books";
    }

    @GetMapping("/edit")
    public String editForm(@RequestParam long id, Model model) {
        Optional<BookPayload> found = books.findBook(id);
        if (found.isEmpty()) {
            return "redirect:/books";
        }
        model.addAttribute("book", found.get());
        model.addAttribute("authors", authors.listAuthors());
        model.addAttribute("formMode", "edit");
        return "book-form";
    }

    @PostMapping("/edit")
    public String update(@Valid @ModelAttribute("book") BookPayload book,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("authors", authors.listAuthors());
            model.addAttribute("formMode", "edit");
            return "book-form";
        }
        books.saveBookChanges(book);
        return "redirect:/books";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam long id) {
        books.deleteBook(id);
        return "redirect:/books";
    }
}
