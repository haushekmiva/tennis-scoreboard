package com.haushekmiva.dao;

import com.haushekmiva.exceptions.DataAccessException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.io.Serializable;

import static com.haushekmiva.HibernateUtil.getSessionFactory;

public abstract class HibernateDao<T, ID extends Serializable> {

    private final Class<T> entityClass;
    protected SessionFactory sessionFactory;

    public HibernateDao(Class<T> entityClass, SessionFactory sessionFactory) {
        this.entityClass = entityClass;
        this.sessionFactory = sessionFactory;
    }

    public void save(T entity) {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) { // TODO: сузить исключения и создать более конкретные
            if (transaction != null) {
                transaction.rollback();
                throw new DataAccessException(e.getCause());
            }
        }
    }

}
