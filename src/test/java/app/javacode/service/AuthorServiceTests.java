package app.javacode.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;

import app.javacode.dao.AuthorDao;
import app.javacode.entity.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedConstruction;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTests {

    @Test
    void createAuthor_WithMockedDaoConstruction_ShouldWork() throws SQLException {
        Connection mockConnection = mock(Connection.class);

        try (MockedConstruction<AuthorDao> mocked = mockConstruction(AuthorDao.class)) {
            AuthorService service = new AuthorService(mockConnection);

            service.createAuthor("Test", "Country");

            AuthorDao mockDao = mocked.constructed().get(0);
            verify(mockDao).save(any(Author.class));
        }
    }

    @Test
    void createAuthor_ShouldSaveAuthorAndPrintId() throws SQLException {
        try (MockedConstruction<AuthorDao> mocked = mockConstruction(AuthorDao.class,
                (mock, context) -> {
                    doAnswer(invocation -> {
                        Author author = invocation.getArgument(0);
                        author.setId(1);
                        return null;
                    }).when(mock).save(any(Author.class));
                })) {

            AuthorService service = new AuthorService(mock(Connection.class));
            service.createAuthor("John Doe", "USA");

            AuthorDao mockDao = mocked.constructed().get(0);
            verify(mockDao).save(any(Author.class));
        }
    }

    @Test
    void showAllAuthors_ShouldPrintAllAuthors() throws SQLException {
        List<Author> authors = Arrays.asList(
                new Author("Author 1", "Country 1"),
                new Author("Author 2", "Country 2")
        );

        try (MockedConstruction<AuthorDao> mocked = mockConstruction(AuthorDao.class,
                (mock, context) -> {
                    when(mock.findAll()).thenReturn(authors);
                })) {

            AuthorService service = new AuthorService(mock(Connection.class));
            service.showAllAuthors();

            AuthorDao mockDao = mocked.constructed().get(0);
            verify(mockDao).findAll();
        }
    }

    @Test
    void findById_ShouldReturnAuthor() throws SQLException {
        Author expectedAuthor = new Author("Test Author", "Test Country");
        expectedAuthor.setId(1);

        try (MockedConstruction<AuthorDao> mocked = mockConstruction(AuthorDao.class,
                (mock, context) -> {
                    when(mock.findById(1)).thenReturn(expectedAuthor);
                })) {

            AuthorService service = new AuthorService(mock(Connection.class));
            Author result = service.findById(1);

            assertEquals(expectedAuthor, result);
            AuthorDao mockDao = mocked.constructed().get(0);
            verify(mockDao).findById(1);
        }
    }

    @Test
    void findById_ShouldReturnNull() throws SQLException {
        try (MockedConstruction<AuthorDao> mocked = mockConstruction(AuthorDao.class,
                (mock, context) -> {
                    when(mock.findById(999)).thenReturn(null);
                })) {

            AuthorService service = new AuthorService(mock(Connection.class));
            Author result = service.findById(999);

            assertNull(result);
            AuthorDao mockDao = mocked.constructed().get(0);
            verify(mockDao).findById(999);
        }
    }

    @Test
    void update_ShouldCallDaoUpdate() throws SQLException {
        Author authorToUpdate = new Author("Updated Name", "Updated Country");
        authorToUpdate.setId(1);

        try (MockedConstruction<AuthorDao> mocked = mockConstruction(AuthorDao.class)) {
            AuthorService service = new AuthorService(mock(Connection.class));
            service.update(authorToUpdate);

            AuthorDao mockDao = mocked.constructed().get(0);
            verify(mockDao).update(authorToUpdate);
        }
    }

    @Test
    void delete_ShouldCallDaoDelete() throws SQLException {
        try (MockedConstruction<AuthorDao> mocked = mockConstruction(AuthorDao.class)) {
            AuthorService service = new AuthorService(mock(Connection.class));
            service.delete(1);

            AuthorDao mockDao = mocked.constructed().get(0);
            verify(mockDao).delete(1);
        }
    }

    @Test
    void createAuthor_ShouldThrowSQLException() throws SQLException {
        try (MockedConstruction<AuthorDao> mocked = mockConstruction(AuthorDao.class,
                (mock, context) -> {
                    doThrow(new SQLException("Ошибка базы данных")).when(mock).save(any(Author.class));
                })) {

            AuthorService service = new AuthorService(mock(Connection.class));
            assertThrows(SQLException.class, () -> service.createAuthor("John", "USA"));

            AuthorDao mockDao = mocked.constructed().get(0);
            verify(mockDao).save(any(Author.class));
        }
    }

    @Test
    void showAllAuthors_ShouldThrowSQLException() throws SQLException {
        try (MockedConstruction<AuthorDao> mocked = mockConstruction(AuthorDao.class,
                (mock, context) -> {
                    when(mock.findAll()).thenThrow(new SQLException("Ошибка базы данных"));
                })) {

            AuthorService service = new AuthorService(mock(Connection.class));
            assertThrows(SQLException.class, service::showAllAuthors);

            AuthorDao mockDao = mocked.constructed().get(0);
            verify(mockDao).findAll();
        }
    }
}
