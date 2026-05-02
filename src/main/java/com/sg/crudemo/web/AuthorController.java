package com.sg.crudemo.web;

import com.sg.crudemo.model.AuthorPayload;
import com.sg.crudemo.service.AuthorService;
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
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authors;

    public AuthorController(AuthorService authors) {
        this.authors = authors;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("authors", authors.listAuthors());
        return "authors-list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("author", new AuthorPayload());
        model.addAttribute("formMode", "create");
        return "author-form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("author") AuthorPayload author,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("formMode", "create");
            return "author-form";
        }
        authors.registerAuthor(author);
        return "redirect:/authors";
    }

    @GetMapping("/edit")
    public String editForm(@RequestParam long id, Model model) {
        Optional<AuthorPayload> found = authors.findAuthor(id);
        if (found.isEmpty()) {
            return "redirect:/authors";
        }
        model.addAttribute("author", found.get());
        model.addAttribute("formMode", "edit");
        return "author-form";
    }

    @PostMapping("/edit")
    public String update(@Valid @ModelAttribute("author") AuthorPayload author,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("formMode", "edit");
            return "author-form";
        }
        authors.saveAuthorChanges(author);
        return "redirect:/authors";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam long id) {
        authors.deleteAuthor(id);
        return "redirect:/authors";
    }
}
