package com.haushekmiva;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");

            sessionFactory = configuration.buildSessionFactory();

            System.out.println("✅ SessionFactory успешно создана");
            System.out.println("✅ HikariCP пул соединений инициализирован");

        } catch (Exception e) {
            System.err.println("❌ Ошибка создания SessionFactory!");
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
            System.out.println("✅ SessionFactory закрыта");
            System.out.println("✅ HikariCP пул соединений освобожден");
        }
    }
}