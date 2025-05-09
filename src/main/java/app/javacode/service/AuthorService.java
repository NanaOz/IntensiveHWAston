package main.java.app.javacode.service;

import main.java.app.javacode.connection.HibernateUtil;
import main.java.app.javacode.dao.AuthorDao;
import main.java.app.javacode.entity.Author;

import java.util.List;
import java.util.Optional;

public class AuthorService {
    private final AuthorDao authorDao;

    public AuthorService() {
        this.authorDao = new AuthorDao(HibernateUtil.getSessionFactory());
    }

    public void createAuthor(String name, String country) {
        Author author = new Author();
        author.setName(name);
        author.setCountry(country);
        authorDao.save(author);
        System.out.println("Автор успешно добавлен с ID: " + author.getId());
    }

    public void showAllAuthors() {
        List<Author> authors = authorDao.findAll();
        System.out.println("\n=== Список авторов ===");
        authors.forEach(System.out::println);
    }

    public Optional<Author> findById(int id) {
        return authorDao.findById(id);
    }

    public void update(Author author) {
        authorDao.update(author);
    }

    public void delete(int id) {
        authorDao.delete(id);
    }
}
