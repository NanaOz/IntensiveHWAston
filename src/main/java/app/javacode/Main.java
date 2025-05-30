package app.javacode;

import app.javacode.connection.HibernateUtil;
import app.javacode.menu.MainMenu;
import app.javacode.service.AuthorService;
import app.javacode.service.BookService;

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