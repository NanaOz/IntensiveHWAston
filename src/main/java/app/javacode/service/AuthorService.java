package app.javacode.service;

import app.javacode.connection.HibernateUtil;
import app.javacode.dao.AuthorDao;
import app.javacode.entity.Author;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class AuthorService {
    private static final Logger logger = LoggerFactory.getLogger(AuthorService.class);
    private final AuthorDao authorDao;

    public AuthorService() {
        this.authorDao = new AuthorDao(HibernateUtil.getSessionFactory());
    }

    public void createAuthor(String name, String country) {
        Author author = new Author();
        author.setName(name);
        author.setCountry(country);
        authorDao.save(author);
        logger.info("Автор успешно добавлен с ID: {}", author.getId());
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
