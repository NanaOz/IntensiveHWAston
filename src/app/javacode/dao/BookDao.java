package app.javacode.dao;

import app.javacode.entity.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookDao {
    private final Connection connection;

    public BookDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Сохраняет книгу в базе данных.
     *
     * @param book объект книги для сохранения.
     * @throws SQLException если возникла ошибка при работе с базой данных.
     */
    public void save(Book book) throws SQLException {
        String sql = "INSERT INTO books (title, author_id, year, available) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, book.getTitle());
            stmt.setInt(2, book.getAuthorId());
            stmt.setInt(3, book.getYear());
            stmt.setBoolean(4, book.isAvailable());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    book.setId(rs.getInt(1));
                }
            }
        }
    }

    /**
     * Находит книгу по ее идентификатору.
     *
     * @param id идентификатор книги, которую нужно найти.
     * @return объект книги с указанным идентификатором, или null, если книга не найдена.
     * @throws SQLException если возникла ошибка при работе с базой данных.
     */
    public Book findById(int id) throws SQLException {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthorId(rs.getInt("author_id"));
                book.setYear(rs.getInt("year"));
                book.setAvailable(rs.getBoolean("available"));
                return book;
            }
            return null;
        }
    }

    /**
     * Обновляет данные книги в базе данных.
     *
     * @param book объект книги, содержащий обновленные данные.
     * @throws SQLException если возникла ошибка при работе с базой данных.
     */
    public void update(Book book) throws SQLException {
        String sql = "UPDATE books SET title = ?, author_id = ?, year = ?, available = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, book.getTitle());
            stmt.setInt(2, book.getAuthorId());
            stmt.setInt(3, book.getYear());
            stmt.setBoolean(4, book.isAvailable());
            stmt.setInt(5, book.getId());
            stmt.executeUpdate();
        }
    }

    /**
     * Удаляет книгу из базы данных по ее идентификатору.
     *
     * @param id идентификатор книги, которую нужно удалить.
     * @throws SQLException если возникла ошибка при работе с базой данных.
     */
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM books WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    /**
     * Находит все книги в базе данных.
     *
     * @return список книг, хранящихся в базе данных.
     * @throws SQLException если возникла ошибка при работе с базой данных.
     */
    public List<Book> findAll() throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT b.*, a.name as author_name FROM books b JOIN authors a ON b.author_id = a.id";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthorId(rs.getInt("author_id"));
                book.setYear(rs.getInt("year"));
                book.setAvailable(rs.getBoolean("available"));
                books.add(book);
            }
        }
        return books;
    }

    /**
     * Находит все книги, написанные автором с указанным идентификатором.
     *
     * @param authorId идентификатор автора, чьи книги нужно найти.
     * @return список книг, написанных автором с указанным идентификатором.
     * @throws SQLException если возникла ошибка при работе с базой данных.
     */
    public List<Book> findByAuthorId(int authorId) throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE author_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, authorId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthorId(rs.getInt("author_id"));
                book.setYear(rs.getInt("year"));
                book.setAvailable(rs.getBoolean("available"));
                books.add(book);
            }
        }
        return books;
    }
}
