package app.javacode.dao;

import app.javacode.entity.Author;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class AuthorDao {
    private final SessionFactory sessionFactory;

    public AuthorDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Author author) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(author);
            transaction.commit();
        }
    }

    public Optional<Author> findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.find(Author.class, id));
        }
    }

    public void update(Author author) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(author);
            transaction.commit();
        }
    }

    public void delete(int id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Author author = session.find(Author.class, id);
            if (author != null) {
                session.remove(author);
            }
            transaction.commit();
        }
    }

    public List<Author> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<Author> query = session.createQuery("FROM Author", Author.class);
            return query.getResultList();
        }
    }
}
