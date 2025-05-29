package app.javacode.service;

import app.javacode.dao.AuthorDao;
import app.javacode.entity.Author;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AuthorService {
    private final AuthorDao authorDao;

    public AuthorService(Connection connection) {
        this.authorDao = new AuthorDao(connection);
    }

    public void createAuthor(String name, String country) throws SQLException {
        Author author = new Author(name, country);
        authorDao.save(author);
        System.out.println("Автор успешно добавлен с ID: " + author.getId());
    }

    public void showAllAuthors() throws SQLException {
        List<Author> authors = authorDao.findAll();
        System.out.println("\n=== Список авторов ===");
        authors.forEach(System.out::println);
    }

    public Author findById(int id) throws SQLException {
        return authorDao.findById(id);
    }

    public void update(Author author) throws SQLException {
        authorDao.update(author);
    }

    public void delete(int id) throws SQLException {
        authorDao.delete(id);
    }
}
