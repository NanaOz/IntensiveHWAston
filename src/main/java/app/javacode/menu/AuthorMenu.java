package app.javacode.menu;

import app.javacode.entity.Author;
import app.javacode.service.AuthorService;

import java.sql.SQLException;
import java.util.Scanner;

public class AuthorMenu implements Menu {
    private final Scanner scanner;
    private final AuthorService authorService;

    public AuthorMenu(AuthorService authorService) {
        this.scanner = new Scanner(System.in);
        this.authorService = authorService;
    }

    @Override
    public void show() {
        while (true) {
            System.out.println("\n=== Меню авторов ===");
            System.out.println("1. Добавить автора");
            System.out.println("2. Показать всех авторов");
            System.out.println("3. Найти автора по ID");
            System.out.println("4. Обновить автора");
            System.out.println("5. Удалить автора");
            System.out.println("0. Назад");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1 -> addAuthor();
                    case 2 -> showAllAuthors();
                    case 3 -> findAuthorById();
                    case 4 -> updateAuthor();
                    case 5 -> deleteAuthor();
                    case 0 -> {
                        return;
                    }
                    default -> System.out.println("Неверный выбор!");
                }
            } catch (SQLException e) {
                System.err.println("Ошибка базы данных: " + e.getMessage());
            }
        }
    }

    private void addAuthor() throws SQLException {
        System.out.print("Введите имя автора: ");
        String name = scanner.nextLine();
        System.out.print("Введите страну: ");
        String country = scanner.nextLine();
        authorService.createAuthor(name, country);
    }

    private void showAllAuthors() throws SQLException {
        authorService.showAllAuthors();
    }

    private void findAuthorById() throws SQLException {
        System.out.print("Введите ID автора: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Author author = authorService.findById(id);
        if (author != null) {
            System.out.println("Найден автор: " + author);
        } else {
            System.out.println("Автор с ID " + id + " не найден");
        }
    }

    private void updateAuthor() throws SQLException {
        System.out.print("Введите ID автора для обновления: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Введите новое имя: ");
        String name = scanner.nextLine();
        System.out.print("Введите новую страну: ");
        String country = scanner.nextLine();
        Author author = new Author(name, country);
        author.setId(id);
        authorService.update(author);
        System.out.println("Автор обновлен");
    }

    private void deleteAuthor() throws SQLException {
        System.out.print("Введите ID автора для удаления: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        authorService.delete(id);
        System.out.println("Автор удален");
    }
}
