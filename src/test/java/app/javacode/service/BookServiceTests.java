package app.javacode.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;

import app.javacode.dao.BookDao;
import app.javacode.entity.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedConstruction;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class BookServiceTests {
    @Test
    void createBook_ShouldSaveBook() throws SQLException {
        try (MockedConstruction<BookDao> mocked = mockConstruction(BookDao.class,
                (mock, context) -> {
                    doAnswer(invocation -> {
                        Book book = invocation.getArgument(0);
                        book.setId(1);
                        return null;
                    }).when(mock).save(any(Book.class));
                })) {

            BookService service = new BookService(mock(Connection.class));
            service.createBook("Test Book", 1, 2023, true);

            BookDao mockDao = mocked.constructed().get(0);
            verify(mockDao).save(any(Book.class));
        }
    }

    @Test
    void showAllBooks_ShouldPrintAllBooks() throws SQLException {
        List<Book> books = Arrays.asList(
                new Book(1, "Book 1", 1, 2020, true),
                new Book(2, "Book 2", 1, 2021, false)
        );

        try (MockedConstruction<BookDao> mocked = mockConstruction(BookDao.class,
                (mock, context) -> {
                    when(mock.findAll()).thenReturn(books);
                })) {

            BookService service = new BookService(mock(Connection.class));
            service.showAllBooks();

            BookDao mockDao = mocked.constructed().get(0);
            verify(mockDao).findAll();
        }
    }

    @Test
    void showBooksByAuthor_ShouldPrintBooks() throws SQLException {
        List<Book> books = Arrays.asList(
                new Book(1, "Book 1", 1, 2020, true),
                new Book(2, "Book 2", 1, 2021, false)
        );

        try (MockedConstruction<BookDao> mocked = mockConstruction(BookDao.class,
                (mock, context) -> {
                    when(mock.findByAuthorId(1)).thenReturn(books);
                })) {

            BookService service = new BookService(mock(Connection.class));
            service.showBooksByAuthor(1);

            BookDao mockDao = mocked.constructed().get(0);
            verify(mockDao).findByAuthorId(1);
        }
    }

    @Test
    void updateBook_ShouldUpdateExistingBook() throws SQLException {
        Book existingBook = new Book(1, "Old Title", 1, 2020, true);

        try (MockedConstruction<BookDao> mocked = mockConstruction(BookDao.class,
                (mock, context) -> {
                    when(mock.findById(1)).thenReturn(existingBook);
                })) {

            BookService service = new BookService(mock(Connection.class));
            service.updateBook(1, "New Title", 2, 2022, false);

            BookDao mockDao = mocked.constructed().get(0);
            verify(mockDao).findById(1);
            verify(mockDao).update(argThat(book ->
                    "New Title".equals(book.getTitle()) &&
                            book.getAuthorId() == 2 &&
                            book.getYear() == 2022 &&
                            !book.isAvailable()
            ));
        }
    }

    @Test
    void updateBook_ShouldNotUpdateWhenBookNotFound() throws SQLException {
        try (MockedConstruction<BookDao> mocked = mockConstruction(BookDao.class,
                (mock, context) -> {
                    when(mock.findById(999)).thenReturn(null);
                })) {

            BookService service = new BookService(mock(Connection.class));
            service.updateBook(999, "New Title", 2, 2022, false);

            BookDao mockDao = mocked.constructed().get(0);
            verify(mockDao).findById(999);
            verify(mockDao, never()).update(any());
        }
    }

    @Test
    void updateBook_ShouldUpdateOnlyProvidedFields() throws SQLException {
        Book existingBook = new Book(1, "Original Title", 1, 2020, true);

        try (MockedConstruction<BookDao> mocked = mockConstruction(BookDao.class,
                (mock, context) -> {
                    when(mock.findById(1)).thenReturn(existingBook);
                })) {

            BookService service = new BookService(mock(Connection.class));
            service.updateBook(1, null, -1, 2022, null);

            BookDao mockDao = mocked.constructed().get(0);
            verify(mockDao).update(argThat(book ->
                    "Original Title".equals(book.getTitle()) &&
                            book.getAuthorId() == 1 &&
                            book.getYear() == 2022 &&
                            book.isAvailable()
            ));
        }
    }

    @Test
    void deleteBook_ShouldDeleteExistingBook() throws SQLException {
        try (MockedConstruction<BookDao> mocked = mockConstruction(BookDao.class)) {
            BookService service = new BookService(mock(Connection.class));
            service.deleteBook(1);

            BookDao mockDao = mocked.constructed().get(0);
            verify(mockDao).delete(1);
        }
    }

    @Test
    void createBook_ShouldThrowSQLException() throws SQLException {
        try (MockedConstruction<BookDao> mocked = mockConstruction(BookDao.class,
                (mock, context) -> {
                    doThrow(new SQLException("Ошибка базы данных")).when(mock).save(any(Book.class));
                })) {

            BookService service = new BookService(mock(Connection.class));
            assertThrows(SQLException.class, () ->
                    service.createBook("Test", 1, 2023, true));

            BookDao mockDao = mocked.constructed().get(0);
            verify(mockDao).save(any(Book.class));
        }
    }

    @Test
    void showAllBooks_ShouldThrowSQLException() throws SQLException {
        try (MockedConstruction<BookDao> mocked = mockConstruction(BookDao.class,
                (mock, context) -> {
                    when(mock.findAll()).thenThrow(new SQLException("Ошибка базы данных"));
                })) {

            BookService service = new BookService(mock(Connection.class));
            assertThrows(SQLException.class, service::showAllBooks);

            BookDao mockDao = mocked.constructed().get(0);
            verify(mockDao).findAll();
        }
    }
}
