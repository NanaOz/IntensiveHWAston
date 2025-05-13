package app.javacode.menu;

import app.javacode.entity.Author;
import app.javacode.service.AuthorService;

import java.util.Optional;
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
            printMenuOptions();
            int choice = readIntInput();

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
            } catch (Exception e) {
                System.err.println("Ошибка: " + e.getMessage());
            }
        }
    }

    /**
     * Выводит на экран доступные опции меню для работы с авторами.
     */
    private void printMenuOptions() {
        System.out.println("\n=== Меню авторов ===");
        System.out.println("1. Добавить автора");
        System.out.println("2. Показать всех авторов");
        System.out.println("3. Найти автора по ID");
        System.out.println("4. Обновить автора");
        System.out.println("5. Удалить автора");
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
     * Добавляет нового автора, запрашивая у пользователя имя и страну.
     */
    private void addAuthor() {
        System.out.print("Введите имя автора: ");
        String name = scanner.nextLine();
        System.out.print("Введите страну: ");
        String country = scanner.nextLine();
        authorService.createAuthor(name, country);
    }

    /**
     * Отображает всех авторов.
     */
    private void showAllAuthors() {
        authorService.showAllAuthors();
    }

    /**
     * Ищет автора по заданному ID и отображает информацию о нем, если он найден.
     */
    private void findAuthorById() {
        System.out.print("Введите ID автора: ");
        int id = readIntInput();
        Optional<Author> author = authorService.findById(id);
        author.ifPresentOrElse(
                a -> System.out.println("Найден автор: " + a),
                () -> System.out.println("Автор с ID " + id + " не найден")
        );
    }

    /**
     * Обновляет информацию об авторе, запрашивая новый ID и изменения имени и страны.
     */
    private void updateAuthor() {
        System.out.print("Введите ID автора для обновления: ");
        int id = readIntInput();

        Optional<Author> authorOpt = authorService.findById(id);
        if (authorOpt.isEmpty()) {
            System.out.println("Автор с ID " + id + " не найден");
            return;
        }

        Author author = authorOpt.get();
        System.out.print("Введите новое имя (текущее: " + author.getName() + "): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            author.setName(name);
        }

        System.out.print("Введите новую страну (текущая: " + author.getCountry() + "): ");
        String country = scanner.nextLine();
        if (!country.isEmpty()) {
            author.setCountry(country);
        }

        authorService.update(author);
        System.out.println("Автор обновлен");
    }

    /**
     * Удаляет автора по заданному ID.
     */
    private void deleteAuthor() {
        System.out.print("Введите ID автора для удаления: ");
        int id = readIntInput();
        authorService.delete(id);
        System.out.println("Автор удален");
    }
}