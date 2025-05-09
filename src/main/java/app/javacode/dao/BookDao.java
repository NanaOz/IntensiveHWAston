package main.java.app.javacode.dao;

import main.java.app.javacode.entity.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class BookDao {
    private final SessionFactory sessionFactory;

    public BookDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Book book) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(book);
            transaction.commit();
        }
    }

    public Optional<Book> findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.find(Book.class, id));
        }
    }

    public void update(Book book) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(book);
            transaction.commit();
        }
    }

    public void delete(int id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Book book = session.find(Book.class, id);
            if (book != null) {
                session.remove(book);
            }
            transaction.commit();
        }
    }

    public List<Book> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<Book> query = session.createQuery("SELECT b FROM Book b JOIN FETCH b.author", Book.class);
            return query.getResultList();
        }
    }

    public List<Book> findByAuthorId(int authorId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Book> query = session.createQuery(
                    "SELECT b FROM Book b JOIN FETCH b.author WHERE b.author.id = :authorId", Book.class);
            query.setParameter("authorId", authorId);
            return query.getResultList();
        }
    }
}
