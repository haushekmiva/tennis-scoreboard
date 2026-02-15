package com.haushekmiva.dao;

import com.haushekmiva.exceptions.DataAccessException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public abstract class HibernateDao<T, ID extends Serializable> {

    private static final Logger log = LoggerFactory.getLogger(HibernateDao.class);

    private final Class<T> entityClass;
    private final SessionFactory sessionFactory;

    public HibernateDao(Class<T> entityClass, SessionFactory sessionFactory) {
        this.entityClass = entityClass;
        this.sessionFactory = sessionFactory;
    }

    public Long save(T entity) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Long id = (Long) session.save(entity);
            transaction.commit();
            return id;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error("Failed to save {} entity", entity.getClass().getSimpleName(), e);
            throw new DataAccessException("An error occurred while saving entity.", e);
        }
    }

    public T getReferenceById(ID id) {
        try (Session session = sessionFactory.openSession()) {
            return session.getReference(entityClass, id);
        } catch (HibernateException e) {
            throw new DataAccessException(e);
        }
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
