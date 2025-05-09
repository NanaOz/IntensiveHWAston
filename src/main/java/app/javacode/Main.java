package main.java.app.javacode;

import main.java.app.javacode.connection.HibernateUtil;
import main.java.app.javacode.menu.MainMenu;
import main.java.app.javacode.service.AuthorService;
import main.java.app.javacode.service.BookService;

public class Main {
    public static void main(String[] args) {
        try {
            AuthorService authorService = new AuthorService();
            BookService bookService = new BookService();

            MainMenu mainMenu = new MainMenu(authorService, bookService);
            mainMenu.show();
        } finally {
            HibernateUtil.shutdown();
        }
    }
}