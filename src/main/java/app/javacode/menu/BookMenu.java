package app.javacode.menu;

import app.javacode.service.AuthorService;
import app.javacode.service.BookService;

import java.sql.SQLException;
import java.util.Scanner;

public class BookMenu implements Menu {
    private final Scanner scanner;
    private final BookService bookService;
    private final AuthorService authorService;

    public BookMenu(BookService bookService, AuthorService authorService) {
        this.scanner = new Scanner(System.in);
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @Override
    public void show() {
        while (true) {
            System.out.println("\n=== Меню книг ===");
            System.out.println("1. Добавить книгу");
            System.out.println("2. Показать все книги");
            System.out.println("3. Показать книги автора");
            System.out.println("4. Обновить книгу");
            System.out.println("5. Удалить книгу");
            System.out.println("0. Назад");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1 -> addBook();
                    case 2 -> showAllBooks();
                    case 3 -> showBooksByAuthor();
                    case 4 -> updateBook();
                    case 5 -> deleteBook();
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

    private void addBook() throws SQLException {
        System.out.print("Введите название книги: ");
        String title = scanner.nextLine();
        System.out.print("Введите ID автора: ");
        int authorId = scanner.nextInt();
        System.out.print("Введите год издания: ");
        int year = scanner.nextInt();
        System.out.print("Доступна (true/false): ");
        boolean available = scanner.nextBoolean();
        bookService.createBook(title, authorId, year, available);
    }

    private void showAllBooks() throws SQLException {
        bookService.showAllBooks();
    }

    private void showBooksByAuthor() throws SQLException {
        System.out.print("Введите ID автора: ");
        int authorId = scanner.nextInt();
        bookService.showBooksByAuthor(authorId);
    }

    private void updateBook() throws SQLException {
        System.out.print("Введите ID книги для обновления: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Новое название (оставьте пустым, чтобы не менять): ");
        String title = scanner.nextLine();
        title = title.isEmpty() ? null : title;

        System.out.print("Новый ID автора (0, чтобы не менять): ");
        int authorId = scanner.nextInt();

        System.out.print("Новый год издания (0, чтобы не менять): ");
        int year = scanner.nextInt();

        System.out.print("Новая доступность (оставьте пустым, чтобы не менять): ");
        scanner.nextLine();
        String availableStr = scanner.nextLine();
        Boolean available = availableStr.isEmpty() ? null : Boolean.parseBoolean(availableStr);

        bookService.updateBook(id, title, authorId, year, available);
    }

    private void deleteBook() throws SQLException {
        System.out.print("Введите ID книги для удаления: ");
        int id = scanner.nextInt();
        bookService.deleteBook(id);
    }
}
