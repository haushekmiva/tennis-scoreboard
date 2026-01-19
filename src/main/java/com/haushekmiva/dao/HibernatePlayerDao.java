package com.haushekmiva.dao;

import com.haushekmiva.exceptions.DataAccessException;
import com.haushekmiva.model.Player;
import org.hibernate.Session;
import org.hibernate.Transaction;

import static com.haushekmiva.HibernateUtil.getSessionFactory;

public class HibernatePlayerDao {

    public void savePlayer(Player player) {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(player);
            transaction.commit();
        } catch (Exception e) { // TODO: сузить исключения и создать более конкретные
            if (transaction != null) {
                transaction.rollback();
                throw new DataAccessException(e.getCause());
            }
        }
    }

}
