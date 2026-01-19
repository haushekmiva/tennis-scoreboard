package com.haushekmiva.dao;

import com.haushekmiva.exceptions.DataAccessException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.xml.crypto.Data;
import java.io.Serializable;

public abstract class HibernateDao<T, ID extends Serializable> {

    private final Class<T> entityClass;
    private final SessionFactory sessionFactory;

    public HibernateDao(Class<T> entityClass, SessionFactory sessionFactory) {
        this.entityClass = entityClass;
        this.sessionFactory = sessionFactory;
    }

    public void save(T entity) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(entity); // если нужно будет айди потом то поменяю
            transaction.commit();
        } catch (Exception e) { // TODO: сузить исключения и создать более конкретные
            if (transaction != null) {
                transaction.rollback();
                throw new DataAccessException(e);
            }
        }
    }

    public T findById(ID id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(entityClass, id);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
