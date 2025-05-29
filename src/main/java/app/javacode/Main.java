package app.javacode;

import app.javacode.connection.DatabaseConnection;
import app.javacode.menu.MainMenu;
import app.javacode.service.AuthorService;
import app.javacode.service.BookService;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            AuthorService authorService = new AuthorService(connection);
            BookService bookService = new BookService(connection);

            MainMenu mainMenu = new MainMenu(authorService, bookService);
            mainMenu.show();
        } catch (SQLException e) {
            System.err.println("Ошибка базы данных: " + e.getMessage());
            e.printStackTrace();
        }
    }
}