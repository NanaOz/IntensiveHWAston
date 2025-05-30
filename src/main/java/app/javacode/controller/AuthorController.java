package app.javacode.controller;

import app.javacode.entity.Author;
import app.javacode.service.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public String listAuthors(Model model) {
        model.addAttribute("authors", authorService.findAll());
        return "authors/list";
    }

    @GetMapping("/new")
    public String showAuthorForm(Model model) {
        model.addAttribute("author", new Author());
        return "authors/form";
    }

    @PostMapping
    public String saveAuthor(@ModelAttribute Author author) {
        authorService.save(author);
        return "redirect:/authors";
    }

    @GetMapping("/edit/{id}")
    public String editAuthor(@PathVariable Long id, Model model) {
        model.addAttribute("author", authorService.findById(id));
        return "authors/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable Long id) {
        authorService.deleteById(id);
        return "redirect:/authors";
    }

    @GetMapping("/search")
    public String searchAuthors(@RequestParam String name, Model model) {
        model.addAttribute("authors", authorService.searchByName(name));
        return "authors/list";
    }

    @GetMapping("/{id}/books")
    public String viewAuthorBooks(@PathVariable Long id, Model model) {
        Author author = authorService.findById(id);
        model.addAttribute("author", author);
        model.addAttribute("books", author.getBooks());
        return "authors/books";
    }
}
