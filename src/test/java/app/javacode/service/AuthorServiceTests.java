package app.javacode.service;

import app.javacode.dao.AuthorDao;
import app.javacode.entity.Author;
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
public class AuthorServiceTests {
    @Mock
    private SessionFactory sessionFactory;

    @Test
    void createAuthor_ShouldSaveAuthorSuccess() {
        try (MockedConstruction<AuthorDao> mocked = mockConstruction(AuthorDao.class,
                (mock, context) -> {
                    doAnswer(invocation -> {
                        Author author = invocation.getArgument(0);
                        author.setId(1);
                        return null;
                    }).when(mock).save(any(Author.class));
                })) {

            AuthorService service = new AuthorService();
            service.createAuthor("John Doe", "USA");

            AuthorDao mockDao = mocked.constructed().get(0);
            verify(mockDao).save(any(Author.class));
        }
    }

    @Test
    void showAllAuthors_ShouldPrintAllAuthors() {
        Author author1 = new Author();
        author1.setId(1);
        author1.setName("Author 1");
        author1.setCountry("Country 1");

        Author author2 = new Author();
        author2.setId(2);
        author2.setName("Author 2");
        author2.setCountry("Country 2");

        List<Author> authors = List.of(author1, author2);

        try (MockedConstruction<AuthorDao> mocked = mockConstruction(AuthorDao.class,
                (mock, context) -> {
                    when(mock.findAll()).thenReturn(authors);
                })) {

            AuthorService service = new AuthorService();
            service.showAllAuthors();

            AuthorDao mockDao = mocked.constructed().get(0);
            verify(mockDao).findAll();
        }
    }

    @Test
    void findById_ShouldReturnAuthorWhenExists() {
        Author expected = new Author();
        expected.setId(1);
        expected.setName("Test Author");
        expected.setCountry("Test Country");

        try (MockedConstruction<AuthorDao> mocked = mockConstruction(AuthorDao.class,
                (mock, context) -> {
                    when(mock.findById(1)).thenReturn(Optional.of(expected));
                })) {

            AuthorService service = new AuthorService();
            Optional<Author> result = service.findById(1);

            assertTrue(result.isPresent());
            assertEquals(expected, result.get());
        }
    }

    @Test
    void findById_ShouldReturnEmptyWhenNotExists() {
        try (MockedConstruction<AuthorDao> mocked = mockConstruction(AuthorDao.class,
                (mock, context) -> {
                    when(mock.findById(999)).thenReturn(Optional.empty());
                })) {

            AuthorService service = new AuthorService();
            Optional<Author> result = service.findById(999);

            assertTrue(result.isEmpty());
        }
    }

    @Test
    void update_ShouldCallDaoUpdate() {
        Author author = new Author();
        author.setId(1);
        author.setName("Name");
        author.setCountry("Country");

        try (MockedConstruction<AuthorDao> mocked = mockConstruction(AuthorDao.class)) {
            AuthorService service = new AuthorService();
            service.update(author);

            AuthorDao mockDao = mocked.constructed().get(0);
            verify(mockDao).update(author);
        }
    }

    @Test
    void delete_ShouldCallDaoDelete() {
        try (MockedConstruction<AuthorDao> mocked = mockConstruction(AuthorDao.class)) {
            AuthorService service = new AuthorService();
            service.delete(1);

            AuthorDao mockDao = mocked.constructed().get(0);
            verify(mockDao).delete(1);
        }
    }
}