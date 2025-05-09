package app.javacode.connection;
import java.sql.*;
import java.util.Properties;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/testappaston";
    private static final String USER = "postgres";
    private static final String PASSWORD = "0025";

    public static Connection getConnection() throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", USER);
        props.setProperty("password", PASSWORD);
        return DriverManager.getConnection(URL, props);
    }
}
