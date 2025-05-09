package app.javacode.dao;

import app.javacode.entity.Author;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDao {
    private final Connection connection;

    public AuthorDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Сохраняет автора в базе данных.
     *
     * @param author объект автора для сохранения.
     * @throws SQLException если возникла ошибка при работе с базой данных.
     */
    public void save(Author author) throws SQLException {
        String sql = "INSERT INTO authors (name, country) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, author.getName());
            stmt.setString(2, author.getCountry());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    author.setId(rs.getInt(1));
                }
            }
        }
    }

    /**
     * Находит автора по его идентификатору.
     *
     * @param id идентификатор автора, которого нужно найти.
     * @return объект автора с указанным идентификатором, или null, если автор не найден.
     * @throws SQLException если возникла ошибка при работе с базой данных.
     */
    public Author findById(int id) throws SQLException {
        String sql = "SELECT * FROM authors WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Author(
                        rs.getString("name"),
                        rs.getString("country")
                );
            }
            return null;
        }
    }

    /**
     * Обновляет данные автора в базе данных.
     *
     * @param author объект автора, содержащий обновленные данные.
     * @throws SQLException если возникла ошибка при работе с базой данных.
     */
    public void update(Author author) throws SQLException {
        String sql = "UPDATE authors SET name = ?, country = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, author.getName());
            stmt.setString(2, author.getCountry());
            stmt.setInt(3, author.getId());
            stmt.executeUpdate();
        }
    }

    /**
     * Удаляет автора из базы данных по его идентификатору.
     *
     * @param id идентификатор автора, которого нужно удалить.
     * @throws SQLException если возникла ошибка при работе с базой данных.
     */
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM authors WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    /**
     * Находит всех авторов в базе данных.
     *
     * @return список авторов, хранящихся в базе данных.
     * @throws SQLException если возникла ошибка при работе с базой данных.
     */
    public List<Author> findAll() throws SQLException {
        List<Author> authors = new ArrayList<>();
        String sql = "SELECT * FROM authors";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Author author = new Author(
                        rs.getString("name"),
                        rs.getString("country")
                );
                author.setId(rs.getInt("id"));
                authors.add(author);
            }
        }
        return authors;
    }
}
