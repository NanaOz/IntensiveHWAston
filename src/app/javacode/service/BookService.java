package app.javacode.service;

import app.javacode.dao.BookDao;
import app.javacode.entity.Book;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BookService {
    private final BookDao bookDao;

    public BookService(Connection connection) {
        this.bookDao = new BookDao(connection);
    }

    public void createBook(String title, int authorId, int year, boolean available) throws SQLException {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthorId(authorId);
        book.setYear(year);
        book.setAvailable(available);
        bookDao.save(book);
        System.out.println("Книга успешно добавлена с ID: " + book.getId());
    }

    public void showAllBooks() throws SQLException {
        List<Book> books = bookDao.findAll();
        System.out.println("\n=== Список книг ===");
        books.forEach(book -> {
            System.out.printf("ID: %d, Название: %s, Автор ID: %d, Год: %d, Доступна: %s\n",
                    book.getId(),
                    book.getTitle(),
                    book.getAuthorId(),
                    book.getYear(),
                    book.isAvailable() ? "Да" : "Нет");
        });
    }

    public void showBooksByAuthor(int authorId) throws SQLException {
        List<Book> books = bookDao.findByAuthorId(authorId);
        System.out.println("\n=== Книги автора ===");
        books.forEach(book -> {
            System.out.printf("ID: %d, Название: %s, Год: %d, Доступна: %s\n",
                    book.getId(),
                    book.getTitle(),
                    book.getYear(),
                    book.isAvailable() ? "Да" : "Нет");
        });
    }

    public void updateBook(int id, String title, int authorId, int year, Boolean available) throws SQLException {
        Book book = bookDao.findById(id);
        if (book == null) {
            System.out.println("Книга с ID " + id + " не найдена");
            return;
        }

        if (title != null) book.setTitle(title);
        if (authorId > 0) book.setAuthorId(authorId);
        if (year > 0) book.setYear(year);
        if (available != null) book.setAvailable(available);

        bookDao.update(book);
        System.out.println("Книга успешно обновлена");
    }

    public void deleteBook(int id) throws SQLException {
        bookDao.delete(id);
        System.out.println("Книга успешно удалена");
    }
}
