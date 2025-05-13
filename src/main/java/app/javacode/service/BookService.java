package app.javacode.service;

import app.javacode.connection.HibernateUtil;
import app.javacode.dao.BookDao;
import app.javacode.entity.Author;
import app.javacode.entity.Book;

import java.util.List;
import java.util.Optional;

public class BookService {
    private final BookDao bookDao;

    public BookService() {
        this.bookDao = new BookDao(HibernateUtil.getSessionFactory());
    }

    public void createBook(String title, int authorId, int year, boolean available) {
        Book book = new Book();
        book.setTitle(title);
        book.setYear(year);
        book.setAvailable(available);
        book.setAuthor(new Author());
        book.getAuthor().setId(authorId);

        bookDao.save(book);
        System.out.println("Книга успешно добавлена с ID: " + book.getId());
    }

    public Optional<Book> findById(int id) {
        return bookDao.findById(id);
    }

    public void showAllBooks() {
        List<Book> books = bookDao.findAll();
        System.out.println("\n=== Список книг ===");
        books.forEach(this::printBookDetails);
    }

    public void showBooksByAuthor(int authorId) {
        List<Book> books = bookDao.findByAuthorId(authorId);
        System.out.println("\n=== Книги автора ===");
        books.forEach(this::printBookDetails);
    }

    public void update(Book book) {
        bookDao.update(book);
        System.out.println("Книга успешно обновлена");
    }

    public void deleteBook(int id) {
        bookDao.delete(id);
        System.out.println("Книга успешно удалена");
    }

    private void printBookDetails(Book book) {
        String details = "ID: " + book.getId() +
                ", Название: " + book.getTitle() +
                ", Автор: " + book.getAuthor().getName() +
                " (ID: " + book.getAuthor().getId() + "), " +
                "Год: " + book.getYear() +
                ", Доступна: " + (book.isAvailable() ? "Да" : "Нет");

        System.out.println(details);
    }
}
