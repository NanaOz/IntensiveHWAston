package app.javacode.service;

import app.javacode.dao.BookDao;
import app.javacode.entity.Author;
import app.javacode.entity.Book;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTests {
    @Mock
    private SessionFactory sessionFactory;

    @Test
    void createBook_ShouldSaveBookSuccess() {
        try (MockedConstruction<BookDao> mocked = mockConstruction(BookDao.class,
                (mock, context) -> {
                    doAnswer(invocation -> {
                        Book book = invocation.getArgument(0);
                        book.setId(1);
                        return null;
                    }).when(mock).save(any(Book.class));
                })) {

            BookService service = new BookService();
            service.createBook("Test Book", 1, 2023, true);

            BookDao mockDao = mocked.constructed().get(0);
            verify(mockDao).save(any(Book.class));
        }
    }

    @Test
    void showAllBooks_ShouldPrintAllBooks() {
        Author author = new Author();
        author.setId(1);
        author.setName("Test Author");
        author.setCountry("Country");

        Book book1 = new Book();
        book1.setId(1);
        book1.setTitle("Book 1");
        book1.setYear(2020);
        book1.setAvailable(true);
        book1.setAuthor(author);

        Book book2 = new Book();
        book2.setId(2);
        book2.setTitle("Book 2");
        book2.setYear(2021);
        book2.setAvailable(false);
        book2.setAuthor(author);

        List<Book> books = List.of(book1, book2);

        try (MockedConstruction<BookDao> mocked = mockConstruction(BookDao.class,
                (mock, context) -> {
                    when(mock.findAll()).thenReturn(books);
                })) {

            BookService service = new BookService();
            service.showAllBooks();

            BookDao mockDao = mocked.constructed().get(0);
            verify(mockDao).findAll();
        }
    }

    @Test
    void showBooksByAuthor_ShouldPrintBooksForAuthor() {
        Author author = new Author();
        author.setId(1);
        author.setName("Test Author");
        author.setCountry("Country");

        Book book1 = new Book();
        book1.setId(1);
        book1.setTitle("Book 1");
        book1.setYear(2020);
        book1.setAvailable(true);
        book1.setAuthor(author);

        Book book2 = new Book();
        book2.setId(2);
        book2.setTitle("Book 2");
        book2.setYear(2021);
        book2.setAvailable(false);
        book2.setAuthor(author);

        List<Book> books = List.of(book1, book2);

        try (MockedConstruction<BookDao> mocked = mockConstruction(BookDao.class,
                (mock, context) -> {
                    when(mock.findByAuthorId(1)).thenReturn(books);
                })) {

            BookService service = new BookService();
            service.showBooksByAuthor(1);

            BookDao mockDao = mocked.constructed().get(0);
            verify(mockDao).findByAuthorId(1);
        }
    }

    @Test
    void findById_ShouldReturnBookWhenExists() {
        Author author = new Author();
        author.setName("Test Author");
        author.setCountry("Country");

        Book expected = new Book();
        expected.setId(1);
        expected.setTitle("Test Book");
        expected.setYear(2023);
        expected.setAvailable(true);
        expected.setAuthor(author);

        try (MockedConstruction<BookDao> mocked = mockConstruction(BookDao.class,
                (mock, context) -> {
                    when(mock.findById(1)).thenReturn(Optional.of(expected));
                })) {

            BookService service = new BookService();
            Optional<Book> result = service.findById(1);

            assertTrue(result.isPresent());
            assertEquals(expected, result.get());
        }
    }

    @Test
    void update_ShouldCallDaoUpdate() {
        Author author = new Author();
        author.setName("Test Author");
        author.setCountry("Country");

        Book book = new Book();
        book.setId(1);
        book.setTitle("Old Title");
        book.setYear(2020);
        book.setAvailable(true);
        book.setAuthor(author);

        try (MockedConstruction<BookDao> mocked = mockConstruction(BookDao.class)) {
            BookService service = new BookService();
            service.update(book);

            BookDao mockDao = mocked.constructed().get(0);
            verify(mockDao).update(book);
        }
    }

    @Test
    void deleteBook_ShouldCallDaoDelete() {
        try (MockedConstruction<BookDao> mocked = mockConstruction(BookDao.class)) {
            BookService service = new BookService();
            service.deleteBook(1);

            BookDao mockDao = mocked.constructed().get(0);
            verify(mockDao).delete(1);
        }
    }
}