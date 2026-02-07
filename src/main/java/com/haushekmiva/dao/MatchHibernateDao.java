package com.haushekmiva.dao;

import com.haushekmiva.exceptions.DataAccessException;
import com.haushekmiva.model.Match;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class MatchHibernateDao extends HibernateDao<Match, Long> {

    public MatchHibernateDao(SessionFactory sessionFactory) {
        super(Match.class, sessionFactory);
    }

    public Long getMatchCount() {
        try (Session session = super.getSessionFactory().openSession()) {
            return session.createQuery("select count(e) from Match e", Long.class)
                    .uniqueResult();
        } catch (HibernateException e) {
            throw new DataAccessException("Ошибка при получении кол-ва записей из бд.", e);
        }
    }

    public List<Match> fetchMatchesSubset(int from, int to) {
        try (Session session = super.getSessionFactory().openSession()) {
            return session.createQuery("from Match", Match.class)
                    .setFirstResult(from)
                    .setMaxResults(to)
                    .list();
        }
        catch (HibernateException e) {
            throw new DataAccessException("Ошибка при получении данных из бд.");
        }
    }

}
