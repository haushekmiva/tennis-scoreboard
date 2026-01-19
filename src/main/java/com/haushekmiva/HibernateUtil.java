package com.haushekmiva;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    // Singleton паттерн - один экземпляр SessionFactory на всё приложение
    private static SessionFactory sessionFactory;

    // Статический блок - выполняется один раз при загрузке класса
    static {
        try {
            // Создаем Configuration объект
            Configuration configuration = new Configuration();

            // Загружаем настройки из hibernate.cfg.xml
            // Файл должен быть в src/main/resources/
            configuration.configure("hibernate.cfg.xml");

            // Создаем SessionFactory - фабрику для создания Session
            // Это ТЯЖЕЛАЯ операция, делаем ОДИН раз!
            sessionFactory = configuration.buildSessionFactory();

            System.out.println("✅ SessionFactory успешно создана");
            System.out.println("✅ HikariCP пул соединений инициализирован");

        } catch (Exception e) {
            System.err.println("❌ Ошибка создания SessionFactory!");
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    // Получить SessionFactory
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    // Закрыть SessionFactory при завершении приложения
    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
            System.out.println("✅ SessionFactory закрыта");
            System.out.println("✅ HikariCP пул соединений освобожден");
        }
    }
}