package app.javacode.controller;

import app.javacode.entity.Book;
import app.javacode.service.AuthorService;
import app.javacode.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final AuthorService authorService;

    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "books/list";
    }

    @GetMapping("/new")
    public String showBookForm(Model model) {
        Book book = new Book();
        book.setAvailable(true);
        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.findAll());
        return "books/form";
    }

    @PostMapping
    public String saveBook(@ModelAttribute Book book) {
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.findById(id));
        model.addAttribute("authors", authorService.findAll());
        return "books/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteById(id);
        return "redirect:/books";
    }

    @GetMapping("/available")
    public String listAvailableBooks(Model model) {
        model.addAttribute("books", bookService.findAvailableBooks());
        return "books/list";
    }

    @GetMapping("/search")
    public String searchBooks(@RequestParam String title, Model model) {
        model.addAttribute("books", bookService.searchByTitle(title));
        return "books/list";
    }
}
