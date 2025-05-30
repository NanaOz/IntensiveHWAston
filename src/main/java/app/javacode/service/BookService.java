package app.javacode.service;

import app.javacode.entity.Author;
import app.javacode.entity.Book;
import app.javacode.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;

    public BookService(BookRepository bookRepository, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Книга не найдена с id: " + id));
    }

    public Book save(Book book) {
        if (book.getAuthor() == null || book.getAuthor().getId() == 0) {
            throw new RuntimeException("Нужен автор");
        }
        Author author = authorService.findById(book.getAuthor().getId());
        book.setAuthor(author);
        return bookRepository.save(book);
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> findByAuthorId(Long authorId) {
        return bookRepository.findByAuthorId(authorId);
    }

    public List<Book> findAvailableBooks() {
        return bookRepository.findByAvailable(true);
    }

    public List<Book> searchByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }
}
