package app.javacode.connection;

import app.javacode.entity.Author;
import app.javacode.entity.Book;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.io.InputStream;
import java.util.Properties;

public class HibernateUtil {
    private static final String HIBERNATE_PROPERTIES = "db.properties";
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = getConfiguration();
                configuration.addAnnotatedClass(Author.class);
                configuration.addAnnotatedClass(Book.class);

                StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    private static Configuration getConfiguration() {
        Configuration configuration = new Configuration();
        try (InputStream input = HibernateUtil.class.getClassLoader()
                .getResourceAsStream(HIBERNATE_PROPERTIES)) {
            Properties settings = new Properties();
            settings.load(input);
            configuration.setProperties(settings);
            return configuration;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return configuration;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}

