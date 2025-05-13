package app.javacode.menu;

import app.javacode.entity.Book;
import app.javacode.service.AuthorService;
import app.javacode.service.BookService;

import java.util.Optional;
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
            printMenuOptions();
            int choice = readIntInput();

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
            } catch (Exception e) {
                System.err.println("Ошибка: " + e.getMessage());
            }
        }
    }

    /**
     * Выводит на экран доступные опции меню для работы с книгами.
     */
    private void printMenuOptions() {
        System.out.println("\n=== Меню книг ===");
        System.out.println("1. Добавить книгу");
        System.out.println("2. Показать все книги");
        System.out.println("3. Показать книги автора");
        System.out.println("4. Обновить книгу");
        System.out.println("5. Удалить книгу");
        System.out.println("0. Назад");
        System.out.print("Выберите действие: ");
    }

    /**
     * Считывает целочисленный ввод от пользователя.
     *
     * @return Введенное целое число.
     */
    private int readIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("Пожалуйста, введите число!");
            scanner.next();
        }
        int input = scanner.nextInt();
        scanner.nextLine();
        return input;
    }

    /**
     * Считывает булевый ввод от пользователя (true/false или да/нет).
     *
     * @return Введенное булевое значение.
     */
    private boolean readBooleanInput() {
        while (true) {
            String input = scanner.nextLine().toLowerCase();
            if (input.equals("true") || input.equals("да")) {
                return true;
            } else if (input.equals("false") || input.equals("нет")) {
                return false;
            }
            System.out.println("Пожалуйста, введите 'true'/'да' или 'false'/'нет'");
        }
    }

    /**
     * Добавляет новую книгу, запрашивая информацию у пользователя.
     */
    private void addBook() {
        System.out.print("Введите название книги: ");
        String title = scanner.nextLine();

        System.out.print("Введите ID автора: ");
        int authorId = readIntInput();

        if (authorService.findById(authorId).isEmpty()) {
            System.out.println("Автор с ID " + authorId + " не существует!");
            return;
        }

        System.out.print("Введите год издания: ");
        int year = readIntInput();

        System.out.print("Доступна (true/да или false/нет): ");
        boolean available = readBooleanInput();

        bookService.createBook(title, authorId, year, available);
    }

    /**
     * Отображает все книги.
     */
    private void showAllBooks() {
        bookService.showAllBooks();
    }

    /**
     * Показывает книги, написанные конкретным автором, запрашивая его ID.
     */
    private void showBooksByAuthor() {
        System.out.print("Введите ID автора: ");
        int authorId = readIntInput();
        bookService.showBooksByAuthor(authorId);
    }

    /**
     * Обновляет информацию о книге, запрашивая новый ID и изменения названия, автора, года издания и доступности.
     */
    private void updateBook() {
        System.out.print("Введите ID книги для обновления: ");
        int id = readIntInput();

        Optional<Book> bookOpt = bookService.findById(id);
        if (bookOpt.isEmpty()) {
            System.out.println("Книга с ID " + id + " не найдена");
            return;
        }

        Book book = bookOpt.get();
        updateBookTitle(book);
        updateBookAuthor(book);
        updateBookYear(book);
        updateBookAvailability(book);

        bookService.update(book);
    }

    /**
     * Обновляет название указанной книги.
     *
     * @param book Книга, название которой необходимо обновить.
     */
    private void updateBookTitle(Book book) {
        System.out.print("Новое название (текущее: " + book.getTitle() + "): ");
        String title = scanner.nextLine();
        if (!title.isEmpty()) {
            book.setTitle(title);
        }
    }

    /**
     * Обновляет автора указанной книги.
     *
     * @param book Книга, автора которой необходимо обновить.
     */
    private void updateBookAuthor(Book book) {
        System.out.print("Новый ID автора (текущий: " + book.getAuthor().getId() + ", 0 чтобы не менять): ");
        int authorId = readIntInput();
        if (authorId > 0) {
            if (authorService.findById(authorId).isEmpty()) {
                System.out.println("Автор с ID " + authorId + " не существует!");
                return;
            }
            book.getAuthor().setId(authorId);
        }
    }

    /**
     * Обновляет год издания указанной книги.
     *
     * @param book Книга, год издания которой необходимо обновить.
     */
    private void updateBookYear(Book book) {
        System.out.print("Новый год издания (текущий: " + book.getYear() + ", 0 чтобы не менять): ");
        int year = readIntInput();
        if (year > 0) {
            book.setYear(year);
        }
    }

    /**
     * Обновляет доступность указанной книги.
     *
     * @param book Книга, доступность которой необходимо обновить.
     */
    private void updateBookAvailability(Book book) {
        System.out.print("Новая доступность (текущая: " + book.isAvailable() + ", оставьте пустым чтобы не менять): ");
        String availableStr = scanner.nextLine();
        if (!availableStr.isEmpty()) {
            book.setAvailable(readBooleanInput());
        }
    }

    /**
     * Удаляет книгу по заданному ID.
     */
    private void deleteBook() {
        System.out.print("Введите ID книги для удаления: ");
        int id = readIntInput();
        bookService.deleteBook(id);
        System.out.println("Книга успешно удалена");
    }
}