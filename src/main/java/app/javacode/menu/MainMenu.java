package app.javacode.menu;

import app.javacode.service.AuthorService;
import app.javacode.service.BookService;

import java.util.Scanner;

public class MainMenu implements Menu {
    private final Scanner scanner;
    private final AuthorMenu authorMenu;
    private final BookMenu bookMenu;

    public MainMenu(AuthorService authorService, BookService bookService) {
        this.scanner = new Scanner(System.in);
        this.authorMenu = new AuthorMenu(authorService);
        this.bookMenu = new BookMenu(bookService, authorService);
    }

    @Override
    public void show() {
        while (true) {
            System.out.println("\n=== Главное меню ===");
            System.out.println("1. Управление авторами");
            System.out.println("2. Управление книгами");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> authorMenu.show();
                case 2 -> bookMenu.show();
                case 0 -> {
                    System.out.println("Выход из программы...");
                    return;
                }
                default -> System.out.println("Неверный выбор!");
            }
        }
    }
}
